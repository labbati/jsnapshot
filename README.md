## jsnapshot

`jsnapshot` is a super simple set of utilities to take and handle snapshots of your POJOs.

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
            new SnapshotValue("property 1", 1),
            new SnapshotValue("property 2", nullSafe(() -> getNested().getOther().getName(), "defaultValue"))
        );
    }
}
```

Then you can just do `Snapshot snapshot = person.takeSnapshot()`. You basically decide which fields get included into the snapshot and with which value. Nothing magic here, just old plain reliable getters.

If you take snapshot of a POJO in two different instant of time, let's say

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