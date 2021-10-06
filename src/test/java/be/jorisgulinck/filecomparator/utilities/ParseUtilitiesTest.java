package be.jorisgulinck.filecomparator.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParseUtilitiesTest {

    private ParseUtilities parseUtilities;

    @BeforeEach
    void setUp() {
        parseUtilities = new ParseUtilities();
    }

    @Test
    void tryParseInt() {
        assertTrue(parseUtilities.tryParseInt("22"));
        assertTrue(parseUtilities.tryParseInt("-200"));

        assertFalse(parseUtilities.tryParseInt("   22   "));
        assertFalse(parseUtilities.tryParseInt(null));
        assertFalse(parseUtilities.tryParseInt(""));
        assertFalse(parseUtilities.tryParseInt("abc"));
    }

    @Test
    void tryParseDate() {
        assertTrue(parseUtilities.tryParseDate("2014-01-12 05:33:22"));

        assertFalse(parseUtilities.tryParseDate("76.77.8765"));
    }
}