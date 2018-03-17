package com.labbati.jsnapshot;

/**
 * Represent a labeled piece of data at two different points in time.
 */
public class DataDiff {

    private String label;
    private Object oldValue;
    private Object newValue;

    public DataDiff(String label, Object oldValue, Object newValue) {
        this.label = label;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * The data label
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * The old value of the piece of data. It can be used as the base value.
     * @return
     */
    public Object getOldValue() {
        return oldValue;
    }

    /**
     * The new value of the piece of data.
     * @return
     */
    public Object getNewValue() {
        return newValue;
    }
}
