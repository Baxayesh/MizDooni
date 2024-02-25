package database;

import exceptions.KeyAlreadyExists;
import exceptions.KeyNotFound;
import model.EntityModel;

import java.util.function.Predicate;

public interface IRepository<TKey, TItem extends EntityModel<TKey>> {

    void Add(TItem item) throws KeyAlreadyExists;
    TItem Get(TKey key) throws KeyNotFound;
    Iterable<TItem> GetAll();
    Iterable<TItem> Search(Predicate<TItem> searchCriteria);
    void Update(TKey key, TItem updated) throws KeyNotFound, KeyAlreadyExists;
    void Delete(TKey key) throws KeyNotFound;
    int Count();

}