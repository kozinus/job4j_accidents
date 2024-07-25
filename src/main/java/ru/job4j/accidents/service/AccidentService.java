package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.repository.AccidentHibernate;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentHibernate accidentMem;

    public Accident save(Accident accident) {
        return accidentMem.save(accident);
    }

    public boolean update(Accident accident) {
        return accidentMem.update(accident);
    }

    public boolean deleteById(int id) {
        return accidentMem.deleteById(id);
    }

    public Optional<Accident> findAccidentById(int id) {
        return Optional.ofNullable(accidentMem.findAccidentById(id));
    }

    public Collection<Accident> findAll() {
        return accidentMem.findAll();
    }
}
