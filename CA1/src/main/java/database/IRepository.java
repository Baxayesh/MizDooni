package database;

import exceptions.KeyAlreadyExists;
import exceptions.KeyNotFound;
import model.EntityModel;

import java.util.function.Predicate;
import java.util.stream.Stream;

public interface IRepository<TKey, TItem extends EntityModel<TKey>> {

    void Add(TItem item) throws KeyAlreadyExists;
    TItem Get(TKey key) throws KeyNotFound;
    Stream<TItem> GetAll();
    Stream<TItem> Search(Predicate<TItem> searchCriteria);
    boolean Exists(TKey key);
    void Update(TKey key, TItem updated) throws KeyNotFound, KeyAlreadyExists;
    void Update(TItem updated) throws KeyNotFound;
    void Delete(TKey key) throws KeyNotFound;
    int Count();

}