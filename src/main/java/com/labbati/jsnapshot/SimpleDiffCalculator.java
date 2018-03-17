package com.labbati.jsnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The simplest diff calculator. It supports:
 *  - Creations: in this case old value is null
 *  - Deletions: in this case new value is null
 *  - Updates: in this case a diff is returned which does not include unchanged values.
 */
public class SimpleDiffCalculator implements DiffCalculator {

    private ValueConverter valueConverter;

    public SimpleDiffCalculator() {
        this(new DefaultValueConverter());
    }

    public SimpleDiffCalculator(ValueConverter valueConverter) {
        this.valueConverter = valueConverter;
    }

    @Override
    public List<DataDiff> calculate(Snapshot before, Snapshot after, boolean includeUnchangedValues) {

        List<DataDiff> diffs = new ArrayList<>();

        // During a create action, before is null
        if (before == null && after != null) {
            for (Map.Entry<String, SnapshotValue> entry : after.getValueMap().entrySet()) {
                String label = entry.getKey();
                Object newValue = entry.getValue().getValue();
                if (includeUnchangedValues || hasChanged(null, newValue)) {
                    diffs.add(new DataDiff(label, null, newValue));
                }
            }
        }
        // During a remove action, after is null
        else if (before != null && after == null) {
            for (Map.Entry<String, SnapshotValue> entry : before.getValueMap().entrySet()) {
                String label = entry.getKey();
                Object oldValue = entry.getValue().getValue();
                if (includeUnchangedValues || hasChanged(oldValue, null)) {
                    diffs.add(new DataDiff(label, oldValue, null));
                }
            }
        } else {
            // Otherwise is just a diff
            for (Map.Entry<String, SnapshotValue> entry : after.getValueMap().entrySet()) {
                String label = entry.getKey();
                Object newValue = entry.getValue().getValue();
                Object oldValue = before.getValueMap().get(label).getValue();
                if (includeUnchangedValues || hasChanged(oldValue, newValue)) {
                    diffs.add(new DataDiff(label, oldValue, newValue));
                }
            }
        }

        return diffs;
    }

    /**
     * Returns whether or not the current value can be considered "changed".
     * This may be externalized to a dedicated strategy object in the future.
     * For now we keep it simple.
     *
     * @param oldValue
     * @param newValue
     * @return
     */
    private boolean hasChanged(Object oldValue, Object newValue) {

        oldValue = valueConverter.apply(oldValue);
        newValue = valueConverter.apply(newValue);

        if (oldValue == null) {
            return newValue != null;
        } else {
            return !oldValue.equals(newValue);
        }
    }
}
