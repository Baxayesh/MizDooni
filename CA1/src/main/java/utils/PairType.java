package utils;

import lombok.Getter;

import java.util.Objects;

@Getter
public class PairType <TFirst, TSecond> {

    private final TFirst First;
    private final TSecond Second;

    public PairType(TFirst first, TSecond second){
        First = first;
        Second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairType<?, ?> pairType = (PairType<?, ?>) o;
        return Objects.equals(First, pairType.First) && Objects.equals(Second, pairType.Second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(First, Second);
    }

    @Override
    public String toString() {
        return String.format("Pair(%s, %s)", First.toString(), Second.toString());
    }
}
