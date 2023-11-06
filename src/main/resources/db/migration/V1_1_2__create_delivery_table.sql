CREATE TABLE IF NOT EXISTS delivery  (
    delivery_id bigserial PRIMARY KEY,
     created_at timestamp(0) with time zone NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at timestamp(0) with time zone NOT NULL,
     created_by text NOT NULL,
     updated_by text NOT NULL,

    drone_id bigint NOT NULL
        REFERENCES drone (drone_id)
            ON DELETE RESTRICT,

    medication_id bigint NOT NULL
        REFERENCES medication (medication_id)
            ON DELETE RESTRICT,

     code text NOT NULL,
    image text NOT NULL,

     version integer NOT NULL DEFAULT 1
     );

--Delivery
CREATE OR REPLACE FUNCTION delivery_update_trigger_function()
    RETURNS TRIGGER
AS
$$
BEGIN
    NEW.updated_at = NOW() AT TIME ZONE 'UTC';
    RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER delivery_update_trigger

    BEFORE UPDATE
    ON "delivery"
    FOR EACH ROW
EXECUTE PROCEDURE delivery_update_trigger_function();