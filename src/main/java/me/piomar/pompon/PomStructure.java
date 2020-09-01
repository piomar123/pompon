package me.piomar.pompon;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

public class PomStructure implements Orderable {

    @Nullable
    private final PomProperties properties;
    @Nullable
    private final PomDependencies dependencies;
    @Nullable
    private final PomDependencies dependencyManagement;
    @Nullable
    private final PomPlugins plugins;

    public PomStructure(@Nullable PomProperties properties,
                        @Nullable PomDependencies dependencies,
                        @Nullable PomDependencies dependencyManagement,
                        @Nullable PomPlugins plugins) {
        this.properties = properties;
        this.dependencies = dependencies;
        this.dependencyManagement = dependencyManagement;
        this.plugins = plugins;
    }

    public static PomStructure parse(PomXmlElement root) {
        PomProperties properties = root.getChildByName("properties").map(PomProperties::create).orElse(null);
        PomDependencies dependencies = root.getChildByName("dependencies").map(PomDependencies::create).orElse(null);
        PomDependencies dependencyManagement = root.getChildByName("dependencyManagement")
                                                   .flatMap(dm -> dm.getChildByName("dependencies"))
                                                   .map(PomDependencies::create)
                                                   .orElse(null);
        PomPlugins plugins = root.getChildByName("build")
                                 .flatMap(dm -> dm.getChildByName("plugins"))
                                 .map(PomPlugins::create)
                                 .orElse(null);

        return new PomStructure(properties, dependencies, dependencyManagement, plugins);
    }

    @Override
    public List<String> getOrderViolations() {
        return Stream.of(properties, dependencies, dependencyManagement, plugins)
                     .filter(Objects::nonNull)
                     .map(Orderable::getOrderViolations)
                     .flatMap(Collection::stream)
                     .collect(Collectors.toList());
    }
}
