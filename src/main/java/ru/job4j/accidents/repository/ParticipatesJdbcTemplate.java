package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Order(1)
@AllArgsConstructor
public class ParticipatesJdbcTemplate {
    private final JdbcTemplate jdbc;

    public boolean save(int accidentId, int ruleId) {
        return jdbc.update("insert into participates (accident_id, rule_id) values (?, ?)",
                accidentId, ruleId) != 0;
    }

    public boolean deleteByIds(int accidentId, int ruleId) {
        return jdbc.update("delete from participates where accident_id = (?) and rule_id = (?)",
                accidentId, ruleId) != 0;
    }

    public boolean deleteByAccidentId(int accidentId) {
        return jdbc.update("delete from participates where accident_id = (?)",
                accidentId) != 0;
    }

    public boolean deleteByRuleId(int ruleId) {
        return jdbc.update("delete from participates where rule_id = (?)",
                ruleId) != 0;
    }
}