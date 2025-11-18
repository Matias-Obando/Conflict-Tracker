package com.example.Conflict.Tracker.domain;
import jakarta.persistence.*;

@Entity
@Table(
        name = "country",
        uniqueConstraints = @UniqueConstraint(columnNames = "code")
)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // codigo tipo "UKR", "RUS", "USA"
    @Column(nullable = false, length = 3)
    private String code;

    public Country() {
    }

    public Country(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    // gett and sett

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
