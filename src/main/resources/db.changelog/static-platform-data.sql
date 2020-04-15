DELETE FROM countries;
DELETE FROM cities;
DELETE FROM language;
INSERT INTO countries (id, country) VALUES ('1','Russia'), ('2','Germany'), ('3','English'), ('4','France');
INSERT INTO cities (id, city, country_id) VALUES ('1','Moscow','1'), ('2','Vladivostok','1'), ('3','Saint-Petersburg','1'),
                                               ('4','Berlin','2'), ('5','Munich','2'), ('6','Hamburg','2'),
                                               ('7','London','3'), ('8','Manchester','3'), ('9','Liverpool','3'),
                                               ('10','Paris','4'), ('11','Lyon','4'), ('12','Marseille','4');
INSERT INTO language (language) VALUES ('Russian'), ('English'), ('German'), ('French'), ('Italian');
