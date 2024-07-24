package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@Order(1)
@AllArgsConstructor
public class TypeJdbcTemplate {
    private final JdbcTemplate jdbc;

    public Optional<AccidentType> save(AccidentType accidentType) {
        jdbc.update("insert into accident_types (at_name) values (?)",
                accidentType.getName());
        return Optional.of(accidentType);
    }

    public boolean deleteById(int id) {
        return jdbc.update("delete from accident_types where id = (?)",
                id) != 0;
    }

    public boolean update(AccidentType accidentType) {
        return jdbc.update("update accident_types set at_name = ? where id = ?",
                accidentType.getName(), accidentType.getId()) != 0;
    }

    public Optional<AccidentType> findById(int id) {
        return jdbc.queryForStream("SELECT * FROM accident_types where id = " + id,
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("at_name"));
                    return accidentType;
                }).findFirst();
    }

    public List<AccidentType> findAll() {
        return jdbc.query("SELECT * FROM accident_types",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("at_name"));
                    return accidentType;
                });
    }
}