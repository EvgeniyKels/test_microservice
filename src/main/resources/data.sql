SET foreign_key_checks = 0;

TRUNCATE people;
TRUNCATE songs;
TRUNCATE songs_people;

INSERT INTO people VALUES (1, "person_name_1");
INSERT INTO people VALUES (2, "person_name_2");
INSERT INTO people VALUES (3, "person_name_3");
INSERT INTO people VALUES (4, "person_name_4");
INSERT INTO people VALUES (5, "person_name_5");
INSERT INTO people VALUES (6, "person_name_6");
INSERT INTO people VALUES (7, "person_name_7");
INSERT INTO people VALUES (8, "person_name_8");
INSERT INTO people VALUES (9, "person_name_9");
INSERT INTO people VALUES (10, "person_name_10");

INSERT INTO songs VALUES (1, "sond_name_1");
INSERT INTO songs VALUES (2, "sond_name_2");
INSERT INTO songs VALUES (3, "sond_name_3");
INSERT INTO songs VALUES (4, "sond_name_4");
INSERT INTO songs VALUES (5, "sond_name_5");

INSERT INTO songs_people VALUES (1, 1);
INSERT INTO songs_people VALUES (1, 2);
INSERT INTO songs_people VALUES (1, 3);
INSERT INTO songs_people VALUES (1, 4);
INSERT INTO songs_people VALUES (1, 5);

INSERT INTO songs_people VALUES (2, 1);
INSERT INTO songs_people VALUES (2, 2);
INSERT INTO songs_people VALUES (2, 3);
INSERT INTO songs_people VALUES (2, 4);
INSERT INTO songs_people VALUES (2, 5);

INSERT INTO songs_people VALUES (3, 1);
INSERT INTO songs_people VALUES (3, 2);
INSERT INTO songs_people VALUES (3, 3);
INSERT INTO songs_people VALUES (3, 4);
INSERT INTO songs_people VALUES (3, 5);

INSERT INTO songs_people VALUES (4, 1);
INSERT INTO songs_people VALUES (4, 2);
INSERT INTO songs_people VALUES (4, 3);
INSERT INTO songs_people VALUES (4, 4);
INSERT INTO songs_people VALUES (4, 5);

INSERT INTO songs_people VALUES (5, 1);
INSERT INTO songs_people VALUES (5, 2);
INSERT INTO songs_people VALUES (5, 3);
INSERT INTO songs_people VALUES (5, 4);
INSERT INTO songs_people VALUES (5, 5);

INSERT INTO songs_people VALUES (6, 1);
INSERT INTO songs_people VALUES (6, 2);
INSERT INTO songs_people VALUES (6, 3);
INSERT INTO songs_people VALUES (6, 4);
INSERT INTO songs_people VALUES (6, 5);

INSERT INTO songs_people VALUES (7, 1);
INSERT INTO songs_people VALUES (7, 2);
INSERT INTO songs_people VALUES (7, 3);
INSERT INTO songs_people VALUES (7, 4);
INSERT INTO songs_people VALUES (7, 5);

INSERT INTO songs_people VALUES (8, 1);
INSERT INTO songs_people VALUES (8, 2);
INSERT INTO songs_people VALUES (8, 3);
INSERT INTO songs_people VALUES (8, 4);
INSERT INTO songs_people VALUES (8, 5);

INSERT INTO songs_people VALUES (9, 1);
INSERT INTO songs_people VALUES (9, 2);
INSERT INTO songs_people VALUES (9, 3);
INSERT INTO songs_people VALUES (9, 4);
INSERT INTO songs_people VALUES (9, 5);

INSERT INTO songs_people VALUES (10, 1);
INSERT INTO songs_people VALUES (10, 2);
INSERT INTO songs_people VALUES (10, 3);
INSERT INTO songs_people VALUES (10, 4);
INSERT INTO songs_people VALUES (10, 5);

SET foreign_key_checks = 1;