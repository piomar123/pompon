package me.piomar.pompon;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.google.common.collect.ImmutableMap;

@Value.Immutable
public abstract class PomSection {

    @Nullable
    public abstract String name();

    @Nullable
    public abstract PomXmlComment commentNode();

    // TODO multimap - duplicated entries
    public abstract ImmutableMap<String, PomXmlElement> properties();
}
