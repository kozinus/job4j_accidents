package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;

@Repository
@AllArgsConstructor
public class RuleHibernate {
    private final SessionFactory sf;

    public Rule save(Rule rule) {
        try (Session session = sf.openSession()) {
            session.persist(rule);
            return rule;
        }
    }

    public List<Rule> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Rule", Rule.class)
                    .list();
        }
    }

    public boolean update(Rule rule) {
        try (Session session = sf.openSession()) {
            session.merge(rule);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Rule findById(int id) {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Rule where id = :fId", Rule.class)
                    .setParameter("fId", id)
                    .getSingleResult();
        }
    }

    public boolean deleteById(int id) {
        try (Session session = sf.openSession()) {
            var sq = session.createQuery("delete from Rule where id = :fId");
            sq.setParameter("fId", id);
            return sq.executeUpdate() != 0;
        }
    }
}