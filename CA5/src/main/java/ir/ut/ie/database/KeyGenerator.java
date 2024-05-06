package ir.ut.ie.database;

public class KeyGenerator {

    int NextKey;

    public KeyGenerator(int startKey){
        NextKey = startKey;
    }

    public KeyGenerator() {
        this(0);
    }

    public int GetNext(){
        return NextKey++;
    }
}
