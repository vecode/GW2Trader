﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

using GW2Trader.ApiWrapper.Util;

namespace GW2TPApiWrapperTest.Util
{
    class TestWebClient : IWebClient
    {
        private WebClient _webClient = new WebClient();

        public System.IO.Stream OpenRead(string url)
        {
            return _webClient.OpenRead(url);
        }

        public void Dispose()
        {
            _webClient.Dispose();
        }
    }
}
