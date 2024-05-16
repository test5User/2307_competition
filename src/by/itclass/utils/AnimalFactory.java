package by.itclass.utils;

import by.itclass.exceptions.CompetitionException;
import by.itclass.model.Animal;
import by.itclass.model.Cat;
import by.itclass.model.Dog;
import by.itclass.model.Genus;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class AnimalFactory {
    private static final String DELIMITER = "[;,]";
    private static final String CHIP_REGEX = "(?=\\d{15}\\b)(?:112|643)09(?:81|56)\\d{8}";
    private static final String EMAIL_REGEX = "^\\S+@\\S+\\.\\S+$";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-y");

    public static Animal getInstance(String textString) throws CompetitionException {
        var slittedArray = textString.split(DELIMITER);
        try {
            var chipNumber = Long.parseLong(validateStringMatches(slittedArray[0], CHIP_REGEX));
            var name = validateStringByEmpty(slittedArray[2]);
            var birthDate = LocalDate.parse(slittedArray[3], FORMATTER);
            var breed = validateStringByEmpty(slittedArray[4]);
            var email = validateStringMatches(slittedArray[5], EMAIL_REGEX);
            return slittedArray[1].equalsIgnoreCase("cat")
                    ? new Cat(chipNumber, Genus.CAT, name, birthDate, breed, email)
                    : new Dog(chipNumber, Genus.of(slittedArray[1]), name, birthDate, breed, email);
        } catch (IllegalStateException e) {
            throw new CompetitionException(e, textString);
        }
    }

    private static String validateStringMatches(String value, String regex) {
        if (value.matches(regex)) {
            return value;
        } else {
            throw new IllegalStateException("Chip Number or email has invalid format");
        }
    }

    private static String validateStringByEmpty(String value) {
        if (!value.isEmpty()) {
            return value;
        } else {
            throw new IllegalStateException("Name or breed is empty");
        }
    }
}
