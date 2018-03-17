package com.labbati.jsnapshot;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class DataDiffTest {

    @Test
    public void getters_returns_the_correct_values() {
        DataDiff subject = new DataDiff("label", 1, 2);

        assertThat(subject.getLabel()).isEqualTo("label");
        assertThat(subject.getOldValue()).isEqualTo(1);
        assertThat(subject.getNewValue()).isEqualTo(2);
    }
}
