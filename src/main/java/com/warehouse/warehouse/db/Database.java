package com.warehouse.warehouse.db;

import com.warehouse.warehouse.dto.Person;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

@Component
public class Database {

    private final List<Person> people = new ArrayList<>();

    private final List<Cnap> cnaps = new ArrayList<>();
    private final List<PensionFund> pensionFunds = new ArrayList<>();
    private final List<Tax> taxes = new ArrayList<>();

    public List<Person> getPeople() {
        return people;
    }

    @PostConstruct
    public void produceData() {
        String[] namesM = {
                "Матвій", "Максим", "Артем", "Данило", "Давид", "Владислав", "Олександр", "Дем'ян", "Марко", "Тимофій",
                "Денис", "Юрій", "Святослав", "Андрій", "Дмитро",
        };
        String[] namesW = {
                "Софія", "Анна", "Вікторія", "Вероніка", "Анастасія", "Яна", "Емілія", "Дарина", "Єва", "Злата", "Соломія",
                "Мілана", "Марта", "Марія", "Діана",
        };

        String[] sureNames = {
                "Мельник", "Шевченко", "Бойко", "Коваленко", "Бондаренко", "Ткаченко", "Ковальчук", "Кравченко", "Олійник",
                "Шевчук", "Коваль", "Поліщук", "Бондар", "Ткачук", "Мороз", "Марченко", "Лисенко", "Руденко", "Савченко",
                "Петренко", "Кравчук", "Клименко", "Павленко", "Савчук", "Кузьменко", "Швець", "Гаврилюк", "Харченко",
                "Пономаренко", "Мельничук", "Василенко", "Мазур", "Хоменко", "Левченко", "Карпенко",
        };

        String[] middleNamesM = {
                "Максимович", "Матвійович", "Данилович", "Артемович", "Давидович", "Владиславович", "Дем'янович",
                "Олександрович", "Маркович", "Тимофійович", "Денисович", "Юрійович", "Святославович", "Дмитрович", "Андрійович",
        };
        String[] middleNamesW = {
                "Максимівна", "Матвіївна", "Данилівна", "Артемівна", "Давидівна", "Владиславівна", "Дем'янівна",
                "Олександрівна", "Марківна", "Тимофіївна", "Денисівна", "Юріївна", "Святославівна", "Дмитрівна", "Андріївна",
        };

        Set<Long> document = new HashSet<>();
        Set<Long> codeId = new HashSet<>();
        Random rand = new Random();
        long doc;
        long code;
        String name;
        String sureName;
        String middleName;
        int age;
        Work work;

        for (int i = 0; i < 1000; i++) {
            do {
                doc = getNum(9, rand);
            } while (!document.add(doc));

            do {
                code = getNum(10, rand);
            } while (!codeId.add(code));

            age = rand.nextInt(70) + 14;

            if (age > 63) {
                age = rand.nextInt(60) + 60;
            }

            if (rand.nextInt(2) == 1) {
                name = namesW[rand.nextInt(namesW.length)];
                middleName = middleNamesW[rand.nextInt(middleNamesW.length)];
            } else {
                name = namesM[rand.nextInt(namesM.length)];
                middleName = middleNamesM[rand.nextInt(middleNamesM.length)];
            }

            sureName = sureNames[rand.nextInt(sureNames.length)];

            if (age > 90 || age < 18) {
                work = Work.UNEMPLOYED;
            } else {
                work = rand.nextInt(15) == 0 ? Work.UNEMPLOYED : Work.EMPLOYED;
            }

            cnaps.add(
                    Cnap.builder()
                            .name(sureName + " " + name + " " + middleName)
                            .age(age)
                            .document(doc)
                            .until(
                                    LocalDate.now()
                                            .plusYears(rand.nextInt(10))
                                            .plusMonths(rand.nextInt(12) + 1)
                                            .plusDays(rand.nextInt(31) + 1)
                            )
                            .build()
            );

            pensionFunds.add(
                    PensionFund.builder()
                            .name(name)
                            .sureName(sureName)
                            .middleName(middleName)
                            .document(doc)
                            .codeId(code)
                            .build()
            );

            taxes.add(
                    Tax.builder()
                            .name(name)
                            .sureName(sureName)
                            .middleName(middleName)
                            .codeId(code)
                            .status(work)
                            .build()
            );

            Cnap cnap = cnaps.get(i);
            PensionFund pensionFund = pensionFunds.stream()
                    .filter(elem -> elem.getDocument().equals(cnap.getDocument()))
                    .findFirst()
                    .get();
            Tax tax = taxes.stream()
                    .filter(elem -> elem.getCodeId().equals(pensionFund.getCodeId()))
                    .findFirst()
                    .get();

            people.add(
                    Person.builder()
                            .name(cnap.getName())
                            .document(pensionFund.getDocument())
                            .codeId(pensionFund.getCodeId())
                            .work(tax.getStatus())
                            .build()
            );
        }

        System.out.println(cnaps.stream().findFirst().get());
        System.out.println(pensionFunds.stream().findFirst().get());
        System.out.println(taxes.stream().findFirst().get());
    }

    private long getNum(int endLoop, Random rand) {
        StringBuilder res = new StringBuilder();

        for (int j = 0; j < endLoop; j++) {
            res.append(rand.nextInt(10));
        }

        return Long.parseLong(res.toString());
    }

}
