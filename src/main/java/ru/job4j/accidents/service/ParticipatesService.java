package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.repository.ParticipatesJdbcTemplate;

@Service
@AllArgsConstructor
public class ParticipatesService {
    private final ParticipatesJdbcTemplate ruleMem;

    public boolean save(int accidentId, int ruleId) {
        return ruleMem.save(accidentId, ruleId);
    }

    public boolean deleteByIds(int accidentId, int ruleId) {
        return ruleMem.deleteByIds(accidentId, ruleId);
    }

    public boolean deleteByAccidentId(int accidentId) {
        return ruleMem.deleteByAccidentId(accidentId);
    }

    public boolean deleteByRuleId(int ruleId) {
        return ruleMem.deleteByRuleId(ruleId);
    }
}
