package ir.ut.ie.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class EntityModel<TKey> {

    private final LocalDateTime CreationTime;

    protected EntityModel() {

        CreationTime = LocalDateTime.now();
    }

    public abstract TKey getKey();

    public boolean Is(TKey otherKey){
        return this.getKey().equals(otherKey);
    }

}
