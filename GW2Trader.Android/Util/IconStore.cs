using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Net;
using Android.App;
using Android.Graphics;
using Android.Graphics.Drawables;
using Android.Widget;
using GW2Trader.Model;
using Path = System.IO.Path;

namespace GW2Trader.Android.Util
{
    public class IconStore : IIconStore
    {
        private readonly BackgroundWorker _backgroundWorker = new BackgroundWorker();
        private readonly string _iconDirectory;
        private readonly Queue<IconDownload> _iconsToLoad = new Queue<IconDownload>();
        private readonly Dictionary<ImageView, int> _viewCounter = new Dictionary<ImageView, int>();

        public IconStore(string iconDirectory)
        {
            _iconDirectory = iconDirectory;
            _backgroundWorker.DoWork += ProcessQueue;
        }

        public void SetIcon(Item item, ImageView view, Activity activity)
        {
            var path = GetIconPath(item);
            if (File.Exists(path))
            {
                if (view != null & activity != null)
                {
                    activity.RunOnUiThread(
                        () =>
                        {
                            //view.SetImageBitmap(BitmapFactory.DecodeFile(path)); 
                            view.Background = Drawable.CreateFromPath(path);
                        });
                }
            }
            _iconsToLoad.Enqueue
                (
                    new IconDownload
                    {
                        Item = item,
                        ImageView = view,
                        Activity = activity
                    }
                );

            lock (_viewCounter)
            {
                if (_viewCounter.ContainsKey(view))
                {
                    _viewCounter[view]++;
                }
                else
                {
                    _viewCounter.Add(view, 1);
                }
            }

            lock (_backgroundWorker)
            {
                if (!_backgroundWorker.IsBusy)
                {
                    _backgroundWorker.RunWorkerAsync();
                }
            }
        }

        private void ProcessQueue(object sender, DoWorkEventArgs e)
        {
            while (_iconsToLoad.Count > 0)
            {
                LoadNext();
            }
        }

        private void LoadNext()
        {
            var next = _iconsToLoad.Dequeue();
            if (next != null)
            {
                var path = GetIconPath(next.Item);
                var tmpFilePath = path + ".tmp";

                Directory.CreateDirectory(_iconDirectory);
                if (!File.Exists(path))
                {
                    using (var client = new WebClient())
                    {
                        client.DownloadFile(next.Item.IconUrl, tmpFilePath);
                        File.Move(tmpFilePath, path);
                    }
                }
                //if (next.ImageView != null & next.Activity != null)
                //{
                //    next.Activity.RunOnUiThread(
                //        () => next.ImageView.SetImageBitmap(BitmapFactory.DecodeFile(path)));
                //}
                //SetIcon(next.Item, next.ImageView, next.Activity);
                //next.ImageView.SetImageBitmap(BitmapFactory.DecodeFile(path));

                // don't update the ImageView if its going to be updated again
                lock (_viewCounter)
                {
                    if (!_viewCounter.ContainsKey(next.ImageView))
                    {
                        SetIcon(next.Item, next.ImageView, next.Activity);
                    }
                }
              

            }
        }

        private string GetIconPath(Item item)
        {
            return Path.Combine(_iconDirectory, Path.GetFileName(item.IconUrl));
        }

        private class IconDownload
        {
            public Item Item { get; set; }
            public ImageView ImageView { get; set; }
            public Activity Activity { get; set; }
        }
    }
}