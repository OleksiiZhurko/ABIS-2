package com.warehouse.warehouse.service;

import com.warehouse.warehouse.db.Database;
import com.warehouse.warehouse.dto.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final Database database;

    @Autowired
    public SearchService(final Database database) {
        this.database = database;
    }

    public List<Person> findAll() {
        return database.getPeople();
    }

    public List<Person> findAllBySurname(final String surname) {
        return sortByNameDesc(
                findAll().stream()
                    .filter(person -> {
                        String lastName = person.getName().substring(0, person.getName().indexOf(' '));

                        if (lastName.length() + 1 == surname.length() || lastName.length() == surname.length()) {
                            return stringMatch(lastName.toLowerCase(), surname.toLowerCase());
                        } else if (lastName.length() - 1 == surname.length()) {
                            return stringMatch(surname.toLowerCase(), lastName.toLowerCase());
                        } else {
                            return false;
                        }
                    })
                    .collect(Collectors.toList()),
                surname
        );
    }

    public boolean deleteByDocument(Long doc) {
        List<Person> people = findAll();
        return people.remove(
                people.stream()
                        .filter(person -> person.getDocument().equals(doc))
                        .findFirst()
                        .get()
        );
    }

    public List<Person> sortByDocument(List<Person> people) {
        people.sort(Comparator.comparingLong(Person::getDocument));

        return people;
    }

    public List<Person> sortByName(List<Person> people) {
        people.sort(Comparator.comparing(Person::getName));

        return people;
    }

    private List<Person> sortByNameDesc(List<Person> people, String surname) {
        if (people.stream()
                .anyMatch(person ->
                        person.getName().substring(0, person.getName().indexOf(' ')).toLowerCase()
                                .equals(surname.toLowerCase()))) {
            return people.stream()
                    .filter(person -> person.getName().substring(0, person.getName().indexOf(' ')).toLowerCase()
                            .equals(surname.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return people;
    }

    private boolean stringMatch(String first, String second) {
        if (first.equals(second)) {
            return true;
        }

        int mistake = 0;

        for (int i = 0; i < first.length(); i++) {
            if (first.charAt(i) != second.charAt(i)) {
                if (mistake++ > 0) {
                    return false;
                }
            }
        }

        return true;
    }

}
