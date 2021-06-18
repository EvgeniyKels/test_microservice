package com.example.demo.repo_tests;

import com.example.demo.model.entity.SongEntity;
import com.example.demo.model.repo.ISongRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SongRepoTest extends RepoTestMethods {
    private final JdbcTemplate jdbcTemplate;
    private final ISongRepo songRepo;
    private final Set<Long> notExistingIds = Set.of(1000L, 5000L, 10000L);

    @Autowired
    public SongRepoTest(JdbcTemplate jdbcTemplate, ISongRepo songRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.songRepo = songRepo;
    }

    @Test
    void countBySongIdInTestCorrectInputValues() {
        List<Long> allSongIds = jdbcTemplate.query(
                "SELECT song_id FROM songs", getListResultSetExtractor());
        Set<Long> songIdsForQueries = getRandomIdSet(Objects.requireNonNull(allSongIds));
        long result = songRepo.countBySongIdIn(songIdsForQueries);
        assertEquals(result, songIdsForQueries.size());
    }

    @Test
    void countByPersonIdInTestNotCorrectInputValues() {
        long result = songRepo.countBySongIdIn(notExistingIds);
        assertEquals(0, result);
    }

    @Test
    void getSongByName() {
        SongEntity songEntityByQuery = Objects.requireNonNull(jdbcTemplate.query(
                "SELECT song_id, song_name FROM songs", getResultSetEntityExtractor(SongEntity.class))).stream().findAny().orElseThrow();
        Optional<SongEntity> optionalSong = songRepo.findBySongName(Objects.requireNonNull(songEntityByQuery).getSongName());
        assertFalse(optionalSong.isEmpty());
        SongEntity songEntity = optionalSong.get();
        assertEquals(songEntityByQuery.getSongId(), songEntity.getSongId());
        assertEquals(songEntityByQuery.getSongName(), songEntity.getSongName());
    }

    @Test
    void getSongByNotExistingName() {
        Optional<SongEntity> optionalSong = songRepo.findBySongName(UUID.randomUUID().toString());
        assertTrue(optionalSong.isEmpty());
    }

    @Test
    void findAllSongsByPeopleArray() {
        List<Long> allPeopleIds = jdbcTemplate.query("SELECT person_id FROM people", getListResultSetExtractor());
        Set<Long> randomPeopleIds = getRandomIdSet(Objects.requireNonNull(allPeopleIds));

        List<SongEntity> songs = jdbcTemplate.query(
                "SELECT DISTINCT s.song_id, s.song_name FROM songs AS s " +
                        "JOIN songs_people AS sp ON s.song_id = sp.song_id " +
                        "JOIN people AS p ON sp.person_id = p.person_id " +
                        "WHERE p.person_id IN (" + getIdsString(randomPeopleIds) + ")", getResultSetEntityExtractor(SongEntity.class));

        List<SongEntity> songFromRepo = songRepo.findAllByPersonSet_PersonIdIn(randomPeopleIds);

        assertEquals(Objects.requireNonNull(songs).size(), songFromRepo.size());
        assertEquals(songs, songFromRepo);
    }

    @Test
    void findAllSongsByPeopleArrayWhichNotExists() {
        List<SongEntity> songsFromRepo = songRepo.findAllByPersonSet_PersonIdIn(notExistingIds);
        assertEquals(0, songsFromRepo.size());
    }

    @Test
    void findAllSongsByPersonsIds() {
        List<Long> allSongsIds = jdbcTemplate.query("SELECT song_id FROM songs", getListResultSetExtractor());
        Set<Long> randomSongIds = getRandomIdSet(Objects.requireNonNull(allSongsIds));

        List<SongEntity> songs = jdbcTemplate.query(
                "SELECT DISTINCT s.song_id, s.song_name FROM songs s WHERE s.song_id IN (" + getIdsString(randomSongIds) + ")", getResultSetEntityExtractor(SongEntity.class));

        List<SongEntity> songFromRepo = songRepo.findAllByPersonSet_PersonIdIn(randomSongIds);

        assertEquals(Objects.requireNonNull(songs).size(), songFromRepo.size());
        assertEquals(songs, songFromRepo);
    }

    @Test
    void findAllSongsByNotExistingSongIds() {
        List<SongEntity> songFromRepo = songRepo.findAllByPersonSet_PersonIdIn(notExistingIds);
        assertEquals(0, songFromRepo.size());
    }
}
