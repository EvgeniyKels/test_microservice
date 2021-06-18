package com.example.demo.model.repo;

import com.example.demo.model.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPersonRepo extends JpaRepository<PersonEntity, Long> {
    long countByPersonIdIn(Iterable<Long> personIds);

    Optional<PersonEntity> findByPersonName(String personName);

    List<PersonEntity> findAllDistinctBySongSet_SongIdIn(@Param("songIdList") Iterable<Long> songIds);

    List<PersonEntity> findAllByPersonIdIn(Iterable<Long> personIds);
}
