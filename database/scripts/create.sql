-- Database: postgres

-- DROP DATABASE postgres;

CREATE DATABASE foodbook
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.utf8'
       LC_CTYPE = 'en_US.utf8'
       CONNECTION LIMIT = -1;

COMMENT ON DATABASE foodbook
  IS 'default administrative connection database';
