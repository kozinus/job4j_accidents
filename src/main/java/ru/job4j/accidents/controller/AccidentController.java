package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidents;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = new ArrayList<>();
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));
        model.addAttribute("types", types);
        return "createAccident";
    }

    @GetMapping("/oneAccident")
    public String viewAccident(@RequestParam("id") int id, Model model) {
        Optional<Accident> accidentOptional = accidents.findAccidentById(id);
        if (accidentOptional.isEmpty()) {
            return "404";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "oneAccident";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        accidents.deleteById(id);
        return "redirect:/index";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidents.save(accident);
        return "redirect:/index";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        if (accidents.update(accident)) {
            return "404";
        }
        return "redirect:/index";
    }
}