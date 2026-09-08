package io.github.sidneyroberto9.rotom.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RotomCollectionUtilsTest {

    private final RotomCollectionUtils collections = new RotomCollectionUtils();

    @Test
    void removeDuplicatesPreservesFirstOccurrenceOrder() {
        assertEquals(
                List.of("a", "b", "c"),
                collections.removeDuplicates(List.of("a", "b", "a", "c", "b"))
        );
    }

    @Test
    void removeDuplicatesReturnsEmptyListForEmptyInput() {
        assertEquals(List.of(), collections.removeDuplicates(List.of()));
    }

    @Test
    void removeDuplicatesThrowsOnNull() {
        assertThrows(NullPointerException.class, () -> collections.removeDuplicates(null));
    }
}
