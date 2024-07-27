package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RuleHibernate {
    private final CrudStore crudStore;

    public Rule save(Rule rule) {
        crudStore.run(session -> session.persist(rule));
        return rule;
    }

    public List<Rule> findAll() {
        return crudStore.query("from Rule", Rule.class);
    }

    public boolean update(Rule rule) {
        boolean result;
        try {
            crudStore.run(session -> session.merge(rule));
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public Optional<Rule> findById(int id) {
        return crudStore.optional("from Rule where id = :fId", Rule.class,
                Map.of("fId", id));
    }

    public boolean deleteById(int id) {
        boolean flag;
        try {
            crudStore.run(
                    "delete from Rule where id = :fId",
                    Map.of("fId", id)
            );
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}