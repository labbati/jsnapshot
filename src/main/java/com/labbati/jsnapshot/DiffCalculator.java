package com.labbati.jsnapshot;

import java.util.List;

public interface DiffCalculator {

    /**
     * Calculates the difference between two snapshots skipping values
     * that look unchanged.
     *
     * @param before
     * @param after
     * @return
     */
    default List<DataDiff> calculate(Snapshot before, Snapshot after) {
        return calculate(before, after, false);
    }

    /**
     * Calculates the difference between two snapshots.
     * Based on @param includeUnchangedValues you can decide whether or
     * not unchanged values should be skipped.
     *
     * @param before
     * @param after
     * @param includeUnchangedValues
     * @return
     */
    List<DataDiff> calculate(Snapshot before, Snapshot after, boolean includeUnchangedValues);
}
