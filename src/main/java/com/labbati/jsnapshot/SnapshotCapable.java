package com.labbati.jsnapshot;

/**
 * This interface can be use don any POJO that should have snapshot capabilities. It is not central to the library
 * and you can use most of capabilities even not implementing this interface. This is useful when you have a to take
 * a simple snapshot of a POJO and the POJO itself can be the source of its own snapshot, not requiring any external
 * service to to the work. If taking a snapshot requires external services, e.g. because you want to convert a {@link java.util.Map}
 * to a json, then it may be better to externalize the snapshot creation to a dedicated service.
 */
public interface SnapshotCapable {

    /**
     * Return a snapshot of the subject.
     *
     * @return
     */
    Snapshot takeSnapshot();

    /**
     * Useful when you have to navigate a graph of nested objects to retrieve a value. If at a certain point in the
     * path a {@link NullPointerException} is throw, then this (and only this) exception is caught and null is returned
     * instead.
     *
     * @param propertyAccessor
     * @return
     */
    default Object nullSafe(PropertyAccessor propertyAccessor) {
        return nullSafe(propertyAccessor, null);
    }

    /**
     * Useful when you have to navigate a graph of nested objects to retrieve a value. If at a certain point in the
     * path a {@link NullPointerException} is throw, then this (and only this) exception is caught and defaultValue is
     * returned instead.
     *
     * @param propertyAccessor
     * @param defaultValue
     * @return
     */
    default Object nullSafe(PropertyAccessor propertyAccessor, Object defaultValue) {
        try {
            return propertyAccessor.execute();
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }

    @FunctionalInterface
    interface PropertyAccessor {
        Object execute();
    }
}
