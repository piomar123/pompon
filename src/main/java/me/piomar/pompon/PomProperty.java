package me.piomar.pompon;

import java.util.Comparator;

import javax.annotation.Nonnull;

import org.immutables.value.Value;

@Value.Immutable(copy = false)
public abstract class PomProperty implements Comparable<PomProperty> {

    private static final Comparator<PomProperty> COMPARATOR =
        Comparator.comparing(PomProperty::value);

    public abstract String value();

    @Override
    public String toString() {
        return String.format("<%s>", value());
    }

    @Override
    public int compareTo(@Nonnull PomProperty o) {
        return COMPARATOR.compare(this, o);
    }
}
