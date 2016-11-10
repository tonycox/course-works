package ru.ssau.tourism.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ssau.tourism.entities.Tour;
import ru.ssau.tourism.services.DataBaseService;

@Controller
@RequestMapping("/tours")
public class TourController {

    private final DataBaseService service;

    @Autowired
    public TourController(DataBaseService service) {
        this.service = service;
    }

    @RequestMapping
    public String home(Model m) {
        m.addAttribute("allTours", getAll());
        return "tours";
    }

    @RequestMapping("/all")
    @ResponseBody
    public Iterable<Tour> getAll() {
        return service.getTours();
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam Long id) {
        service.deleteTour(id);
        return "redirect:/tours";
    }
}
