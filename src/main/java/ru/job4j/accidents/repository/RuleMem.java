package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class RuleMem {
    private final AtomicInteger nextId = new AtomicInteger(4);

    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public RuleMem() {
        rules.put(1, new Rule(1, "Статья.1"));
        rules.put(2, new Rule(2, "Статья.2"));
        rules.put(3, new Rule(3, "Статья.3"));
    }

    public Optional<Rule> save(Rule rule) {
        rule.setId(nextId.getAndIncrement());
        rules.put(rule.getId(), rule);
        return Optional.of(rule);
    }

    public boolean deleteById(int id) {
        return rules.remove(id) != null;
    }

    public boolean update(Rule rule) {
        return rules.computeIfPresent(rule.getId(), (id, oldRule) -> new Rule(oldRule.getId(), rule.getName())) != null;
    }

    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    public Collection<Rule> findAll() {
        return rules.values();
    }

}
