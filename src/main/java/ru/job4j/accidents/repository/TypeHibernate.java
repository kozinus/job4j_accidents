package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

@Repository
@AllArgsConstructor
public class TypeHibernate {
    private final SessionFactory sf;

    public AccidentType save(AccidentType type) {
        try (Session session = sf.openSession()) {
            session.persist(type);
            return type;
        }
    }

    public List<AccidentType> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from AccidentType", AccidentType.class)
                    .list();
        }
    }

    public boolean update(AccidentType type) {
        try (Session session = sf.openSession()) {
            session.merge(type);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public AccidentType findById(int id) {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from AccidentType where id = :fId", AccidentType.class)
                    .setParameter("fId", id)
                    .getSingleResult();
        }
    }

    public boolean deleteById(int id) {
        try (Session session = sf.openSession()) {
            var sq = session.createQuery("delete from AccidentType where id = :fId");
            sq.setParameter("fId", id);
            return sq.executeUpdate() != 0;
        }
    }
}