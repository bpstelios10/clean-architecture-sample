-- CREATE SCHEMA
create schema events_test;
GRANT ALL ON SCHEMA events_test TO dbuser;
-- CREATE TABLE
CREATE TABLE IF NOT EXISTS events_test.CONCERT_EVENT (
    id CHARACTER VARYING(255) NOT NULL,
    capacity INTEGER NOT NULL,
    date TIMESTAMP,
    location CHARACTER VARYING(255) NOT NULL,
    spots_left INTEGER NOT NULL,
    ticket_price REAL NOT NULL,
    PRIMARY KEY (id)
);
