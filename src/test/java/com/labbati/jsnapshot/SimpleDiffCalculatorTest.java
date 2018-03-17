package com.labbati.jsnapshot;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SimpleDiffCalculatorTest {

    private static Snapshot before = new Snapshot(
        () -> "some_id",
        new SnapshotValue("l1", 1),
        new SnapshotValue("l2", 2),
        new SnapshotValue("l3", 3)
    );

    private static Snapshot after = new Snapshot(
        () -> "some_id",
        new SnapshotValue("l1", 1),
        new SnapshotValue("l2", 12),
        new SnapshotValue("l3", 13)
    );

    @Test
    public void calculates_diff_when_before_is_null() {
        SimpleDiffCalculator subject = new SimpleDiffCalculator();
        List<DataDiff> result = subject.calculate(null, after);

        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getLabel()).isEqualTo("l1");
        assertThat(result.get(0).getOldValue()).isNull();
        assertThat(result.get(0).getNewValue()).isEqualTo(1);

        assertThat(result.get(1).getLabel()).isEqualTo("l2");
        assertThat(result.get(1).getOldValue()).isNull();
        assertThat(result.get(1).getNewValue()).isEqualTo(12);

        assertThat(result.get(2).getLabel()).isEqualTo("l3");
        assertThat(result.get(2).getOldValue()).isNull();
        assertThat(result.get(2).getNewValue()).isEqualTo(13);
    }

    @Test
    public void calculates_diff_when_after_is_null() {
        SimpleDiffCalculator subject = new SimpleDiffCalculator();
        List<DataDiff> result = subject.calculate(before, null);

        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getLabel()).isEqualTo("l1");
        assertThat(result.get(0).getOldValue()).isEqualTo(1);
        assertThat(result.get(0).getNewValue()).isNull();

        assertThat(result.get(1).getLabel()).isEqualTo("l2");
        assertThat(result.get(1).getOldValue()).isEqualTo(2);
        assertThat(result.get(1).getNewValue()).isNull();

        assertThat(result.get(2).getLabel()).isEqualTo("l3");
        assertThat(result.get(2).getOldValue()).isEqualTo(3);
        assertThat(result.get(2).getNewValue()).isNull();
    }

    @Test
    public void calculates_diff_omitting_unchanged_values() {
        SimpleDiffCalculator subject = new SimpleDiffCalculator();
        List<DataDiff> result = subject.calculate(before, after);

        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getLabel()).isEqualTo("l2");
        assertThat(result.get(0).getOldValue()).isEqualTo(2);
        assertThat(result.get(0).getNewValue()).isEqualTo(12);

        assertThat(result.get(1).getLabel()).isEqualTo("l3");
        assertThat(result.get(1).getOldValue()).isEqualTo(3);
        assertThat(result.get(1).getNewValue()).isEqualTo(13);
    }

    @Test
    public void calculates_diff_including_unchanged_values() {
        SimpleDiffCalculator subject = new SimpleDiffCalculator();
        List<DataDiff> result = subject.calculate(before, after, true);

        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getLabel()).isEqualTo("l1");
        assertThat(result.get(0).getOldValue()).isEqualTo(1);
        assertThat(result.get(0).getNewValue()).isEqualTo(1);

        assertThat(result.get(1).getLabel()).isEqualTo("l2");
        assertThat(result.get(1).getOldValue()).isEqualTo(2);
        assertThat(result.get(1).getNewValue()).isEqualTo(12);

        assertThat(result.get(2).getLabel()).isEqualTo("l3");
        assertThat(result.get(2).getOldValue()).isEqualTo(3);
        assertThat(result.get(2).getNewValue()).isEqualTo(13);
    }
}
