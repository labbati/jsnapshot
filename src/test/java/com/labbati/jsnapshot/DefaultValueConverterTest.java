package com.labbati.jsnapshot;

import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;

public class DefaultValueConverterTest {

    @Test
    public void it_converts_dates() {
        DefaultValueConverter subject = new DefaultValueConverter();
        assertThat(subject.apply(new Date(1L))).isEqualTo(1L);
    }

    @Test
    public void it_leaves_int_unchanged() {
        DefaultValueConverter subject = new DefaultValueConverter();
        assertThat(subject.apply(2)).isEqualTo(2);
    }

    @Test
    public void it_leaves_string_unchanged() {
        DefaultValueConverter subject = new DefaultValueConverter();
        assertThat(subject.apply("string")).isEqualTo(subject.apply("string"));
    }

    @Test
    public void it_leaves_null_unchanged() {
        DefaultValueConverter subject = new DefaultValueConverter();
        assertThat(subject.apply(null)).isEqualTo(subject.apply(null));
    }
}
