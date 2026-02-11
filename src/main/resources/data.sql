-- COUNTRIES
INSERT INTO country (id, name, code) VALUES
                                         (1, 'Ukraine', 'UKR'),
                                         (2, 'Russia', 'RUS'),
                                         (3, 'USA', 'USA')
    ON CONFLICT (id) DO NOTHING;

-- CONFLICT
INSERT INTO conflict (id, name, start_date, status, description) VALUES
    (1, 'Russo-Ukrainian War', '2022-02-24', 'ACTIVE', 'Conflicte entre Russia and Ukraine.')
    ON CONFLICT (id) DO NOTHING;

-- CONFLICT_COUNTRY (tabla puente)
INSERT INTO conflict_country (conflict_id, country_id) VALUES
                                                           (1, 1),
                                                           (1, 2)
    ON CONFLICT DO NOTHING;

-- EVENTS
INSERT INTO event (id, event_date, location, description, conflict_id) VALUES
                                                                           (1, '2022-02-24', 'Kyiv', 'Russian troops enter Ukraine', 1),
                                                                           (2, '2022-06-10', 'East Ukraine', 'Ukraine retakes territory', 1)
    ON CONFLICT (id) DO NOTHING;

-- FACTIONS
INSERT INTO faction (id, name, conflict_id) VALUES
                                                (1, 'Pro-Ukraine', 1),
                                                (2, 'Pro-Russia', 1)
    ON CONFLICT (id) DO NOTHING;

-- FACTION_COUNTRY (tabla puente)
INSERT INTO faction_country (faction_id, country_id) VALUES
                                                         (1, 1),
                                                         (1, 3),
                                                         (2, 2)
    ON CONFLICT DO NOTHING;

--Reset de secuencias (Postgres)
SELECT setval(pg_get_serial_sequence('country','id'),  (SELECT COALESCE(MAX(id),0) FROM country));
SELECT setval(pg_get_serial_sequence('conflict','id'), (SELECT COALESCE(MAX(id),0) FROM conflict));
SELECT setval(pg_get_serial_sequence('event','id'),    (SELECT COALESCE(MAX(id),0) FROM event));
SELECT setval(pg_get_serial_sequence('faction','id'),  (SELECT COALESCE(MAX(id),0) FROM faction));
