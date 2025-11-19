package com.example.Conflict.Tracker.model;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "faction")
public class Faction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // ManyToOne con Conflict (cada faccion pertenece a 1 conflicto)
    @ManyToOne(optional = false)
    @JoinColumn(name = "conflict_id")
    private Conflict conflict;

    // ManyToMany con Country (paises que apoyan a esa faccion)
    @ManyToMany
    @JoinTable(
            name = "faction_country",
            joinColumns = @JoinColumn(name = "faction_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> supporterCountries = new HashSet<>();

    public Faction() {
    }

    public Faction(Long id, String name, Conflict conflict) {
        this.id = id;
        this.name = name;
        this.conflict = conflict;
    }

    //Gett and sett

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Conflict getConflict() {
        return conflict;
    }

    public void setConflict(Conflict conflict) {
        this.conflict = conflict;
    }

    public Set<Country> getSupporterCountries() {
        return supporterCountries;
    }

    public void setSupporterCountries(Set<Country> supporterCountries) {
        this.supporterCountries = supporterCountries;
    }
}

