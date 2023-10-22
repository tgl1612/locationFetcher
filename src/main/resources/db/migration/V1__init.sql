CREATE TABLE t_location_information
(
  location_id VARCHAR(200),
  location_type VARCHAR(200),
  address_line_1 VARCHAR,
  address_line_2 VARCHAR,
  city VARCHAR(200),
  post_code VARCHAR(200),
  country VARCHAR(200),
  price VARCHAR(200),
  parking BOOLEAN,
  permit_required BOOLEAN,
  ramp BOOLEAN,
  lift BOOLEAN,
  direct BOOLEAN,
  keywords VARCHAR,
  primary key (location_id)
);

CREATE TABLE t_contact_information
(
  id VARCHAR(200),
  first_name VARCHAR(200),
  last_name VARCHAR(200),
  email VARCHAR(200),
  phone_number VARCHAR(200),
  contact_type VARCHAR(200),
  location_id VARCHAR(200),
  primary key (id)
);

ALTER TABLE t_contact_information
ADD CONSTRAINT LOCATION_ID_FK
FOREIGN KEY (location_id) REFERENCES t_location_information ON DELETE CASCADE;