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
import ru.job4j.accidents.service.ParticipatesService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidents;
    private final AccidentTypeService types;
    private final RuleService rules;
    private final ParticipatesService participates;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", types.findAll());
        model.addAttribute("rules", rules.findAll());
        return "createAccident";
    }

    @GetMapping("/oneAccident")
    public String viewAccident(@RequestParam("id") int id, Model model) {
        Optional<Accident> accidentOptional = accidents.findAccidentById(id);
        if (accidentOptional.isEmpty()) {
            return "404";
        }
        model.addAttribute("accident", accidentOptional.get());
        model.addAttribute("types", types.findAll());
        model.addAttribute("rules", rules.findAll());
        return "oneAccident";
    }

    @GetMapping("/accidentsDelete/{id}")
    public String delete(Model model, @PathVariable int id) {
        participates.deleteByAccidentId(id);
        accidents.deleteById(id);
        return "redirect:/index";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        String[] typeId = req.getParameterValues("type.id");
        Set<Rule> ruleSet = new HashSet<>();
        Arrays.stream(ids).forEach(x -> {
            int ruleId = Integer.parseInt(x);
            Optional<Rule> optRule = rules.findRuleById(ruleId);
            if (optRule.isPresent()) {
                ruleSet.add(optRule.get());
            }
        });
        accident.setRules(ruleSet);
        Optional<AccidentType> type = types.findAccidentTypeById(Integer.parseInt(typeId[0]));
        if (type.isPresent()) {
            accident.setType(type.get());
        }
        Optional<Accident> optAccident = accidents.save(accident);
        if (optAccident.isPresent()) {
            accident = optAccident.get();
            for (Rule x : accident.getRules()) {
                participates.save(accident.getId(), x.getId());
            }
        }
        return "redirect:/index";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        String[] typeId = req.getParameterValues("type.id");
        Set<Rule> ruleSet = new HashSet<>();
        Arrays.stream(ids).forEach(x -> {
            Optional<Rule> rule = rules.findRuleById(Integer.parseInt(x));
            rule.ifPresent(ruleSet::add);
        });
        accident.setRules(ruleSet);
        Optional<AccidentType> type = types.findAccidentTypeById(Integer.parseInt(typeId[0]));
        if (type.isPresent()) {
            accident.setType(type.get());
        }
        if (!accidents.update(accident)) {
            return "404";
        }
        participates.deleteByAccidentId(accident.getId());
        accident.getRules().forEach(x -> participates.save(accident.getId(), x.getId()));
        return "redirect:/index";
    }
}