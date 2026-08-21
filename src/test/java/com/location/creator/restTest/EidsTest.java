package com.location.creator.restTest;

import com.location.creator.rest.Eids;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EidsTest {

    @Test
    public void toUuid_stripsPrefixAndSuffix() {
        String eid = "location:abc-123:ivt";
        assertThat(Eids.toUuid(eid)).isEqualTo("abc-123");
    }

    @Test
    public void toUuid_returnsPlainUuidUnchanged() {
        String eid = "123456789abcde";
        assertThat(Eids.toUuid(eid)).isEqualTo("123456789abcde");
    }
}
