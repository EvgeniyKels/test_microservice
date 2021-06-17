package com.example.demo.model.entity;

import com.example.demo.config.constants.TableNames;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = TableNames.PERSON_TABLE)
@NoArgsConstructor
@Getter
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = TableNames.PERSON_ID)
    private Long personId;
    @Column(name = TableNames.PERSON_NAME)
    private String personName;
    @ManyToMany(mappedBy = "personSet", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
    private final Set<SongEntity> songSet = new HashSet<>();

    public PersonEntity(String personName) {
        this.personName = personName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity that = (PersonEntity) o;
        return personName.equals(that.personName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personName);
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "personId=" + personId +
                ", personName='" + personName + '\'' +
                '}';
    }
}
