package com.example.demo.repo_tests;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.*;

@ActiveProfiles("test")
@SpringBootTest
@Sql(scripts = "/test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RepoTestMethods {

    Set<Long> getRandomIdSet(List<Long> idsFromDb) {
        Set<Long>ids = new HashSet<>();
        int entityInDb = idsFromDb.size();
        long amountEntitiesForFetch = getRandomLong(idsFromDb, entityInDb);
        for (int i = 0; i < amountEntitiesForFetch; i++) {
            ids.add(getRandomLong(idsFromDb, entityInDb));
        }
        return ids;
    }

    long getRandomLong(List<Long> peopleIds, int peopleInDb) {
        return new Random().longs(1, peopleIds.get(0), peopleIds.get(peopleInDb - 1)).findAny().orElseThrow();
    }

    String getIdsString(Set<Long> randomIds) {
        return randomIds.stream().map(x -> Long.toString(x)).reduce("", (s, s2) -> s.concat(", ").concat(s2)).replaceFirst(", ", "");
    }

    ResultSetExtractor<List<Long>> getListResultSetExtractor() {
        return rs -> {
            List<Long> ids = new ArrayList<>();
            while (rs.next()) {
                ids.add(rs.getLong(1));
            }
            return ids;
        };
    }

    <K> ResultSetExtractor<List<K>> getResultSetEntityExtractor(Class<K> clazz) {
        return rs -> {
            List<K> entities = new ArrayList<>();
            while (rs.next()) {
                K entity = null;
                try {
                    entity = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
                Class<?> entityClass = entity.getClass();
                Field[] declaredFields = entityClass.getDeclaredFields();
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(Column.class)) {
                        field.setAccessible(true);
                        String columnName = field.getAnnotation(Column.class).name();
                        Class<?> type = field.getType();
                        Object object = rs.getObject(columnName, type);
                        try {
                            field.set(entity, object);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                        field.setAccessible(false);
                    }
                }
                entities.add(entity);
            }
            return entities;
        };
    }

//   <K> ResultSetExtractor<List<K>> getResultSetEntityExtractor(Class<K> clazz) {
//        return rs -> {
//            List<K> entities = new ArrayList<>();
//            while (rs.next()) {
//                Class<?> entityClass = entity.getClass();
//                Field[] declaredFields = entityClass.getDeclaredFields();
//                for (Field field : declaredFields) {
//                    if (field.isAnnotationPresent(Column.class)) {
//                        field.setAccessible(true);
//                        String columnName = field.getAnnotation(Column.class).name();
//                        Class<?> type = field.getType();
//                        Object object = rs.getObject(columnName, type);
//                        try {
//                            field.set(entity, object);
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                            System.out.println(e.getMessage());
//                        }
//                        field.setAccessible(false);
//                    }
//                }
//                entities.add(entity);
//            }
//            return entities;
//        };
//    }
}
