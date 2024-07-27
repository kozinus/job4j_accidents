package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.TypeHibernate;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final TypeHibernate accidentTypeMem;

    public Optional<AccidentType> save(AccidentType accidentType) {
        return Optional.ofNullable(accidentTypeMem.save(accidentType));
    }

    public boolean update(AccidentType accidentType) {
        return accidentTypeMem.update(accidentType);
    }

    public boolean deleteById(int id) {
        return accidentTypeMem.deleteById(id);
    }

    public Optional<AccidentType> findAccidentTypeById(int id) {
        return accidentTypeMem.findById(id);
    }

    public Collection<AccidentType> findAll() {
        return accidentTypeMem.findAll();
    }
}
