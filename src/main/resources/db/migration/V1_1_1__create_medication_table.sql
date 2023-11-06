CREATE TABLE IF NOT EXISTS medication  (
    medication_id bigserial PRIMARY KEY,
     created_at timestamp(0) with time zone NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at timestamp(0) with time zone NOT NULL,
     created_by text NOT NULL,
     updated_by text NOT NULL,

    medication_name text NOT NULL,
    weight_unit text NOT NULL,
    weight_limit decimal NOT NULL,

     code text NOT NULL,
    image text NOT NULL,

     version integer NOT NULL DEFAULT 1
     );

ALTER TABLE medication ADD CONSTRAINT drone_weight_limit_check CHECK (weight_limit < 500);
ALTER TABLE medication ADD CONSTRAINT drone_weight_min_check CHECK (weight_limit > 0);

--Medication
CREATE OR REPLACE FUNCTION medication_update_trigger_function()
    RETURNS TRIGGER
AS
$$
BEGIN
    NEW.updated_at = NOW() AT TIME ZONE 'UTC';
    RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER medication_update_trigger

    BEFORE UPDATE
    ON "medication"
    FOR EACH ROW
EXECUTE PROCEDURE medication_update_trigger_function();