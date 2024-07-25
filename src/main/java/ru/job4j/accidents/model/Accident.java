package ru.job4j.accidents.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Getter
@Setter
@ToString
@Table(name = "accidents")
public class Accident {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ac_name")
    private String name;

    @Column(name = "ac_text")
    private String text;

    @Column(name = "ac_address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private AccidentType type;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "accident_id") },
            inverseJoinColumns = { @JoinColumn(name = "rule_id") }
    )
    private Set<Rule> rules;

    public void addRule(Rule rule) {
        if (rule != null) {
            rules.add(rule);
        }
    }
}