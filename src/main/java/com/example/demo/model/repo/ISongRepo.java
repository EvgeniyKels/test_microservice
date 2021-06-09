package com.example.demo.model.repo;

import com.example.demo.model.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISongRepo extends JpaRepository<SongEntity, Long> {
    List<SongEntity> findAllBySongIdIn(Iterable<Long> songIds);

    Optional<SongEntity> findBySongName(String songName);

    List<SongEntity> findAllByPersonSet_personIdIn(List<Long> personIds);

    long countBySongIdIn(Iterable<Long> songIds);
}
