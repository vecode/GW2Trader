﻿using System.Collections.Generic;
using System.Data.Entity;
using ErikEJ.SqlCe;
using GW2Trader.Desktop.Model;

namespace GW2Trader.Desktop.Data
{
    public class GameDataContext : DbContext, IGameDataContext
    {
        public GameDataContext()
            //: base("ItemDb.DbConnection")
        {
            Database.SetInitializer(new DbInitializer());            
        }

        public IDbSet<GameItemModel> GameItems { get; set; }
        public IDbSet<InvestmentModel> Investments { get; set; }
        public IDbSet<InvestmentWatchlistModel> InvestmentWatchlists { get; set; }
        public IDbSet<ItemWatchlistModel> ItemWatchlists { get; set; }

        public void Save()
        {
            SaveChanges();
        }

        public void BulkInsert(IList<GameItemModel> items)
        {
            using (var bcp = new SqlCeBulkCopy(Database.Connection.ConnectionString))
            {
                bcp.DestinationTableName = "GameItemModels";
                bcp.WriteToServer(items);
            }
        }

        private class DbInitializer : MigrateDatabaseToLatestVersion<GameDataContext, Migrations.Configuration>
        {
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<ItemWatchlistModel>()
                .HasMany(wl => wl.Items).WithMany(i => i.Watchlists);

            modelBuilder.Entity<InvestmentWatchlistModel>()
                .HasMany(wl => wl.Items).WithMany(i => i.InvestmentLists);

            modelBuilder.Entity<InvestmentModel>()
                .HasRequired(inv => inv.GameItem).WithMany(item => item.Investments);
        }
    }
}