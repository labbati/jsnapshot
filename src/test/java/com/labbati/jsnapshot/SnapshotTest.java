package com.labbati.jsnapshot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SnapshotTest {

    @Test
    public void getters_returns_the_correct_values() {
        Snapshot snapshot = new Snapshot(
            () -> 123,
            new SnapshotValue("a", 1),
            new SnapshotValue("b", 2)
        );

        assertThat(snapshot.getTargetId()).isEqualTo(123);
        assertThat(snapshot.getValueMap().get("a").getValue()).isEqualTo(1);
        assertThat(snapshot.getValueMap().get("b").getValue()).isEqualTo(2);
    }
}
