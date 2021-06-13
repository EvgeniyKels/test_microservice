//package com.example.demo.util;
//
//import com.example.demo.model.entity.PersonEntity;
//import com.example.demo.model.entity.SongEntity;
//import com.example.demo.model.repo.IPersonRepo;
//import com.example.demo.model.repo.ISongRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
////TODO JDBC TEMPLATE
//public class Inserter  {
//    private final IPersonRepo personRepo;
//    private final ISongRepo songRepo;
//    @Value(value = "${song.num}")
//    private int songNum;
//    @Value(value = "${person.num}")
//    private int personNum;
//
//    private static final String SONG_NAME = "inserted song name";
//    private static final String PERSON_NAME = "inserted person name";
//
//    @Autowired
//    public Inserter(IPersonRepo personRepo, ISongRepo songRepo) {
//        this.personRepo = personRepo;
//        this.songRepo = songRepo;
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public void insertInitialData() {
//
//        List<SongEntity>songs = new ArrayList<>();
//        List<PersonEntity>people = new ArrayList<>();
//        for (var i = 0; i < songNum; i++) {
//            songs.add(new SongEntity(SONG_NAME + "_" + i));
//        }
//        for (var i = 0; i < personNum; i++) {
//            people.add(new PersonEntity(PERSON_NAME + "_" + i));
//        }
//        people = personRepo.saveAll(people);
//        for (var song : songs) {
//            for (var person : people) {
//                person.getSongSet().add(song);
//                song.getPersonSet().add(person);
//            }
//
//        }
//        System.out.println(songRepo.saveAll(songs).size());
//        System.out.println(personRepo.saveAll(people).size());
//    }
//}