﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using GW2TPApiWrapper.Wrapper;
using System.Data.Entity;
using GW2Trader.Model;
using GW2TPApiWrapper.Entities;
using System.Net;
using GW2Trader.Util;
using System.Collections;

namespace GW2Trader.Data
{
    public class DbBuilder
    {
        private readonly ITradingPostApiWrapper _wrapper;
        private readonly GameDataContextProvider _contextProvider;

        public DbBuilder(ITradingPostApiWrapper wrapper, GameDataContextProvider contextProvider)
        {
            _wrapper = wrapper;
            _contextProvider = contextProvider;
        }

        public void BuildDatabase(bool dropOldDb = false)
        {
            using (var context = _contextProvider.GetContext())
            {
                if (context.GameItems.Count() == 0 || dropOldDb)
                {
                    int[] ids = _wrapper.ItemIds().ToArray();
                    List<Item> items = _wrapper.ItemDetails(ids).ToList();
                    List<GameItemModel> convertedItems = items.Select(i => ConvertToGameItem(i)).ToList();

                    context.GameItems.AddRange(convertedItems);
                    context.Save();
                }
            }
        }

        private static GameItemModel ConvertToGameItem(Item item)
        {
            GameItemModel itemModel = new GameItemModel
            {
                ItemId = item.Id,
                IconUrl = item.IconUrl,
                Name = item.Name,
                Rarity = item.Rarity,
                RestrictionLevel = item.Level,
                Type = item.Type,
                CommerceDataLastUpdated = DateTime.Now
            };
            return itemModel;
        }
    }
}
