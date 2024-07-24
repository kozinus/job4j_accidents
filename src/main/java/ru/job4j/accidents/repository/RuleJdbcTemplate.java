package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;

@Repository
@Order(1)
@AllArgsConstructor
public class RuleJdbcTemplate {
    private final JdbcTemplate jdbc;

    public Optional<Rule> save(Rule rule) {
        jdbc.update("insert into accident_rules (ar_name) values (?)",
                rule.getName());
        return Optional.of(rule);
    }

    public boolean deleteById(int id) {
        return jdbc.update("delete from accident_rules where id = (?)",
                id) != 0;
    }

    public boolean update(Rule rule) {
        return jdbc.update("update accident_rules set ar_name = ? where id = ?",
                rule.getName(), rule.getId()) != 0;
    }

    public Optional<Rule> findById(int id) {
        return jdbc.queryForStream("SELECT * FROM accident_rules where id = " + id,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("ar_name"));
                    return rule;
                }).findFirst();
    }

    public List<Rule> findAll() {
        return jdbc.query("SELECT * FROM accident_rules",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("ar_name"));
                    return rule;
                });
    }
}