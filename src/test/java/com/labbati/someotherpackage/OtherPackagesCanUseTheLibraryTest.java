package com.labbati.someotherpackage;

import com.labbati.jsnapshot.*;
import org.junit.Test;

import java.util.List;

/**
 * This test is not to test any functional aspect of the library. It is just to test that we are not messing
 * up with public vs package-scoped classes.
 */
class OtherPackagesCanUseTheLibraryTest {

    private static class Pojo implements SnapshotCapable {

        @Override
        public Snapshot takeSnapshot() {
            return new Snapshot(
                () -> 1,
                new SnapshotValue("l1", 100)
            );
        }
    }

    private static class MyDiffCalculator implements DiffCalculator {

        @Override
        public List<DataDiff> calculate(Snapshot before, Snapshot after, boolean includeUnchangedValues) {
            return null;
        }
    }

    private static class MyValueConverter implements ValueConverter {

        @Override
        public Object apply(Object t) {
            return null;
        }
    }

    @Test
    public void classes_are_visible() {
        new DataDiff("l1", 1, 2);
        new DefaultValueConverter();
        new SimpleDiffCalculator();
        new Snapshot(() -> 1);
        new SnapshotValue("l1", 1);
    }
}
