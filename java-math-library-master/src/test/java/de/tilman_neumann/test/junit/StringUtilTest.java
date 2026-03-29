package de.tilman_neumann.test.junit;

import de.tilman_neumann.util.StringUtil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    // ================= repeat =================

    @Test // boundary test
    void testRepeat_nullString() {
        assertNull(StringUtil.repeat(null, 5));
    }

    @Test // boundary test n = 0
    void testRepeat_zeroN() {
        assertNull(StringUtil.repeat("a", 0));
    }

    @Test // boundary below 0
    void testRepeat_negativeN() {
        assertNull(StringUtil.repeat("a", -1));
    }

    @Test // boundary above 0
    void testRepeat_emptyString() {
        assertEquals("", StringUtil.repeat("", 5));
    }

    @Test
    void testRepeat_nEqualsOne() {
        assertEquals("a", StringUtil.repeat("a", 1));
    }

    @Test
    void testRepeat_normalCase() {
        assertEquals("ababab", StringUtil.repeat("ab", 3));
    }

    // ================= formatLeft =================

    @Test
    void testFormatLeft_nullString() {
        assertEquals("123", StringUtil.formatLeft(null, "123"));
    }

    @Test
    void testFormatLeft_nullMask() {
        assertEquals("abc", StringUtil.formatLeft("abc", null));
    }

    @Test // Case S length is less than mask
    void testFormatLeft_shorterThanMask() {
        assertEquals("abc456", StringUtil.formatLeft("abc", "123456"));
    }

    @Test // Case S length is greater than mask
    void testFormatLeft_longerThanMask() {
        assertEquals("abcdef", StringUtil.formatLeft("abcdef", "123"));
    }

    @Test // Case S length equals 0
    void testFormatLeft_emptyString() {
        assertEquals("123", StringUtil.formatLeft("", "123"));
    }

    @Test
    void testFormatLeft_emptyMask() {
        assertEquals("abc", StringUtil.formatLeft("abc", ""));
    }

    // ================= formatRight =================

    @Test
    void testFormatRight_nullString() {
        assertEquals("123", StringUtil.formatRight(null, "123"));
    }

    @Test
    void testFormatRight_nullMask() {
        assertEquals("abc", StringUtil.formatRight("abc", null));
    }

    @Test
    void testFormatRight_shorterThanMask() {
        assertEquals("123abc", StringUtil.formatRight("abc", "123456"));
    }

    @Test
    void testFormatRight_longerThanMask() {
        assertEquals("abcdef", StringUtil.formatRight("abcdef", "123"));
    }

    @Test
    void testFormatRight_emptyString() {
        assertEquals("123", StringUtil.formatRight("", "123"));
    }

    @Test
    void testFormatRight_emptyMask() {
        assertEquals("abc", StringUtil.formatRight("abc", ""));
    }
}