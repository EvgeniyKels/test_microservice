package com.example.demo.model.repo;

import com.example.demo.model.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IPersonRepo extends JpaRepository<PersonEntity, Long> {
    Optional<PersonEntity> findByPersonName(String personName);

    long countByPersonIdIn(Iterable<Long> personIds);

    List<PersonEntity> findAllBySongSet_songIdIn(Iterable<Long> songIds);


    List<PersonEntity> findAllByPersonIdIn(Iterable<Long> personIds);
}
