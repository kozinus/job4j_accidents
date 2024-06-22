package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidents;
    private final AccidentTypeService types;
    private final RuleService rules;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        types.save(new AccidentType(1, "Две машины"));
        types.save(new AccidentType(2, "Машина и человек"));
        types.save(new AccidentType(3, "Машина и велосипед"));
        model.addAttribute("types", types.findAll());
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
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
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