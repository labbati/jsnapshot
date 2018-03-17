package com.labbati.jsnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * The core representation of a POJO at a given point in time.
 */
public class Snapshot {

    /**
     * The object id is not necessary calculated when the snapshot is taken. In some cases, e.g. when a JPA entity
     * is created and the ID is given by the DB as an identity, you may want to delay the retrieval of the identifier
     * until after object has been saved.
     */
    private Supplier targetIdSupplier;

    /**
     * {@link Snapshot::getValueMap}
     */
    private Map<String, SnapshotValue> valueMap = new HashMap<>();

    public Snapshot(Supplier targetIdSupplier, SnapshotValue... values) {
        this.targetIdSupplier = targetIdSupplier;
        Arrays.asList(values).forEach((v) -> valueMap.put(v.getLabel(), v));
    }

    public Snapshot(Object id, SnapshotValue... values) {
        this(() -> id, values);
    }

    /**
     * Returns the target identifier, getting the value from the identity supplier.
     *
     * @return
     */
    public Object getTargetId() {
        return targetIdSupplier.get();
    }

    /**
     * A value map of labels to {@link Snapshot} values.
     */
    public Map<String, SnapshotValue> getValueMap() {
        return valueMap;
    }
}
