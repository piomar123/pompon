package me.piomar.pompon;

import java.util.Comparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable(copy = false)
public abstract class PomPlugin implements Comparable<PomPlugin> {

    private static final Comparator<PomPlugin> COMPARATOR =
        Comparator.comparing(PomPlugin::groupId)
                  .thenComparing(PomPlugin::artifactId);

    public abstract String groupId();

    public abstract String artifactId();

    @Nullable
    public abstract String version();

    @Override
    public String toString() {
        return String.format("%s:%s", groupId(), artifactId());
    }

    @Override
    public int compareTo(@Nonnull PomPlugin o) {
        return COMPARATOR.compare(this, o);
    }
}
