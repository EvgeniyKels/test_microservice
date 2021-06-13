package com.example.demo.repository;

import com.example.demo.config.constants.PredefinedQueries;
import com.example.demo.config.constants.TableNames;
import com.example.demo.model.entity.PersonEntity;
import com.example.demo.model.repo.IPersonRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PersonRepoTest extends RepoTestMethods {
    private final JdbcTemplate jdbcTemplate;
    private final IPersonRepo personRepo;
    private final Set<Long> notExistingIds = Set.of(1000L, 5000L, 10000L);

    @Autowired
    public PersonRepoTest(JdbcTemplate jdbcTemplate, IPersonRepo personRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.personRepo = personRepo;
    }

    @Test
    @Rollback(value = false)
    void countByPersonIdInTestCorrectInputValues() {
        List<Long> allPeopleIds = jdbcTemplate.query(
                "SELECT person_id FROM people", getListResultSetExtractor());
        Set<Long> personIdsForQueries = getRandomIdSet(Objects.requireNonNull(allPeopleIds));
        long result = personRepo.countByPersonIdIn(personIdsForQueries);
        assertEquals(result, personIdsForQueries.size());
    }

    @Test
    @Rollback(value = false)
    void countByPersonIdInTestNotCorrectInputValues() {
        long result = personRepo.countByPersonIdIn(notExistingIds);
        assertEquals(0, result);
    }

    @Test
    void getPersonByName() {
        PersonEntity personTestEntity = Objects.requireNonNull(jdbcTemplate.query(
                "SELECT person_id, person_name FROM people", getResultSetEntityExtractor(new PersonEntity()))).stream().findAny().orElseThrow();
        Optional<PersonEntity> optionalPerson = personRepo.findByPersonName(Objects.requireNonNull(personTestEntity).getPersonName());
        assertFalse(optionalPerson.isEmpty());
        PersonEntity personEntity = optionalPerson.get();
        assertEquals(personTestEntity.getPersonId(), personEntity.getPersonId());
        assertEquals(personTestEntity.getPersonName(), personEntity.getPersonName());
    }

    @Test
    void getPersonByNotExistingName() {
        Optional<PersonEntity> optionalPerson = personRepo.findByPersonName(UUID.randomUUID().toString());
        assertTrue(optionalPerson.isEmpty());
    }

    @Test
    void findAllPersonsBySongArray() {
        List<Long> allSongsIds = jdbcTemplate.query("SELECT song_id FROM songs", getListResultSetExtractor());
        Set<Long> randomSongIds = getRandomIdSet(Objects.requireNonNull(allSongsIds));

        List<PersonEntity> people = jdbcTemplate.query(
                "SELECT DISTINCT p.person_id, p.person_name FROM people AS p JOIN songs_people AS sp ON p.person_id = sp.person_id JOIN songs AS s ON sp.song_id = s.song_id " +
                "WHERE s.song_id IN (" + getIdsString(randomSongIds) + ")", getResultSetEntityExtractor(new PersonEntity()));

        List<PersonEntity> peopleFromRepo = personRepo.findAllDistinctBySongSet_SongIdIn(randomSongIds);

        assertEquals(Objects.requireNonNull(people).size(), peopleFromRepo.size());
        assertEquals(people, peopleFromRepo);
    }

    @Test
    void findAllPersonsBySongArrayWhichNotExists() {
        List<PersonEntity> peopleFromRepo = personRepo.findAllDistinctBySongSet_SongIdIn(notExistingIds);
        assertEquals(0, peopleFromRepo.size());
    }

    @Test
    void findAllPersonsByPersonsIds() {
        List<Long> allPeopleIds = jdbcTemplate.query("SELECT person_id FROM people", getListResultSetExtractor());
        Set<Long> randomPeopleIds = getRandomIdSet(Objects.requireNonNull(allPeopleIds));

        List<PersonEntity> people = jdbcTemplate.query(
                "SELECT DISTINCT p.person_id, p.person_name FROM people p WHERE p.person_id IN (" + getIdsString(randomPeopleIds) + ")",
                getResultSetEntityExtractor(new PersonEntity()));

        List<PersonEntity> peopleFromRepo = personRepo.findAllByPersonIdIn(randomPeopleIds);

        assertEquals(Objects.requireNonNull(people).size(), peopleFromRepo.size());
        assertEquals(people, peopleFromRepo);
    }

    @Test
    void findAllPersonsByNotExistingPersonsIds() {
        List<PersonEntity> peopleFromRepo = personRepo.findAllByPersonIdIn(notExistingIds);
        assertEquals(0, peopleFromRepo.size());
    }
}
