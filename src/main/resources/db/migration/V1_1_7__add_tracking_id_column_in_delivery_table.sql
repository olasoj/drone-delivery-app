ALTER TABLE delivery
    ADD COLUMN tracking_id text NOT NULL default 'aabffa6a-326b-48d8-976f-39167ce5cefe' ;

ALTER TABLE delivery ALTER COLUMN tracking_id DROP DEFAULT;
