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
        try {
            crudStore.run(session -> session.merge(accident));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<Accident> findAccidentById(int id) {
        return crudStore.optional("select distinct ac from Accident ac JOIN FETCH ac.rules where ac.id = :fId",
                Accident.class,
                Map.of("fId", id));
    }

    public boolean deleteById(int id) {
        try {
            crudStore.run(
                    "delete from Accident where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}