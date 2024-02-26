package database;

import exceptions.KeyAlreadyExists;
import exceptions.KeyNotFound;
import model.EntityModel;

import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class InMemoryRepo<TKey, TItem extends EntityModel<TKey>> implements IRepository<TKey, TItem> {

    private HashMap<TKey, TItem> MemoryDb;

    void EnsureKeyNotExits(TItem item) throws KeyAlreadyExists {
        var key = item.getKey();
        if(MemoryDb.containsKey(key)){
            throw new KeyAlreadyExists(key.toString());
        }
    }

    void EnsureKeyExists(TKey key) throws KeyNotFound{
        if(!MemoryDb.containsKey(key)){
            throw new KeyNotFound(key.toString());
        }
    }

    @Override
    public void Add(TItem item) throws KeyAlreadyExists {
        EnsureKeyNotExits(item);
        MemoryDb.put(item.getKey(), item);
    }

    @Override
    public TItem Get(TKey key) throws KeyNotFound {
        EnsureKeyExists(key);
        return MemoryDb.get(key);
    }

    @Override
    public Stream<TItem> GetAll() {
        return MemoryDb.values().stream();
    }


    @Override
    public Stream<TItem> Search(Predicate<TItem> searchCriteria) {
        return MemoryDb.values().stream().filter(searchCriteria);
    }

    @Override
    public boolean Exists(TKey key){
        return MemoryDb.containsKey(key);
    }

    @Override
    public void Update(TKey key, TItem updated) throws KeyNotFound, KeyAlreadyExists {
        //TODO: Not Needed in InMemory Implementation //Handle it

        EnsureKeyExists(key);

        if(!updated.Is(key)){
            EnsureKeyNotExits(updated);
            MemoryDb.remove(key);
        }

        updated.ConfirmChangesHaveBeenSaved();
        MemoryDb.put(updated.getKey(), updated);
    }

    @Override
    public void Update(TItem updated) throws KeyNotFound {
        EnsureKeyExists(updated.getKey());
        updated.ConfirmChangesHaveBeenSaved();
        MemoryDb.put(updated.getKey(), updated);
    }

    @Override
    public void Delete(TKey key) throws KeyNotFound {
        EnsureKeyExists(key);
        MemoryDb.remove(key);
    }

    @Override
    public int Count() {
        return MemoryDb.size();
    }
}
