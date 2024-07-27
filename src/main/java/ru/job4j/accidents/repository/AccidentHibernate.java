package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    private final CrudStore crudStore;

    public Accident save(Accident accident) {
        crudStore.run(session -> session.persist(accident));
        return accident;
    }

    public List<Accident> findAll() {
        return crudStore.query("select distinct ac from Accident ac JOIN FETCH ac.rules", Accident.class);
    }

    public boolean update(Accident accident) {
        boolean result;
        try {
            crudStore.run(session -> session.merge(accident));
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public Optional<Accident> findAccidentById(int id) {
        return crudStore.optional("select distinct ac from Accident ac JOIN FETCH ac.rules where ac.id = :fId",
                Accident.class,
                Map.of("fId", id));
    }

    public boolean deleteById(int id) {
        boolean flag;
        try {
            crudStore.run(
                    "delete from Accident where id = :fId",
                    Map.of("fId", id)
            );
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}