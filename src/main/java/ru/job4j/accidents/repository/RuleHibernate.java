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
        try {
            crudStore.run(session -> session.merge(rule));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<Rule> findById(int id) {
        return crudStore.optional("from Rule where id = :fId", Rule.class,
                Map.of("fId", id));
    }

    public boolean deleteById(int id) {
        try {
            crudStore.run(
                    "delete from Rule where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}