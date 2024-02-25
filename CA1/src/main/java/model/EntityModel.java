package model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class EntityModel<TKey> {

    private final LocalDateTime CreationTime;
    private LocalDateTime LastSavedChange;
    private final TKey Key;

    protected EntityModel(TKey key) {
        Key = key;
        CreationTime = LocalDateTime.now();
        LastSavedChange = CreationTime;
    }

    public void ConfirmChangesHaveBeenSaved(){
        LastSavedChange = LocalDateTime.now();
    }

    public boolean Is(TKey otherKey){
        return this.Key.equals(otherKey);
    }

}
