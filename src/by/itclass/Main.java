package by.itclass;

import by.itclass.model.Cat;
import by.itclass.model.Dog;

import java.util.HashMap;
import java.util.TreeSet;

import static by.itclass.utils.CompetitionUtils.*;

public class Main {
    public static void main(String[] args) {
        var cats = new TreeSet<Cat>();
        var dogs = new TreeSet<Dog>();
        var errors = new HashMap<String, String>();

        parseFile(cats, dogs, errors);

        printResults(cats, dogs, errors);

        System.out.println("_______________________________________________________________________");

        var youngCats = filterAnimals(cats, true);
        var oldCats = filterAnimals(cats, false);
        var youngDogs = filterAnimals(dogs, true);
        var oldDogs = filterAnimals(dogs, false);

        printResults(youngCats, youngDogs, oldCats, oldDogs);
    }
}
