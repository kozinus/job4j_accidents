package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeRepository accidentTypeMem;

    public AccidentType save(AccidentType accidentType) {
        return accidentTypeMem.save(accidentType);
    }

    public void update(AccidentType accidentType) {
        accidentTypeMem.save(accidentType);
    }

    public void deleteById(int id) {
        accidentTypeMem.deleteById(id);
    }

    public Optional<AccidentType> findAccidentTypeById(int id) {
        return accidentTypeMem.findById(id);
    }

    public Collection<AccidentType> findAll() {
        return (List<AccidentType>) accidentTypeMem.findAll();
    }
}
