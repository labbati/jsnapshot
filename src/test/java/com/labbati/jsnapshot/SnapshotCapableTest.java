package com.labbati.jsnapshot;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class SnapshotCapableTest {

    @Test
    public void null_safe_() {
        DummySnapshotCapable subject = new DummySnapshotCapable();
        Snapshot snapshot = subject.takeSnapshot();

        assertThat(snapshot.getValueMap().get("null").getValue()).isEqualTo(null);
        assertThat(snapshot.getValueMap().get("default").getValue()).isEqualTo("defaultValue");
    }

    private static class DummySnapshotCapable implements SnapshotCapable {

        public int getId() { return 1; }

        @Override
        public Snapshot takeSnapshot() {
            return new Snapshot(
                this::getId,
                new SnapshotValue("null", nullSafe(() -> throwNullPointer())),
                new SnapshotValue("default", nullSafe(() -> throwNullPointer(), "defaultValue"))
            );
        }

        public int throwNullPointer() {
            throw new NullPointerException();
        }
    }
}
