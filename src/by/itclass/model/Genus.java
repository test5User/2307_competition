package by.itclass.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum Genus {
    CAT("Cat"), DOG("Dog");

    @Getter
    private final String value;

    public static Genus of(String value) {
        return Arrays.stream(values())
                .filter(it -> it.value.equalsIgnoreCase(value))
                .findAny()
                .orElse(null);
    }
}
