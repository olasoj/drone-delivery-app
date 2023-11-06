CREATE TYPE Model AS ENUM ('Lightweight', 'Middleweight', 'Cruiserweight', 'Heavyweight');
CREATE TYPE DroneState AS ENUM ('IDLE', 'LOADING', 'LOADED', 'DELIVERING', 'DELIVERED', 'RETURNING');


CREATE TABLE IF NOT EXISTS drone (
     drone_id bigserial PRIMARY KEY,
     created_at timestamp(0) with time zone NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at timestamp(0) with time zone NOT NULL,
     created_by text NOT NULL,
     updated_by text NOT NULL,

    serial_number text NOT NULL,
    weight_unit text NOT NULL,
    weight_limit decimal NOT NULL,

     model Model NOT NULL,
    drone_state DroneState NOT NULL,

    battery_capacity decimal NOT NULL,
     version integer NOT NULL DEFAULT 1
     );


CREATE UNIQUE INDEX IF NOT EXISTS  idx_drone_serial_number
    ON drone(serial_number);

ALTER TABLE drone ADD CONSTRAINT drone_weight_limit_check CHECK (weight_limit < 500);
ALTER TABLE drone ADD CONSTRAINT drone_battery_capacity_check CHECK (battery_capacity < 101);
ALTER TABLE drone ADD CONSTRAINT drone_weight_min_check CHECK (weight_limit > 0);
ALTER TABLE drone ADD CONSTRAINT drone_battery_capacity_min_check CHECK (battery_capacity > 0);
ALTER TABLE drone ADD CONSTRAINT drone_serial_number_length_check CHECK (LENGTH(serial_number) < 100);
-- ALTER TABLE follows ADD CONSTRAINT drone_serial_number__unique_check UNIQUE (serial_number);

--Drone
CREATE OR REPLACE FUNCTION drone_update_trigger_function()
    RETURNS TRIGGER
AS
$$
BEGIN
    NEW.updated_at = NOW() AT TIME ZONE 'UTC';
RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER drone_update_trigger

    BEFORE UPDATE
    ON "drone"
    FOR EACH ROW
    EXECUTE PROCEDURE drone_update_trigger_function();