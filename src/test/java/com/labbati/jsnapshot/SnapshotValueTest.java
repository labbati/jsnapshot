package com.labbati.jsnapshot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SnapshotValueTest {

    @Test
    public void getters_returns_the_correct_values() {
        SnapshotValue subject = new SnapshotValue("label", 1);

        assertThat(subject.getLabel()).isEqualTo("label");
        assertThat(subject.getValue()).isEqualTo(1);
    }
}
