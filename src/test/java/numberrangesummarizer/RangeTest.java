package numberrangesummarizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RangeTest {

    @Test
    public void testSingleNumberHasNoDash() {
        Range range = new Range(5, 5);
        assertEquals("5", range.toString());
    }

    @Test
    public void testRangeUsesDash() {
        Range range = new Range(6, 8);
        assertEquals("6-8", range.toString());
    }

    @Test
    public void testGetters() {
        Range range = new Range(12, 15);
        assertEquals(12, range.getStart());
        assertEquals(15, range.getEnd());
    }
}
