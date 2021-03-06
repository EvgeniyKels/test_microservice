package com.example.demo.integration_test;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@NoArgsConstructor
@SpringBootTest
@ActiveProfiles("test")
@Sql(value = "/test_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParentTest {

    public static final String TEST_NAME = "test name";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void notNullTest() {
        assertNotNull(jdbcTemplate);
    }

    protected int getRandomInt(int from, int to) {
        return new Random().ints(1, from, to).findAny().orElseThrow();
    }

//    @AfterEach
//    void cleanUpAllTables() {
//
//        String[] tableNames = Arrays.stream(
//                TableNames.class.getDeclaredFields()).
//                filter(x -> x.getName().endsWith("_TABLE")).map(x -> {
//            Class<?> type = x.getType();
//            Object value = null;
//            try {
//                Object instance = type.newInstance();
//                value = x.get(instance);
//            } catch (InstantiationException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            return Objects.requireNonNull((String)value);
//        }).collect(Collectors.toUnmodifiableList()).toArray(new String[0]);
//
//        jdbcTemplate.execute(PredefinedQueries.SET_FOREIGN_KEYS_CHECK_0.getQuery());
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, tableNames);
//        jdbcTemplate.execute(PredefinedQueries.SET_FOREIGN_KEYS_CHECK_1.getQuery());
//
//    }
}
