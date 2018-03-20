## jsnapshot [![Build Status](https://travis-ci.org/labbati/jsnapshot.svg?branch=master)](https://travis-ci.org/labbati/jsnapshot)

`jsnapshot` is a super simple set of utilities to take and handle snapshots of your POJOs. It requires Java 8+.

### Dependency resolution

`jsnapshot` is distribuited through `jcenter`.

#### Maven
In your `pom.xml`:

```xml
...
<repositories>
    ...
    <repository>
      <id>jcenter</id>
      <url>https://jcenter.bintray.com/</url>
    </repository>
    ...
</repositories>

<dependencies>
    ...
    <dependency>
      <groupId>com.labbati</groupId>
      <artifactId>jsnapshot</artifactId>
      <version>0.2.14</version>
      <type>pom</type>
    </dependency>
    ...
</dependencies>
```

#### Gradle
In your `build.gradle`:

```groovy
repositories {
    ...
    jcenter()
}

dependencies {
    ...
    compile 'com.labbati:jsnapshot:0.2.14'
}
```

### Usage

A snapshot is just a bunch of values wrapped in an instance of `Snapshot`. The simplest way to start working with `jsnapshot` is to add the interface `SnapshotCapable` to a POJO.

```java
public class Person implements SnapshotCapable {

    private int id;
    private String name;

    // ... getters and setter ...

    @Override
    public Snapshot takeSnapshot() {
        return new Snapshot(
            this::getId,
            new SnapshotValue("name or something else", 1),
            new SnapshotValue("some other label", nullSafe(() -> getNested().getOther().getName(), "defaultValue"))
        );
    }
}
```

Then you can just do `Snapshot snapshot = person.takeSnapshot()`. You basically decide which fields get included into the snapshot and with which value. Nothing's magic here, just old plain reliable getters.

As you may have noticed, for `some other label` we used `SnapshotCapable`'s method `nullSafe()`. This method is useful when you need to access nested objects to extract the value. If one object down the path is `null`, you would get a `NullPointerException`. This method makes sure that such exception is trapped.
The first argument to the method is java 8 lambda that just returns the value. The optional second argument is the fallback value that should be returned if `NullPointerException` is thrown. If not provided, than the fallback value is `null`.

If you take snapshots of a POJO in two different instant of time, let's say

```java
Snapshot before = person.takeSnapshot();
person.setName("Homer");
Snapshot after = person.takeSnapshot();
```
At this point you can calculate differences between the two snapshots.

```java
DiffCalculator diffCalculator = new SimpleDiffCalculator();
// You can either exclude values that have not changed...
List<DataDiff> diffsWithSkipUnchanged = diffCalculator.calculate(before, after);
// ...or include them
List<DataDiff> diffsWithIncludeUnchanged = diffCalculator.calculate(before, after, true);
```

The returned value is a list of `DataDiff`. `DataDiff` is just a value object including a `label`, the `oldValue` and the `newValue`.

By default, the value that you return in the `takeSnapshot()` method will be passed as is to the `DataDiff` object. With the exception of `Date` objects, which are converted to the correspondent value in milliseconds. However, when you build an instance of `SimpleDiffCalculator` you can provide your own implementation of `ValueConverter` if you do not like the idea.

A final note: obviously, it is not necessary to generate snapshots starting from a `SnapshotCapable`. Specifically, if generating the `Snapshot` requires complex calculations or the collaboration of other services, feel free to externalize the creation to separate service.

Have fun!

## License

[MIT](http://opensource.org/licenses/MIT)

Copyright (c) 2018-present, Luca Abbati