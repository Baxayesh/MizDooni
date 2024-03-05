package database;

import exceptions.KeyAlreadyExists;
import exceptions.KeyNotFound;
import models.EntityModel;

import java.util.function.Predicate;
import java.util.stream.Stream;

public interface IRepository<TKey, TItem extends EntityModel<TKey>> {

    void Add(TItem item) throws KeyAlreadyExists;

    void Upsert(TItem item);

    TItem Get(TKey key) throws KeyNotFound;
    Stream<TItem> Search(Predicate<TItem> searchCriteria);
    boolean Exists(TKey key);

}