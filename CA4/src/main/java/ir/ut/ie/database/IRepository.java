package ir.ut.ie.database;

import ir.ut.ie.exceptions.KeyAlreadyExists;
import ir.ut.ie.exceptions.KeyNotFound;
import ir.ut.ie.models.EntityModel;

import java.util.function.Predicate;
import java.util.stream.Stream;

public interface IRepository<TKey, TItem extends EntityModel<TKey>> {

    void Add(TItem item) throws KeyAlreadyExists;

    void Upsert(TItem item);

    TItem Get(TKey key) throws KeyNotFound;
    Stream<TItem> Search(Predicate<TItem> searchCriteria);
    boolean Exists(TKey key);

}