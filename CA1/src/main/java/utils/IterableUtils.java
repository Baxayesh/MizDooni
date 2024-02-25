package utils;

public class IterableUtils {

    public static <T> boolean IsEmpty (Iterable<T> items){
        var iterator = items.iterator();
        return ! iterator.hasNext();
    }

    public static <T> int Count(Iterable<T> items){
        int count = 0;

        for(
            var i = items.iterator();
            i.hasNext();
            i.next()
        )   {
            count++;
        }

        return count;
    }

    public static <T> T First (Iterable<T> items){

        var iterator = items.iterator();

        if(iterator.hasNext()){

            return iterator.next();
        }

        throw new RuntimeException("Cannot find first element of empty iterable");

    }

    public static <T> T Single(Iterable<T> items) {

        var iterator = items.iterator();

        if(iterator.hasNext()){
            var first = iterator.next();

            if(iterator.hasNext())
                throw new RuntimeException("Cannot find first element of empty iterable");

            return first;
        }

        throw new RuntimeException("Cannot find any element in empty iterable");
    }
}
