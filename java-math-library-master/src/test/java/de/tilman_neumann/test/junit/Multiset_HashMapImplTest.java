package de.tilman_neumann.test.junit;

import de.tilman_neumann.util.Multiset_HashMapImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test suite for Multiset_HashMapImpl class.
 *
 * ============= CONSTRUCTORS =============
 *
 * PARTITIONS FOR input:
 *   i1: Empty (no args)
 *   i2: Collection (normal, empty)
 *   i3: Array (normal, empty)
 *   i4: Copy (another multiset)
 *
 * BOUNDARY VALUES & EXPECTED BEHAVIOR:
 *   • empty               → isEmpty() is true, totalCount() = 0
 *   • valid collection    → element counts equal collection occurrences
 *   • empty collection    → isEmpty() is true
 *   • valid array         → element counts equal array occurrences
 *   • empty array         → isEmpty() is true
 *   • copy                → independent but equal multiset
 *
 * ============= ADD METHOD: add(T entry, int mult) =============
 *
 * PARTITIONS FOR entry:
 *   e1: new entry
 *   e2: existing entry
 *
 * PARTITIONS FOR mult:
 *   m1: mult > 0
 *   m2: mult = 0 (boundary)
 *   m3: mult < 0 (boundary)
 *
 * BOUNDARY VALUES & EXPECTED BEHAVIOR:
 *   • new entry, mult=3       → returns 0, entry count becomes 3
 *   • existing entry, mult=0  → returns old count, state unchanged
 *   • existing entry, mult=-1 → returns old count, state unchanged
 *   • existing entry, mult=3  → accumulates correctly
 *
 * ============= REMOVE METHOD: remove(T key, int mult) =============
 *
 * PARTITIONS FOR key:
 *   k1: existing key
 *   k2: non-existent key
 *
 * PARTITIONS FOR mult relationship to present count:
 *   m1: mult < present count
 *   m2: mult = present count (boundary)
 *   m3: mult > present count (boundary)
 *
 * BOUNDARY VALUES & EXPECTED BEHAVIOR:
 *   • mult < present        → count reduces, key remains
 *   • mult = present        → key is completely removed
 *   • mult > present        → key is completely removed
 *   • non-existent key      → returns 0 / null
 *
 * ============= INTERSECT METHOD: intersect(Multiset other) =============
 *
 * PARTITIONS FOR other:
 *   o1: null
 *   o2: overlapping multiset
 *   o3: disjoint multiset
 *   o4: itself
 *
 * BOUNDARY VALUES & EXPECTED BEHAVIOR:
 *   • overlapping     → new multiset holds min count for shared keys
 *   • null            → returns empty multiset
 *   • disjoint        → returns empty multiset
 *   • itself          → returns equal multiset
 */
class Multiset_HashMapImplTest {

    private Multiset_HashMapImpl<String> multiset;

    @BeforeEach
    void setUp() {
        multiset = new Multiset_HashMapImpl<>();
    }

    // ================= Constructors =================

    @Test // empty constructor produces empty multiset
    void testConstructor_empty() {
        assertTrue(multiset.isEmpty());
        assertEquals(0, multiset.totalCount());
    }

    @Test // collection constructor with normal list
    void testConstructor_fromCollection() {
        List<String> list = Arrays.asList("a", "b", "a");
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>(list);
        assertEquals(2, (int) ms.get("a"));
        assertEquals(1, (int) ms.get("b"));
    }

    @Test // collection constructor with empty list
    void testConstructor_fromEmptyCollection() {
        List<String> list = Arrays.asList();
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>(list);
        assertTrue(ms.isEmpty());
    }

    @Test // array constructor with normal array
    void testConstructor_fromArray() {
        String[] arr = {"x", "y", "x"};
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>(arr);
        assertEquals(2, (int) ms.get("x"));
        assertEquals(1, (int) ms.get("y"));
    }

    @Test // array constructor with empty array
    void testConstructor_fromEmptyArray() {
        String[] arr = {};
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>(arr);
        assertTrue(ms.isEmpty());
    }

    @Test // copy constructor produces equal but independent multiset
    void testConstructor_copy() {
        multiset.add("a");
        multiset.add("a");
        multiset.add("b");
        Multiset_HashMapImpl<String> copy = new Multiset_HashMapImpl<>(multiset);
        assertEquals(multiset, copy);
        // independence: modifying copy does not affect original
        copy.add("c");
        assertNull(multiset.get("c"));
    }

    // ================= add(T entry) =================

    @Test // adding a new entry returns 0 (old multiplicity)
    void testAdd_newEntry() {
        int oldMult = multiset.add("a");
        assertEquals(0, oldMult);
        assertEquals(1, (int) multiset.get("a"));
    }

    @Test // adding existing entry increments multiplicity
    void testAdd_existingEntry() {
        multiset.add("a");
        int oldMult = multiset.add("a");
        assertEquals(1, oldMult);
        assertEquals(2, (int) multiset.get("a"));
    }

    @Test // adding same entry multiple times accumulates correctly
    void testAdd_multipleTimesAccumulates() {
        multiset.add("a");
        multiset.add("a");
        multiset.add("a");
        assertEquals(3, (int) multiset.get("a"));
    }

    // ================= add(T entry, int mult) =================

    @Test // adding with positive multiplicity
    void testAddWithMult_positive() {
        int oldMult = multiset.add("a", 3);
        assertEquals(0, oldMult);
        assertEquals(3, (int) multiset.get("a"));
    }

    @Test // adding with mult=0 does not change state (boundary: mult=0)
    void testAddWithMult_zero() {
        multiset.add("a", 3);
        int oldMult = multiset.add("a", 0);
        assertEquals(3, oldMult);
        assertEquals(3, (int) multiset.get("a")); // unchanged
    }

    @Test // adding with negative mult does not change state (boundary: mult<0)
    void testAddWithMult_negative() {
        multiset.add("a", 3);
        multiset.add("a", -1);
        assertEquals(3, (int) multiset.get("a")); // unchanged
    }

    @Test // adding to existing key accumulates
    void testAddWithMult_accumulates() {
        multiset.add("a", 2);
        multiset.add("a", 3);
        assertEquals(5, (int) multiset.get("a"));
    }

    // ================= addAll(Multiset) =================

    @Test // addAll with normal multiset merges correctly
    void testAddAllMultiset_normal() {
        multiset.add("a", 2);
        Multiset_HashMapImpl<String> other = new Multiset_HashMapImpl<>();
        other.add("a", 3);
        other.add("b", 1);
        multiset.addAll(other);
        assertEquals(5, (int) multiset.get("a"));
        assertEquals(1, (int) multiset.get("b"));
    }

    @Test // addAll with null does nothing
    void testAddAllMultiset_null() {
        multiset.add("a");
        multiset.addAll((Multiset_HashMapImpl<String>) null);
        assertEquals(1, (int) multiset.get("a"));
        assertEquals(1, multiset.size());
    }

    // ================= addAll(Collection) =================

    @Test // addAll collection with normal list
    void testAddAllCollection_normal() {
        multiset.addAll(Arrays.asList("a", "b", "a"));
        assertEquals(2, (int) multiset.get("a"));
        assertEquals(1, (int) multiset.get("b"));
    }

    @Test // addAll collection with null does nothing
    void testAddAllCollection_null() {
        multiset.add("a");
        multiset.addAll((List<String>) null);
        assertEquals(1, (int) multiset.get("a"));
    }

    // ================= addAll(T[]) =================

    @Test // addAll array with normal elements
    void testAddAllArray_normal() {
        multiset.addAll(new String[]{"x", "x", "y"});
        assertEquals(2, (int) multiset.get("x"));
        assertEquals(1, (int) multiset.get("y"));
    }

    @Test // addAll array with null does nothing
    void testAddAllArray_null() {
        multiset.add("a");
        multiset.addAll((String[]) null);
        assertEquals(1, (int) multiset.get("a"));
    }

    // ================= remove(Object key) =================

    @Test // removing an entry with multiplicity > 1 decrements it
    void testRemove_decrementsMult() {
        multiset.add("a", 3);
        Integer oldMult = multiset.remove("a");
        assertEquals(3, (int) oldMult);
        assertEquals(2, (int) multiset.get("a"));
    }

    @Test // removing entry with multiplicity = 1 deletes the key (boundary: mult=1)
    void testRemove_deletesWhenMultOne() {
        multiset.add("a");
        multiset.remove("a");
        assertNull(multiset.get("a"));
    }

    @Test // removing a non-existent key returns null
    void testRemove_nonExistentKey() {
        Integer result = multiset.remove("z");
        assertNull(result);
    }

    // ================= remove(T key, int mult) =================

    @Test // partial remove reduces multiplicity
    void testRemoveWithMult_partial() {
        multiset.add("a", 5);
        int oldMult = multiset.remove("a", 2);
        assertEquals(5, oldMult);
        assertEquals(3, (int) multiset.get("a"));
    }

    @Test // removing more than present removes the key entirely (boundary: mult > present)
    void testRemoveWithMult_moreThanPresent() {
        multiset.add("a", 2);
        multiset.remove("a", 5);
        assertNull(multiset.get("a"));
    }

    @Test // removing exact count removes the key (boundary: mult = present)
    void testRemoveWithMult_exactCount() {
        multiset.add("a", 3);
        multiset.remove("a", 3);
        assertNull(multiset.get("a"));
    }

    @Test // removing from non-existent key returns 0
    void testRemoveWithMult_nonExistentKey() {
        int result = multiset.remove("z", 1);
        assertEquals(0, result);
    }

    // ================= removeAll(T key) =================

    @Test // removeAll removes the key entirely
    void testRemoveAll_existingKey() {
        multiset.add("a", 4);
        int removed = multiset.removeAll("a");
        assertEquals(4, removed);
        assertNull(multiset.get("a"));
    }

    @Test // removeAll on non-existent key returns 0
    void testRemoveAll_nonExistentKey() {
        int result = multiset.removeAll("z");
        assertEquals(0, result);
    }

    // ================= intersect =================

    @Test // intersection of overlapping multisets
    void testIntersect_overlapping() {
        multiset.add("a", 3);
        multiset.add("b", 2);
        Multiset_HashMapImpl<String> other = new Multiset_HashMapImpl<>();
        other.add("a", 2);
        other.add("c", 5);
        Multiset_HashMapImpl<String> result = (Multiset_HashMapImpl<String>) multiset.intersect(other);
        assertEquals(2, (int) result.get("a")); // min(3, 2)
        assertNull(result.get("b"));             // b not in other
        assertNull(result.get("c"));             // c not in this
    }

    @Test // intersection with null returns empty multiset
    void testIntersect_null() {
        multiset.add("a", 2);
        Multiset_HashMapImpl<String> result = (Multiset_HashMapImpl<String>) multiset.intersect(null);
        assertTrue(result.isEmpty());
    }

    @Test // intersection with disjoint multiset returns empty
    void testIntersect_disjoint() {
        multiset.add("a", 2);
        Multiset_HashMapImpl<String> other = new Multiset_HashMapImpl<>();
        other.add("b", 3);
        Multiset_HashMapImpl<String> result = (Multiset_HashMapImpl<String>) multiset.intersect(other);
        assertTrue(result.isEmpty());
    }

    @Test // intersection with itself returns equal multiset
    void testIntersect_withItself() {
        multiset.add("a", 3);
        multiset.add("b", 1);
        Multiset_HashMapImpl<String> result = (Multiset_HashMapImpl<String>) multiset.intersect(multiset);
        assertEquals(3, (int) result.get("a"));
        assertEquals(1, (int) result.get("b"));
    }

    // ================= totalCount =================

    @Test // totalCount of empty multiset is 0 (boundary)
    void testTotalCount_empty() {
        assertEquals(0, multiset.totalCount());
    }

    @Test // totalCount sums all multiplicities
    void testTotalCount_normal() {
        multiset.add("a", 3);
        multiset.add("b", 2);
        assertEquals(5, multiset.totalCount());
    }

    @Test // totalCount with single entry multiplicity 1 (boundary: min valid)
    void testTotalCount_singleEntry() {
        multiset.add("a");
        assertEquals(1, multiset.totalCount());
    }

    // ================= toList =================

    @Test // toList of empty multiset returns empty list
    void testToList_empty() {
        assertTrue(multiset.toList().isEmpty());
    }

    @Test // toList size equals totalCount
    void testToList_sizeEqualsTotalCount() {
        multiset.add("a", 2);
        multiset.add("b", 3);
        List<String> list = multiset.toList();
        assertEquals(multiset.totalCount(), list.size());
    }

    @Test // toList contains correct elements
    void testToList_containsCorrectElements() {
        multiset.add("a", 2);
        multiset.add("b", 1);
        List<String> list = multiset.toList();
        long countA = list.stream().filter(e -> e.equals("a")).count();
        long countB = list.stream().filter(e -> e.equals("b")).count();
        assertEquals(2, countA);
        assertEquals(1, countB);
    }

    // ================= toString =================

    @Test // toString on empty multiset returns "{}"
    void testToString_empty() {
        assertEquals("{}", multiset.toString());
    }

    @Test // toString on single entry with multiplicity 1 (no "^")
    void testToString_singleEntryNoExponent() {
        multiset.add("a");
        String str = multiset.toString();
        assertTrue(str.contains("a"));
        assertFalse(str.contains("^"));
    }

    @Test // toString on entry with multiplicity > 1 includes "^"
    void testToString_multipleMultiplicityHasExponent() {
        multiset.add("a", 3);
        String str = multiset.toString();
        assertTrue(str.contains("a^3"));
    }

    // ================= equals =================

    @Test // two empty multisets are equal (boundary)
    void testEquals_bothEmpty() {
        Multiset_HashMapImpl<String> other = new Multiset_HashMapImpl<>();
        assertEquals(multiset, other);
    }

    @Test // equal multisets with same elements and multiplicities
    void testEquals_equalMultisets() {
        multiset.add("a", 2);
        multiset.add("b", 1);
        Multiset_HashMapImpl<String> other = new Multiset_HashMapImpl<>();
        other.add("a", 2);
        other.add("b", 1);
        assertEquals(multiset, other);
    }

    @Test // multisets with different multiplicities are not equal
    void testEquals_differentMultiplicities() {
        multiset.add("a", 2);
        Multiset_HashMapImpl<String> other = new Multiset_HashMapImpl<>();
        other.add("a", 3);
        assertNotEquals(multiset, other);
    }

    @Test // multisets with different keys are not equal
    void testEquals_differentKeys() {
        multiset.add("a", 1);
        Multiset_HashMapImpl<String> other = new Multiset_HashMapImpl<>();
        other.add("b", 1);
        assertNotEquals(multiset, other);
    }

    @Test // multisets with different sizes are not equal
    void testEquals_differentSizes() {
        multiset.add("a", 1);
        multiset.add("b", 1);
        Multiset_HashMapImpl<String> other = new Multiset_HashMapImpl<>();
        other.add("a", 1);
        assertNotEquals(multiset, other);
    }

    @Test // equals with null returns false
    void testEquals_null() {
        assertNotEquals(null, multiset);
    }

    @Test // equals with non-Multiset object returns false
    void testEquals_nonMultisetObject() {
        assertNotEquals("not a multiset", multiset);
    }

    // ================= hashCode =================

    @Test // hashCode throws IllegalStateException
    void testHashCode_throwsException() {
        multiset.add("a");
        assertThrows(IllegalStateException.class, () -> multiset.hashCode());
    }
}