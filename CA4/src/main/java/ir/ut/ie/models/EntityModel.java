package ir.ut.ie.models;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class EntityModel<TKey> {

    private final LocalDateTime CreationTime;
    private final TKey Key;

    protected EntityModel(TKey key) {
        Key = key;
        CreationTime = LocalDateTime.now();
    }

    public boolean Is(TKey otherKey){
        return this.Key.equals(otherKey);
    }

}
