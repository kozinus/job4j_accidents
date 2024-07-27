package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleRepository ruleMem;

    public Optional<Rule> save(Rule rule) {
        return Optional.of(ruleMem.save(rule));
    }

    public void update(Rule rule) {
        ruleMem.save(rule);
    }

    public void deleteById(int id) {
        ruleMem.deleteById(id);
    }

    public Optional<Rule> findRuleById(int id) {
        return ruleMem.findById(id);
    }

    public Collection<Rule> findAll() {
        return (List<Rule>) ruleMem.findAll();
    }
}
