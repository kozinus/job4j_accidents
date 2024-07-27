package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TypeHibernate {
    private final CrudStore crudStore;

    public AccidentType save(AccidentType type) {
        crudStore.run(session -> session.persist(type));
        return type;
    }

    public List<AccidentType> findAll() {
        return crudStore.query("from AccidentType", AccidentType.class);
    }

    public boolean update(AccidentType type) {
        boolean result;
        try {
            crudStore.run(session -> session.merge(type));
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public Optional<AccidentType> findById(int id) {
        return crudStore.optional("from AccidentType where id = :fId", AccidentType.class,
                Map.of("fId", id));
    }

    public boolean deleteById(int id) {
        boolean flag;
        try {
            crudStore.run(
                    "delete from AccidentType where id = :fId",
                    Map.of("fId", id)
            );
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}