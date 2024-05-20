package by.itclass.utils;

import by.itclass.exceptions.CompetitionException;
import by.itclass.model.Animal;
import by.itclass.model.Cat;
import by.itclass.model.Dog;
import lombok.experimental.UtilityClass;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public class CompetitionUtils {
    private static final String PATH_TO_FILE = "src/by/itclass/resources/animals.txt";
    private static final String EMAIL_REGEX = ",\\S+@\\S+\\.\\S+$";

    private static final LocalDate AGE_DELIMITER = LocalDate.now().minusYears(4);
    private static final Predicate<Animal> YOUNG_PREDICATE = it -> it.getBirthDate().isAfter(AGE_DELIMITER);
    private static final Predicate<Animal> OLD_PREDICATE = it -> it.getBirthDate().isBefore(AGE_DELIMITER);

    public static void parseFile(TreeSet<Cat> cats, TreeSet<Dog> dogs,
                                 Map<String, String> errors) {
        try (var sc = new Scanner(new FileReader(PATH_TO_FILE))){
            while (sc.hasNextLine()) {
                fillingCollection(sc.nextLine(), cats, dogs, errors);
            }
        } catch (FileNotFoundException e) {
            System.err.printf("File not found by path %s%n", PATH_TO_FILE);
            System.exit(1);
        }
    }

    private static void fillingCollection(String textLine, TreeSet<Cat> cats, TreeSet<Dog> dogs,
                                          Map<String, String> errors) {
        try {
            var animal = AnimalFactory.getInstance(textLine);
            if (animal instanceof Cat) {
                cats.add((Cat) animal);
            } else {
                dogs.add((Dog) animal);
            }
        } catch (CompetitionException e) {
            processException(errors, e);
        }
    }

    private static void processException(Map<String, String> errors, CompetitionException e) {
        var pattern = Pattern.compile(EMAIL_REGEX);
        var matcher = pattern.matcher(e.getErrorLine());
        if (matcher.find()) {
            errors.put(matcher.group().replace(",", ""), String.format("Error in string \"%s\" - %s", e.getErrorLine(), e.getCause().getMessage()));
        }
    }

    public static void printResults(TreeSet<Cat> cats, TreeSet<Dog> dogs,
                                    Map<String, String> errors) {
        System.out.println("Cats collection size: " + cats.size());
        printTreeSet(cats);
        System.out.println("Dogs collection size: " + dogs.size());
        printTreeSet(dogs);
        printMap(errors);
    }

    private static <T> void printTreeSet(TreeSet<T> treeSet) {
        treeSet.forEach(System.out::println);
    }

    private static void printMap(Map<String, String> errors) {
        if (!errors.isEmpty()) {
            System.out.println("Errors size: " + errors.size());
            errors.forEach((key, value) -> System.out.println(key + "; " + value));
        }
    }

    public static <T extends Animal> TreeSet<T> filterAnimals(TreeSet<T> participants, boolean isYoung) {
        return participants.stream()
                .filter(isYoung ? YOUNG_PREDICATE : OLD_PREDICATE)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public static void printResults(TreeSet<Cat> youngCats, TreeSet<Dog> youngDogs,
                                    TreeSet<Cat> oldCats, TreeSet<Dog> oldDogs) {
        System.out.println("First Day participants:");
        System.out.println("Cats collection size: " + youngCats.size());
        printTreeSet(youngCats);
        System.out.println("Dogs collection size: " + youngDogs.size());
        printTreeSet(youngDogs);
        System.out.println("Second Day participants:");
        System.out.println("Cats collection size: " + oldCats.size());
        printTreeSet(oldCats);
        System.out.println("Dogs collection size: " + oldDogs.size());
        printTreeSet(oldDogs);
    }
}
