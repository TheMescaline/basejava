package ru.javawebinar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        int[] valuesForMinValue = {1, 2, 2, 3, 4, 4, 4, 5};
        List<Integer> valuesForOddOrEven = Arrays.asList(9, -8, 1, 3, 6);
        System.out.println(minValue(valuesForMinValue));
        System.out.println(oddOrEven(valuesForOddOrEven));
    }

    public static int minValue(int[] values) {
        String result = Arrays.stream(values)
                .distinct()
                .sorted()
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        return Integer.parseInt(result);
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
