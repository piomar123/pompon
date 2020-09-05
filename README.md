# pompon-maven-plugin
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.piomar/pompon-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/me.piomar/pompon-maven-plugin/)
[![Build Status](https://travis-ci.com/piomar123/pompon.svg?branch=master)](https://travis-ci.com/piomar123/pompon)

POM Put in Order Nicely Maven Plugin

A Maven plugin for keeping an order of project POM files. 
Currently, it enforces the alphabetical order of properties, dependencies and plugins. In case of violation, it shows expected order including exact locations. 
Each section can be divided into sub-sections by adding a comment between entries. This way, it's possible to prioritize selected dependencies/properties when they are more important (changing dependencies order can change resulting versions in Maven dependency race).

## Usage

Inline checking with the latest plugin version: `mvn me.piomar:pompon-maven-plugin:check`

Embedding in the project:
```xml
<plugin>
    <groupId>me.piomar</groupId>
    <artifactId>pompon-maven-plugin</artifactId>
    <version>${version}</version>
    <executions>
        <execution>
            <id>check-order</id>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
