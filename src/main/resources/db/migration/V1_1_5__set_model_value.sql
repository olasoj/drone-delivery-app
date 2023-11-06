CREATE TYPE DroneModel AS ENUM ('LIGHTWEIGHT', 'MIDDLEWEIGHT', 'CRUISERWEIGHT', 'HEAVYWEIGHT');

ALTER TABLE drone
    ALTER COLUMN model TYPE DroneModel
    USING model::text::dronemodel;

    DROP TYPE Model;

