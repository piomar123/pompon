package me.piomar.pompon;

import java.util.Comparator;

import javax.annotation.Nonnull;

import org.immutables.value.Value;

@Value.Immutable(copy = false)
public abstract class PomDependency implements Comparable<PomDependency> {

    private static final Comparator<PomDependency> COMPARATOR =
        Comparator.comparing(PomDependency::scope)
                  .thenComparing(PomDependency::groupId)
                  .thenComparing(PomDependency::artifactId);

    public abstract String groupId();

    public abstract String artifactId();

    @Value.Default
    public String scope() {
        return "compile";
    }

    @Override
    public String toString() {
        return String.format("[%s] %s:%s", scope(), groupId(), artifactId());
    }

    @Override
    public int compareTo(@Nonnull PomDependency o) {
        return COMPARATOR.compare(this, o);
    }
}
