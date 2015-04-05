﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using GW2TPApiWrapper.Entities;
using System.IO;
using Shared.Util;
using GW2TPApiWrapper.Util;

namespace GW2TPApiWrapper.Wrapper
{
    public class TradingPostApiWrapper : ITradingPostApiWrapper
    {
        private readonly IApiAccessor _apiAccessor;

        private const int MaxRequestSize = 200;

        /// <summary>
        /// Number of ids per request (limited by API)
        /// </summary>
        private int _requestSize = MaxRequestSize;
        public int RequestSize
        {
            get { return _requestSize; }
            set
            {
                _requestSize = value > 1 ? Math.Min(MaxRequestSize, value) : _requestSize;
            }
        }

        public TradingPostApiWrapper(IApiAccessor apiAccessor)
        {
            _apiAccessor = apiAccessor;
        }

        public int[] ItemIds()
        {
            Stream responseStream = _apiAccessor.ItemIds();
            int[] ids = ApiResponseConverter.DeserializeStream<int[]>(responseStream);
            return ids;
        }

        public Item ItemDetails(int id)
        {
            Stream responseStream = _apiAccessor.ItemDetails(id);
            if (responseStream == null) return null;
            else
            {
                Item item = ApiResponseConverter.DeserializeStream<Item>(responseStream);
                return item;
            }
        }

        public ItemListing Listings(int id)
        {
            Stream responseStream = _apiAccessor.Listings(id);
            if (responseStream == null) return null;
            else
            {
                ItemListing listing = ApiResponseConverter.DeserializeStream<ItemListing>(responseStream);
                return listing;
            }
        }

        public IList<Item> ItemDetails(int[] ids)
        {
            return ApiRequest<Item>(ids);
        }

        private IList<T> ApiRequest<T>(int[] ids) where T : GW2TPApiResponse
        {
            Func<int[], Stream> entityRetrievalMethod;

            if (typeof(T) == typeof(Item))
            {
                entityRetrievalMethod = (i) => _apiAccessor.ItemDetails(i);
            }
            else if (typeof(T) == typeof(ItemListing))
            {
                entityRetrievalMethod = (i) => _apiAccessor.Listings(i);
            }
            else if (typeof(T) == typeof(ItemPrice))
            {
                entityRetrievalMethod = (i) => _apiAccessor.Prices(i);
            }
            else
            {
                throw new NotSupportedException("Type " + typeof(T) + " is not supported");
            }

            List<T> entites = new List<T>(ids.Length);
            int needRequests = (int)Math.Ceiling((double)ids.Length / RequestSize);

            for (int i = 0; i < needRequests; i++)
            {
                var idSubset = ids.Skip(i * RequestSize).Take(RequestSize).ToArray();
                var responseStream = entityRetrievalMethod(idSubset);

                if (responseStream == null)
                {
                    break;
                }

                var entitySubset = ApiResponseConverter.DeserializeStream<T[]>(responseStream);
                entites.AddRange(entitySubset);
            }
            return entites;
        }

        public IList<ItemListing> Listings(int[] ids)
        {
            return ApiRequest<ItemListing>(ids);
        }

        public ItemPrice Price(int id)
        {
            throw new NotImplementedException();
        }

        public IList<ItemPrice> Price(int[] ids)
        {
            return ApiRequest<ItemPrice>(ids);
        }
    }
}
