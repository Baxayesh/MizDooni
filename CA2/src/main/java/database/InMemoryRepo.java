package database;

import exceptions.KeyAlreadyExists;
import exceptions.KeyNotFound;
import models.EntityModel;

import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class InMemoryRepo<TKey, TItem extends EntityModel<TKey>> implements IRepository<TKey, TItem> {

    private final HashMap<TKey, TItem> MemoryDb;

    public InMemoryRepo() {
         MemoryDb = new HashMap<>();
    }


    void EnsureKeyNotExits(TItem item) throws KeyAlreadyExists {
        var key = item.getKey();
        if(MemoryDb.containsKey(key)){
            throw new KeyAlreadyExists(key.toString());
        }
    }

    void EnsureKeyExists(TKey key) throws KeyNotFound{
        if(!MemoryDb.containsKey(key)){
            if(key == null){
                throw new KeyNotFound("<null>");
            }
            throw new KeyNotFound(key.toString());
        }
    }

    @Override
    public void Add(TItem item) throws KeyAlreadyExists {
        EnsureKeyNotExits(item);
        MemoryDb.put(item.getKey(), item);
    }

    @Override
    public void Upsert(TItem item) {
        MemoryDb.put(item.getKey(), item);
    }

    @Override
    public TItem Get(TKey key) throws KeyNotFound {
        EnsureKeyExists(key);
        return MemoryDb.get(key);
    }

    @Override
    public Stream<TItem> Search(Predicate<TItem> searchCriteria) {
        return MemoryDb.values().stream().filter(searchCriteria);
    }

    @Override
    public boolean Exists(TKey key){
        return MemoryDb.containsKey(key);
    }

}
