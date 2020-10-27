package com.warehouse.warehouse.controller;

import com.warehouse.warehouse.dto.Person;
import com.warehouse.warehouse.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DefaultController {

    private final SearchService searchService;

    @Autowired
    public DefaultController(final SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(value = "")
    public String produceMainPage(Model model) {
        model.addAttribute("peoples", searchService.sortByDocument(searchService.findAll()));

        return "index";
    }

    @GetMapping(value = "", params = {"surname"})
    public @ResponseBody List<Person> getMainPageBySurname(@RequestParam final String surname) {
        return searchService.findAllBySurname(surname);
    }

    @GetMapping(value = "", params = {"sort"})
    public @ResponseBody List<Person> getMainPageBySort(@RequestParam final String sort) {
        return switch (sort) {
            case "Name" -> searchService.sortByName(searchService.findAll());
            default -> searchService.sortByDocument(searchService.findAll());
        };
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Long> deleteByDocument(@PathVariable final long id) {
        searchService.deleteByDocument(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
