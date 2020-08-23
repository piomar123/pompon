package me.piomar.pompon;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.google.common.collect.ImmutableMap;

@Value.Immutable
public abstract class PomDependenciesSection {

    @Nullable
    public abstract String name();

    @Nullable
    public abstract PomXmlComment commentNode();

    public abstract ImmutableMap<PomDependency, PomXmlElement> dependencies();
}
