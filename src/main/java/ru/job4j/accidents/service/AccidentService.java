package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentRepository accidentMem;

    public Accident save(Accident accident) {
        return accidentMem.save(accident);
    }

    public boolean update(Accident accident) {
        boolean result = false;
        try {
            accidentMem.save(accident);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteById(int id) {
        accidentMem.deleteById(id);
    }

    public Optional<Accident> findAccidentById(int id) {
        return accidentMem.findById(id);
    }

    public Collection<Accident> findAll() {
        return accidentMem.findAll();
    }
}
