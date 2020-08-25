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

    public PomStructure(@Nullable PomProperties properties, @Nullable PomDependencies dependencies, @Nullable PomDependencies dependencyManagement) {
        this.properties = properties;
        this.dependencies = dependencies;
        this.dependencyManagement = dependencyManagement;
    }

    public static PomStructure parse(PomXmlElement root) {
        PomProperties properties = root.getChildByName("properties").map(PomProperties::create).orElse(null);
        PomDependencies dependencies = root.getChildByName("dependencies").map(PomDependencies::create).orElse(null);
        PomDependencies dependencyManagement = root.getChildByName("dependencyManagement")
                                                   .flatMap(dm -> dm.getChildByName("dependencies"))
                                                   .map(PomDependencies::create)
                                                   .orElse(null);

        return new PomStructure(properties, dependencies, dependencyManagement);
    }

    @Override
    public List<String> getOrderViolations() {
        return Stream.of(properties, dependencies, dependencyManagement)
                     .filter(Objects::nonNull)
                     .map(Orderable::getOrderViolations)
                     .flatMap(Collection::stream)
                     .collect(Collectors.toList());
    }
}
