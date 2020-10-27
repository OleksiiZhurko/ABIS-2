package com.warehouse.warehouse.service;

import com.warehouse.warehouse.db.Database;
import com.warehouse.warehouse.db.Work;
import com.warehouse.warehouse.dto.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class SearchServiceTest {

    @Test
    void findAllBySurname() {
        Database mockPeople = mock(Database.class);

        Person person1 = Person.builder()
                .name("Журко Олексій Олександрович")
                .document(1L)
                .codeId(2L)
                .work(Work.EMPLOYED)
                .build();
        Person person2 = Person.builder()
                .name("Іванов Олексій Олександрович")
                .document(2L)
                .codeId(3L)
                .work(Work.EMPLOYED)
                .build();
        Person person3 = Person.builder()
                .name("Іванова Олексій Олександрович")
                .document(2L)
                .codeId(3L)
                .work(Work.EMPLOYED)
                .build();

        when(mockPeople.getPeople()).thenReturn(List.of(person1, person2, person3));

        SearchService searchService = new SearchService(mockPeople);

        assertEquals(List.of(person1), searchService.findAllBySurname("Журко"));
        assertEquals(List.of(person1), searchService.findAllBySurname("Жарко"));
        assertEquals(List.of(person2, person3), searchService.findAllBySurname("Іванов"));
        assertEquals(List.of(person2, person3), searchService.findAllBySurname("Іванова"));
        assertEquals(List.of(), searchService.findAllBySurname("Жрко"));
        assertEquals(List.of(), searchService.findAllBySurname("Олексій"));
    }
}