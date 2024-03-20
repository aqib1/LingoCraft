-- database creation
-- TODO password shouldn't be stored here
--CREATE ROLE lingo_owner WITH PASSWORD 'lingo_owner';
--ALTER ROLE lingo_owner WITH LOGIN;

-- TODO password shouldn't be stored here
--CREATE ROLE lingo_read WITH PASSWORD 'lingo_read';
--ALTER ROLE lingo_read WITH LOGIN;

CREATE
    DATABASE "lingo_craft"
    WITH OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

