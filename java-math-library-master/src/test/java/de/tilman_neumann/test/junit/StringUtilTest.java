package de.tilman_neumann.test.junit;

import de.tilman_neumann.util.StringUtil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test suite for StringUtil class.
 *
 * ============= REPEAT METHOD: repeat(String s, int n) =============
 *
 * PARTITIONS FOR s:
 *   s1: s = null
 *   s2: s = "" (empty)
 *   s3: s = "a" or "ab" (non-empty)
 *
 * PARTITIONS FOR n:
 *   n1: n < 0 (negative)
 *   n2: n = 0 (boundary)
 *   n3: n = 1
 *   n4: n > 1 (multiple)
 *
 * BOUNDARY VALUES & EXPECTED BEHAVIOR:
 *   • s=null, n=5          → null
 *   • s="a", n=0           → null
 *   • s="a", n=-1          → null
 *   • s="", n=5            → ""
 *   • s="a", n=1           → "a"
 *   • s="ab", n=3          → "ababab"
 *
 * ============= FORMATLEFT METHOD: formatLeft(String s, String mask) =============
 *
 * PARTITIONS FOR s:
 *   s1: s = null
 *   s2: s = "" (empty)
 *   s3: s = "abc" (non-empty)
 *
 * PARTITIONS FOR mask:
 *   m1: mask = null
 *   m2: mask = "" (empty)
 *   m3: mask = "123" or "123456" (non-empty)
 *
 * PARTITIONS FOR length relationship:
 *   l1: len(s) < len(mask)
 *   l2: len(s) > len(mask)
 *   l3: len(s) = len(mask)
 *
 * BOUNDARY VALUES & EXPECTED BEHAVIOR:
 *   • s=null, mask="123"       → "123"
 *   • s="abc", mask=null       → "abc"
 *   • s="abc", mask="123456"   → "abc456"
 *   • s="abcdef", mask="123"   → "abcdef"
 *   • s="", mask="123"         → "123"
 *   • s="abc", mask=""         → "abc"
 *
 * ============= FORMATRIGHT METHOD: formatRight(String s, String mask) =============
 *
 * PARTITIONS FOR s:
 *   s1: s = null
 *   s2: s = "" (empty)
 *   s3: s = "abc" (non-empty)
 *
 * PARTITIONS FOR mask:
 *   m1: mask = null
 *   m2: mask = "" (empty)
 *   m3: mask = "123" or "123456" (non-empty)
 *
 * PARTITIONS FOR length relationship:
 *   l1: len(s) < len(mask)
 *   l2: len(s) > len(mask)
 *   l3: len(s) = len(mask)
 *
 * BOUNDARY VALUES & EXPECTED BEHAVIOR:
 *   • s=null, mask="123"       → "123"
 *   • s="abc", mask=null       → "abc"
 *   • s="abc", mask="123456"   → "123abc"
 *   • s="abcdef", mask="123"   → "abcdef"
 *   • s="", mask="123"         → "123"
 *   • s="abc", mask=""         → "abc"
 */
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