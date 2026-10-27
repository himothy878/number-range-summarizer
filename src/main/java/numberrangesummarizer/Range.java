package numberrangesummarizer;

/**
 * Represents a group of sequential numbers, from start to end (inclusive).
 * If start and end are the same, the range is just a single number.
 */
public class Range {

    private final int start;
    private final int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    /**
     * Returns "5" for a single number, or "6-8" for a range.
     */
    @Override
    public String toString() {
        if (start == end) {
            return String.valueOf(start);
        }
        return start + "-" + end;
    }
}
