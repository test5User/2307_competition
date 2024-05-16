package by.itclass.utils;

import by.itclass.exceptions.CompetitionException;
import by.itclass.model.Cat;
import by.itclass.model.Dog;
import lombok.experimental.UtilityClass;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

@UtilityClass
public class CompetitionUtils {
    private static final String PATH_TO_FILE = "src/by/itclass/resources/animals.txt";

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
            e.printStackTrace();
        }
    }
}
