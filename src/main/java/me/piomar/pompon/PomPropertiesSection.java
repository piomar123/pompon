package me.piomar.pompon;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.google.common.collect.ImmutableMap;

@Value.Immutable
public abstract class PomPropertiesSection {

    @Nullable
    public abstract String name();

    @Nullable
    public abstract PomXmlComment commentNode();

    public abstract ImmutableMap<String, PomXmlElement> properties();
}
