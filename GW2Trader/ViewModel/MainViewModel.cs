﻿using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data.Entity;
using System.Linq;
using System.Windows;
using GW2TPApiWrapper.Wrapper;
using GW2Trader.Data;
using GW2Trader.DesignTimeErrorPrevention;
using GW2Trader.Model;
using System.Threading.Tasks;
using GW2Trader.Command;

namespace GW2Trader.ViewModel
{
    public class MainViewModel : BaseViewModel
    {
        private int _selectedTabIndex;
        private IGameDataContextProvider _contextProvider = new GameDataContextProvider();
        private List<GameItemModel> _sharedItems;
        private ITradingPostApiWrapper _tpApiWrapper;
        private IApiDataUpdater _dataUpdater;

        public ObservableCollection<BaseViewModel> ChildViews { get; private set; }

        public MainViewModel()
        {
            Init();

            var watchlistViewModel = new WatchlistViewModel(_contextProvider, _sharedItems);
            var searchViewModel = new ItemSearchViewModel(_sharedItems, _dataUpdater, watchlistViewModel);
            var investmentViewModel = new InvestmentViewModel();

            ChildViews = new ObservableCollection<BaseViewModel>
            {
                searchViewModel,
                watchlistViewModel,
                investmentViewModel
            };
        }

        public int SelectedTabIndex
        {
            get { return _selectedTabIndex; }
            set
            {
                _selectedTabIndex = value;
                RaisePropertyChanged("SelectedTabIndex");
            }
        }

        private void Init()
        {
            // prevent design time error in xaml designer
            if (System.ComponentModel.DesignerProperties.GetIsInDesignMode(new DependencyObject()))
            {
                _contextProvider = new FakeDataContextProvider();
            }
            else
            {
                _contextProvider = new GameDataContextProvider();
            }

            _tpApiWrapper = new TradingPostApiWrapper(new ApiAccessor());
            _dataUpdater = new ApiDataUpdater(_tpApiWrapper);

            DbBuilder dbBuilder = new DbBuilder(_tpApiWrapper, _contextProvider);
            dbBuilder.BuildDatabase();
            
            using (var context = _contextProvider.GetContext())
            {
                _sharedItems = context.GameItems.ToList();
            }
            Task.Run(() => _dataUpdater.UpdatePricesParallel(_sharedItems));
        }

        public void UpdateCommerceData()
        {
            _dataUpdater.UpdatePricesParallel(_sharedItems);
        }

        private RelayCommand _updateCommerceDataCommand;

        public RelayCommand UpdateCommerceDataCommand
        {
            get
            {
                if(_updateCommerceDataCommand == null) 
                    _updateCommerceDataCommand = new UpdateCommerceDataCommand();
                return _updateCommerceDataCommand;
            }
        }
    }
}