package ir.ut.ie.utils;

import lombok.Getter;

import java.util.Objects;

@Getter
public class TripleType<TFirst, TSecond, TThird> {

    private final TFirst First;
    private final TSecond Second;
    private final TThird Third;


    public TripleType(TFirst first, TSecond second, TThird third) {
        First = first;
        Second = second;
        Third = third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripleType<?, ?, ?> pairType = (TripleType<?, ?, ?>) o;
        return Objects.equals(First, pairType.First) &&
                Objects.equals(Second, pairType.Second) &&
                Objects.equals(Third, pairType.Third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(First, Second, Third);
    }

    @Override
    public String toString() {
        return String.format("Triple(%s, %s, %s)", First.toString(), Second.toString(), Third.toString());
    }
}
