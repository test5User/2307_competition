package by.itclass;

import by.itclass.model.Cat;
import by.itclass.model.Dog;
import by.itclass.model.Genus;
import by.itclass.utils.CompetitionUtils;

import java.util.HashMap;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        var cats = new TreeSet<Cat>();
        var dogs = new TreeSet<Dog>();
        var errors = new HashMap<String, String>();

        CompetitionUtils.parseFile(cats, dogs, errors);

        cats.forEach(System.out::println);
        System.out.println("__________________________________");
        dogs.forEach(System.out::println);
    }
}
