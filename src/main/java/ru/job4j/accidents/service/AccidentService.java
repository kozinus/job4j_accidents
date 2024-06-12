package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.repository.AccidentMem;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem accidentMem;

    public Optional<Accident> save(Accident accident) {
        return accidentMem.save(accident);
    }

    public Optional<Accident> findAccidentById(int id) {
        return accidentMem.findById(id);
    }

    public Collection<Accident> findAll() {
        return accidentMem.findAll();
    }
}
