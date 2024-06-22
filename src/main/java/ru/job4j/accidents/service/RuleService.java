package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleMem;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleMem ruleMem;

    public Optional<Rule> save(Rule rule) {
        return ruleMem.save(rule);
    }

    public boolean update(Rule rule) {
        return ruleMem.update(rule);
    }

    public boolean deleteById(int id) {
        return ruleMem.deleteById(id);
    }

    public Optional<Rule> findRuleById(int id) {
        return ruleMem.findById(id);
    }

    public Collection<Rule> findAll() {
        return ruleMem.findAll();
    }
}
