# pompon-maven-plugin
[![Maven Central](https://img.shields.io/maven-central/v/me.piomar/pompon-maven-plugin.svg?label=Maven%20Central&color=brightgreen)](https://search.maven.org/search?q=g:%22me.piomar%22%20AND%20a:%22pompon-maven-plugin%22)

POM Put in Order Nicely Maven Plugin

A Maven plugin for keeping an order of project POM files

## Usage

Inline checking with the latest plugin version: `mvn me.piomar:pompon-maven-plugin:check`

Embedding in the project:
```pom
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
