package com.alten.util;

import com.alten.exception.EmptySpeciesException;
import com.alten.exception.TailedAnimalsNotFoundException;
import com.alten.exception.WingedAnimalsNotFoundException;
import com.alten.model.*;

import java.time.LocalDate;
import java.util.*;

public class Zoo {

    private Map<Class<?extends Animal>, List<Animal>> animalsMap;

    public Zoo() {
        this.animalsMap = new HashMap<>();
    }

    public void addAnimal(Animal animal) {
        Class<?extends Animal> species = animal.getClass();
        animalsMap.computeIfAbsent(species, k -> new ArrayList<>()).add(animal);
    }

    public void populateZoo() {
        addAnimal(new Lion("Simba", "Zebra", 2, LocalDate.of(2023, 10, 9), 1.50f, 100f, 0.50f));
        addAnimal(new Lion("Mufasa", "Buffalo", 8, LocalDate.of(2019, 10, 10), 2.50f, 200f, 1.00f));
        addAnimal(new Lion("Nala", "Zebra", 4, LocalDate.of(2018, 10, 7), 1.70f, 155.5f, 0.75f));

        addAnimal(new Tiger("Diego", "Gazzelle", 1, LocalDate.of(2023, 10, 10), 1.40f, 50f, 0.20f));
        addAnimal(new Tiger("Red", "Antelope", 5, LocalDate.of(2019, 10, 6), 1.80f, 150f, 0.50f));
        addAnimal(new Tiger("Black", "Antelope", 7, LocalDate.of(2020, 10, 10), 2.00f, 270f, 0.80f));

        addAnimal(new Eagle("Olympia", "Rabbit", 3, LocalDate.of(2019, 10, 5), 0.75f, 3f, 1.80f));
        addAnimal(new Eagle("Sky", "Rabbit", 3, LocalDate.of(2018, 10, 3), 0.50f, 4f, 1.60f));
        addAnimal(new Eagle("Blue", "Fish", 4, LocalDate.of(2019, 10, 10), 0.60f, 2f, 2.15f));
    }


    private List<Animal> findAllAnimalsBySpecies(Class<? extends Animal> animalType) {
        return animalsMap.getOrDefault(animalType, Collections.emptyList());
    }

    public Animal findHighestAnimal(Class<? extends Animal> species) throws EmptySpeciesException {
        List<Animal> matchingAnimals = findAllAnimalsBySpecies(species);

        return matchingAnimals.stream().max(Comparator.comparing(Animal::getHeight))
                .orElseThrow(() -> new EmptySpeciesException("There are no animals for the selected species"));
    }

    public Animal findShortestAnimal(Class<? extends Animal> species) throws EmptySpeciesException {
        List<Animal> matchingAnimals = findAllAnimalsBySpecies(species);

        return matchingAnimals.stream().min(Comparator.comparing(Animal::getHeight))
                .orElseThrow(() -> new EmptySpeciesException("There are no animals for the selected species"));
    }

    public Animal findHeaviestAnimal(Class<? extends Animal> species) throws EmptySpeciesException {
        List<Animal> matchingAnimals = findAllAnimalsBySpecies(species);

        return matchingAnimals.stream().max(Comparator.comparing(Animal::getWeight))
                .orElseThrow(() -> new EmptySpeciesException("There are no animals for the selected species"));
    }

    public Animal findLightestAnimal(Class<? extends Animal> species) throws EmptySpeciesException {
        List<Animal> matchingAnimals = findAllAnimalsBySpecies(species);

        return matchingAnimals.stream().min(Comparator.comparing(Animal::getWeight))
                .orElseThrow(() -> new EmptySpeciesException("There are no animals for the selected species"));
    }

    public Animal findLargestWingspanAnimal() throws WingedAnimalsNotFoundException {
        return animalsMap.values().stream().flatMap(List::stream)
                .filter(animal -> animal instanceof WingedAnimal)
                .map(animal -> (WingedAnimal) animal)
                .max(Comparator.comparing(WingedAnimal::getWingspan))
                .orElseThrow(() -> new WingedAnimalsNotFoundException("There are no animals with wing in the zoo"));
    }

    public Animal findLongestTailAnimal() throws TailedAnimalsNotFoundException {
        return animalsMap.values().stream().flatMap(List::stream)
                .filter(animal -> animal instanceof TailedAnimal)
                .map(animal -> (TailedAnimal) animal)
                .max(Comparator.comparing(TailedAnimal::getTailLenght))
                .orElseThrow(() -> new TailedAnimalsNotFoundException("There are no animals with tail in the zoo"));
    }
}