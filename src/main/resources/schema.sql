CREATE TABLE player (
    player_id SERIAL PRIMARY KEY,
    firstname VARCHAR( 64),
    lastname VARCHAR(64),
    nickname VARCHAR(64),
    date_of_birth DATE
);

CREATE TABLE event (
    event_id SERIAL PRIMARY KEY,
    eventDate DATE,
    eventType VARCHAR( 32)
);

CREATE TABLE merit (
    merit_id SERIAL PRIMARY KEY,
    merit_name VARCHAR( 128),
    category VARCHAR( 32)
);

CREATE TABLE merit_received (
    player_id integer REFERENCES player,
    merit_id integer REFERENCES merit,
    event_id integer REFERENCES event
);

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(64),
    mail VARCHAR( 128),
    pwdhash VARCHAR(128),
    rolename VARCHAR(64)
);
