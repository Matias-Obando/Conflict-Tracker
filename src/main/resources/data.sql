INSERT INTO COUNTRY (id, name, code) VALUES
  (1, 'Ukraine', 'UKR'),
  (2, 'Russia', 'RUS'),
  (3, 'USA', 'USA');

INSERT INTO CONFLICT (id, name, start_date, status, description)
VALUES
  (1, 'Russo-Ukrainian War', '2022-02-24', 'ACTIVE', 'Conflicte entre Russia and Ukraine.');

INSERT INTO CONFLICT_COUNTRY (conflict_id, country_id) VALUES
  (1, 1),
  (1, 2);

-- EVENTOS
INSERT INTO EVENT (id, event_date, location, description, conflict_id)
VALUES
  (1, '2022-02-24', 'Kyiv', 'Russian troops enter Ukraine', 1),
  (2, '2022-06-10', 'East Ukraine', 'Ukraine retakes territory', 1);

-- FACTIONS
INSERT INTO FACTION (id, name, conflict_id)
VALUES
  (1, 'Pro-Ukraine', 1),
  (2, 'Pro-Russia', 1);

-- PAÍSES QUE APOYAN A CADA FACTION
INSERT INTO FACTION_COUNTRY (faction_id, country_id)
VALUES
  (1, 1),  -- Ukraine apoya Pro-Ukraine
  (1, 3),  -- USA apoya Pro-Ukraine
  (2, 2);  -- Russia apoya Pro-Russia

  -- resetear autoincrement (para que los POST no intenten reutilizar IDs que ya tengo)
  ALTER TABLE COUNTRY  ALTER COLUMN ID RESTART WITH 4;
  ALTER TABLE CONFLICT ALTER COLUMN ID RESTART WITH 2;
  ALTER TABLE EVENT    ALTER COLUMN ID RESTART WITH 3;
  ALTER TABLE FACTION  ALTER COLUMN ID RESTART WITH 3;

