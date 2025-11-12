# AppSuite Spring

## Build Configuration

### Annotation Processing Configuration

The project is configured to explicitly enable annotation processing to avoid compiler warnings. The Maven compiler plugin is configured with:

- `<proc>full</proc>` - Explicitly enables full annotation processing
- `-Xlint:-options` - Suppresses the annotation processing options warning
- `-Xlint:-varargs` - Suppresses varargs-related warnings

This configuration is set in `pom.xml` under the `maven-compiler-plugin`:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>${maven-compiler-plugin.version}</version>
    <configuration>
        <source>${java.version}</source>
        <target>${java.version}</target>
        <proc>full</proc>
        <compilerArgs>
            <arg>-Xlint:-options</arg>
            <arg>-Xlint:-varargs</arg>
        </compilerArgs>
    </configuration>
</plugin>
```

### Varargs Warnings

Some methods in the codebase use varargs parameters and pass arrays to varargs methods, which can generate compiler warnings. These warnings are suppressed using `@SuppressWarnings("varargs")` annotations on the affected methods:

- `AbstractFilter.hasKeys(String... keys)` - Line 73
- `CsvParser.buildCSVResourceStream()` - Line 98
- `ExcelParser.addHeaders()` - Line 108
- `TestUtils.pathString()` - Line 26

These warnings are non-critical and do not affect functionality. The code compiles and runs successfully.

## Spring Boot Version

This project uses Spring Boot 3.5.7 with Jakarta EE (migrated from javax.* to jakarta.*).

## Dependencies

Key dependency versions:
- Spring Boot: 3.5.7
- Java: 21
- Lombok: 1.18.42
- Guava: 33.5.0-jre
- JUnit Jupiter: 6.0.1

