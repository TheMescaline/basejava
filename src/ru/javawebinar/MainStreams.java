package ru.javawebinar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainStreams {
    public static void main(String[] args) {
        int[] valuesForMinValue = {7, 4, 1, 2, 2, 3, 4, 4, 4, 5};
        List<Integer> valuesForOddOrEven = Arrays.asList(9, -8, 1, 3, 6);
        System.out.println(minValue(valuesForMinValue));
        System.out.println(oddOrEven(valuesForOddOrEven));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, value) -> acc * 10 + value);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        final List<Integer> odd = new ArrayList<>();
        final List<Integer> even = new ArrayList<>();
        int sum = integers.stream()
                .peek(value -> {
                    if (value % 2 == 0) {
                        even.add(value);
                    } else {
                        odd.add(value);
                    }
                })
                .reduce(0, (acc, x) -> acc + x);
        return sum % 2 == 0 ? odd : even;
    }
}
