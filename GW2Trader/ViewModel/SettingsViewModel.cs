﻿using GW2Trader.Command;
using GW2Trader.Data;

namespace GW2Trader.ViewModel
{
    public class SettingsViewModel : BaseViewModel
    {
        private readonly IDbBuilder _dbBuilder;

        public SettingsViewModel(IDbBuilder dbBuilder)
        {
            ViewModelName = "Settings";
            _dbBuilder = dbBuilder;
        }

        public void UpdateDatabase()
        {
            _dbBuilder.UpdateDatabase();
        }

        #region Commands

        private RelayCommand _updateDatabaseCommand;

        public RelayCommand UpdateDatabaseCommand
        {
            get
            {
                if (_updateDatabaseCommand == null)
                    _updateDatabaseCommand = new UpdateDatabaseCommand();
                return _updateDatabaseCommand;
            }
        }

        #endregion
    }
}
