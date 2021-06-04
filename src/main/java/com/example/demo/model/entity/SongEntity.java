package com.example.demo.model.entity;

import com.example.demo.config.TableNames;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = TableNames.SONG_TABLE)
@NoArgsConstructor
@Getter
public class SongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = TableNames.SONG_ID)
    private Long songId;
    @Column(name = TableNames.SONG_NAME)
    private String songName;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = TableNames.SONGS_PEOPLE_TABLE,
            joinColumns = @JoinColumn(name = TableNames.PERSON_ID),
            inverseJoinColumns = @JoinColumn(name = TableNames.SONG_ID)
    )
    private final Set<PersonEntity>personSet = new HashSet<>();

    public SongEntity(String songName) {
        this.songName = songName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongEntity that = (SongEntity) o;
        return songName.equals(that.songName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songName);
    }

    @Override
    public String toString() {
        return "SongEntity{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                '}';
    }
}
