package com.labbati.jsnapshot;

public class SnapshotValue {
    private String label;
    private Object value;

    public SnapshotValue(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public Object getValue() {
        return value;
    }
}
