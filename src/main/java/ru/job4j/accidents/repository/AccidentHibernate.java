package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    private final SessionFactory sf;

    public Accident save(Accident accident) {
        try (Session session = sf.openSession()) {
            session.persist(accident);
            return accident;
        }
    }

    public List<Accident> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("select distinct ac from Accident ac JOIN FETCH ac.rules", Accident.class)
                    .list();
        }
    }

    public boolean update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.merge(accident);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Accident findAccidentById(int id) {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("select distinct ac from Accident ac LEFT JOIN FETCH ac.rules where ac.id = :fId", Accident.class)
                    .setParameter("fId", id)
                    .getSingleResult();
        }
    }

    public boolean deleteById(int id) {
        try (Session session = sf.openSession()) {
            var sq = session.createQuery("delete from Accident where id = :fId");
            sq.setParameter("fId", id);
            return sq.executeUpdate() != 0;
        }
    }
}