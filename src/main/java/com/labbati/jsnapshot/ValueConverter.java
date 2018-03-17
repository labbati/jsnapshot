package com.labbati.jsnapshot;

@FunctionalInterface
public interface ValueConverter {
    Object apply(Object t);
}
