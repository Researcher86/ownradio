CREATE TABLE db.cnt_music_history( 
 id SERIAL,
 user_id VARCHAR(128) NOT NULL,
 device_id VARCHAR(128) NOT NULL,
 music_id INTEGER NOT NULL,
 description varchar(1024),
 changed_in varchar(128),
 lastupdatedatetime TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
PRIMARY KEY(id));

COMMENT ON TABLE db.cnt_music_history IS 'Music changes history';
COMMENT ON COLUMN db.cnt_music_history.user_id IS 'Identifies the user';
COMMENT ON COLUMN db.cnt_music_history.music_id IS 'Identifies the music';
COMMENT ON COLUMN db.cnt_music_history.description IS 'Changes description';
COMMENT ON COLUMN db.cnt_music_history.changed_in IS 'Place of changes';
COMMENT ON COLUMN db.cnt_music_history.lastupdatedatetime IS 'Last modifications date and time';
