--Users
ALTER TABLE drone ALTER COLUMN updated_at SET DEFAULT (NOW() AT TIME ZONE 'UTC');

-- social_media_account
ALTER TABLE medication ALTER COLUMN updated_at SET DEFAULT (NOW() AT TIME ZONE 'UTC');

-- post
ALTER TABLE delivery ALTER COLUMN updated_at SET DEFAULT (NOW() AT TIME ZONE 'UTC');