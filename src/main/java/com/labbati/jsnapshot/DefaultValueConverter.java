package com.labbati.jsnapshot;

import java.util.Date;

/**
 * Default converter which convert a plain pojo value to a more
 * representation friendly version of it.
 * <p>
 * This base version only converts Date objects to their long value
 * in milliseconds.
 */
public class DefaultValueConverter implements ValueConverter {

    @Override
    public Object apply(Object value) {
        if (value instanceof Date) {
            return ((Date) value).getTime();
        } else {
            return value;
        }
    }
}
