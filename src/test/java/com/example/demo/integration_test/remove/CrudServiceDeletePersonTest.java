package com.example.demo.integration_test.remove;

import com.example.demo.integration_test.ParentTest;
import com.example.demo.model.repo.IPersonRepo;
import com.example.demo.model.repo.ISongRepo;
import com.example.demo.service.interfaces.ICrudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CrudServiceDeletePersonTest extends ParentTest {
    private final ICrudService crudService;
    private final ISongRepo songRepo;
    private final IPersonRepo personRepo;

    @Autowired
    public CrudServiceDeletePersonTest(ICrudService crudService, ISongRepo songRepo, IPersonRepo personRepo) {
        this.crudService = crudService;
        this.songRepo = songRepo;
        this.personRepo = personRepo;
    }

    @Test
    void removePersonById() {
        long songsBefore = songRepo.count();
        long peopleBefore = personRepo.count();
        long randomId = getRandomInt(1, (int) peopleBefore);
        Map<String, Long> stringLongMap = crudService.deletePerson(List.of(randomId));
        assertEquals(songsBefore, songRepo.count());
        assertEquals(peopleBefore - 1, personRepo.count());


    }
}
