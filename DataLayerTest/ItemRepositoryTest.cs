﻿using DataLayer.Model;
using DataLayer.Repository;
using System.Collections.Generic;
using System.Linq;
using Xunit;

namespace DataLayerTest
{
    public class ItemRepositoryTest
    {
        [Fact]
        public void ItemWithoutIconShouldBeAdded()
        {
            ItemRepository repository = new ItemRepository(new TestDatabaseProvider());

            Item item = TestDataProvider.GetItem();
            repository.Save(item);

            Item dbItem = repository.Get(item.Id);

            Assert.NotNull(dbItem);
            Assert.Equal(item.Id, dbItem.Id);
        }

        [Fact]
        public void ItemShouldBeDeleted()
        {
            ItemRepository repository = new ItemRepository(new TestDatabaseProvider());

            Item item = TestDataProvider.GetItem();
            repository.Save(item);

            repository.Delete(item);

            Item dbItem = repository.Get(item.Id);
            Assert.Null(dbItem);
        }

        [Fact]
        public void ItemShouldBeUpdated()
        {
            ItemRepository repository = new ItemRepository(new TestDatabaseProvider());

            Item item = TestDataProvider.GetItem();
            repository.Save(item);

            // update name
            item.Name = "new item name";
            repository.Save(item);

            Item dbItem = repository.Get(item.Id);
            Assert.NotNull(dbItem);
            Assert.Equal(item.Name, dbItem.Name);          
        }
    }
}
