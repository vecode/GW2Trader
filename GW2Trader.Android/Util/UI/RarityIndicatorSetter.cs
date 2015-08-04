using Android.App;
using Android.Graphics.Drawables;
using Android.Widget;
using GW2Trader.Model;

namespace GW2Trader.Android.Util.UI
{
    public static class RarityIndicatorSetter
    {
        public static void SetRarityColor(Activity activity, ImageView iconImageView, Item item)
        {
            GradientDrawable foreground = (GradientDrawable)iconImageView.Drawable;
            int colorId;
            switch (item.Rarity)
            {
                case "Junk":
                    colorId = Resource.Color.RarityJunkColor;
                    break;
                case "Basic":
                    colorId = Resource.Color.RarityBasicColor;
                    break;
                case "Fine":
                    colorId = Resource.Color.RarityFineColor;
                    break;
                case "Masterwork":
                    colorId = Resource.Color.RarityMasterworkColor;
                    break;
                case "Rare":
                    colorId = Resource.Color.RarityRareColor;
                    break;
                case "Exotic":
                    colorId = Resource.Color.RarityExoticColor;
                    break;
                case "Ascended":
                    colorId = Resource.Color.RarityAscendedColor;
                    break;
                case "Legendary":
                    colorId = Resource.Color.RarityLegendaryColor;
                    break;
                default:
                    return;
            }
            if (activity == null) return;
            var color = activity.Resources.GetColor(colorId);
            activity.RunOnUiThread(() => foreground.SetStroke(6, color));
        }
    }
}