package numberrangesummarizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the NumberRangeSummarizer interface.
 *
 * Takes a comma separated list of numbers and groups the sequential numbers into ranges.
 * Example: "1,3,6,7,8,12,13,14,15" becomes "1, 3, 6-8, 12-15"
 */
public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {

    /**
     * Converts a comma separated string into a list of integers.
     * Returns an empty list if the input is null or empty.
     *
     * @throws IllegalArgumentException if one of the values is not a whole number
     */
    @Override
    public Collection<Integer> collect(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(this::toNumber)
                .collect(Collectors.toList());
    }

    /**
     * Sorts the numbers, removes duplicates and groups sequential numbers into ranges.
     * Returns an empty string if the collection is null or empty.
     */
    @Override
    public String summarizeCollection(Collection<Integer> input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        List<Integer> sortedNumbers = input.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<Range> ranges = groupIntoRanges(sortedNumbers);

        return ranges.stream()
                .map(Range::toString)
                .collect(Collectors.joining(", "));
    }

    /**
     * Goes through the sorted numbers once. While the next number follows on from the
     * current range, the range is extended. Otherwise the range is saved and a new one starts.
     */
    private List<Range> groupIntoRanges(List<Integer> sortedNumbers) {
        List<Range> ranges = new ArrayList<>();

        int start = sortedNumbers.get(0);
        int end = sortedNumbers.get(0);

        for (int i = 1; i < sortedNumbers.size(); i++) {
            int current = sortedNumbers.get(i);

            if (current == end + 1) {
                end = current;
            } else {
                ranges.add(new Range(start, end));
                start = current;
                end = current;
            }
        }

        // add the last range
        ranges.add(new Range(start, end));

        return ranges;
    }

    private Integer toNumber(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number in input: '" + value + "'");
        }
    }
}
