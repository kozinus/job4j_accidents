package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.mapper.AccidentRowMapper;
import ru.job4j.accidents.model.Accident;

import java.util.*;

@Repository
@Order(1)
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public Optional<Accident> save(Accident accident) {
        boolean flag = false;
        flag = jdbc.update("insert into accidents (ac_name, ac_text, ac_address, type_id) values (?, ?, ?, ?)",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId()) != 0;

        if (flag) {
            SqlRowSet rowSet = jdbc.queryForRowSet("select max(id) from accidents");
            rowSet.next();
            accident.setId(rowSet.getInt("max"));
        }
        return Optional.of(accident);
    }

    public boolean deleteById(int id) {
        return jdbc.update("delete from accidents where id = (?)",
                id) != 0;
    }

    public boolean update(Accident accident) {
        return jdbc.update("update accidents set ac_name = (?), ac_text = (?), ac_address = (?), type_id = (?) "
                + "where id = (?)",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId(),
                accident.getId()) != 0;
    }

    public Optional<Accident> findById(int id) {
        Map<Integer, Accident> accidentMap = new HashMap<>();
        jdbc.query(
                "SELECT * FROM accidents AS ac "
                        + "LEFT JOIN accident_types AS at ON ac.type_id = at.id "
                        + "LEFT JOIN participates AS p ON ac.id = p.accident_id "
                        + "LEFT JOIN accident_rules AS r ON p.rule_id = r.id "
                        + "WHERE ac.id = "
                        + id,
                new AccidentRowMapper(accidentMap)
        );
        return Optional.ofNullable(accidentMap.get(id));
    }

    public Collection<Accident> findAll() {
        Map<Integer, Accident> accidentMap = new HashMap<>();
        jdbc.query(
                "SELECT * FROM accidents AS ac "
                        + "LEFT JOIN accident_types AS at ON ac.type_id = at.id "
                        + "LEFT JOIN participates AS p ON ac.id = p.accident_id "
                        + "LEFT JOIN accident_rules AS r ON p.rule_id = r.id",
                new AccidentRowMapper(accidentMap)
        );
        return accidentMap.values();
    }
}