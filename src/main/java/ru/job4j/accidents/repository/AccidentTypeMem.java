package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class AccidentTypeMem {
    private final AtomicInteger nextId = new AtomicInteger(4);

    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();

    public AccidentTypeMem() {
        types.put(1, new AccidentType(1, "Две машины"));
        types.put(2, new AccidentType(2, "Машина и человек"));
        types.put(3, new AccidentType(3, "Машина и велосипед"));
    }

    public Optional<AccidentType> save(AccidentType accidentType) {
        accidentType.setId(nextId.getAndIncrement());
        types.put(accidentType.getId(), accidentType);
        return Optional.of(accidentType);
    }

    public boolean deleteById(int id) {
        return types.remove(id) != null;
    }

    public boolean update(AccidentType accidentType) {
        return types.computeIfPresent(accidentType.getId(), (id, oldType) -> new AccidentType(oldType.getId(), accidentType.getName())) != null;
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(types.get(id));
    }

    public Collection<AccidentType> findAll() {
        return types.values();
    }

}
