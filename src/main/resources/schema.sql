DROP TABLE IF EXISTS user_document CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS office CASCADE;
DROP TABLE IF EXISTS organizations CASCADE;
DROP TABLE IF EXISTS documents CASCADE;
DROP TABLE IF EXISTS countries CASCADE;

-- Table: countries
CREATE TABLE countries
(
  id serial NOT NULL,
  code integer,
  name character varying(255),
  CONSTRAINT countries_pkey PRIMARY KEY (id),
  CONSTRAINT country_code UNIQUE (code),
  CONSTRAINT country_name UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE countries
  OWNER TO postgres;
  
-- Table: documents
CREATE TABLE documents
(
  id serial NOT NULL,
  code integer,
  name character varying(255),
  CONSTRAINT documents_pkey PRIMARY KEY (id),
  CONSTRAINT document_code UNIQUE (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE documents
  OWNER TO postgres;


-- Table: organizations
CREATE TABLE organizations
(
  id serial NOT NULL,
  active boolean NOT NULL DEFAULT true,
  address character varying(255) NOT NULL,
  fullname character varying(255) NOT NULL,
  inn character varying(10) NOT NULL,
  kpp character varying(9) NOT NULL,
  name character varying(255) NOT NULL,
  phone character varying(255),
  CONSTRAINT organizations_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE organizations
  OWNER TO postgres;
  
-- Table: office
CREATE TABLE office
(
  id serial NOT NULL,
  active boolean NOT NULL,
  address character varying(255) NOT NULL,
  name character varying(255) NOT NULL,
  phone character varying(255),
  org_id integer,
  CONSTRAINT office_pkey PRIMARY KEY (id),
  CONSTRAINT office_org_fk FOREIGN KEY (org_id)
      REFERENCES organizations (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE office
  OWNER TO postgres;
  
-- Table: users
CREATE TABLE users
(
  id serial NOT NULL,
  firstname character varying(255) NOT NULL,
  identified boolean NOT NULL,
  middlename character varying(255),
  phone character varying(255),
  "position" character varying(255) NOT NULL,
  secondname character varying(255),
  citizenship_code integer,
  office_id integer,
  docdate date,
  docnumber character varying(255),
  doc_code integer,
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT user_citizenship_fk FOREIGN KEY (citizenship_code)
      REFERENCES countries (code) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE NO ACTION,
  CONSTRAINT doc_code_fk FOREIGN KEY (doc_code)
      REFERENCES documents (code) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE NO ACTION,
  CONSTRAINT user_office_fk FOREIGN KEY (office_id)
      REFERENCES office (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;

-- Index: userdoc_code
CREATE INDEX userdoc_code
  ON users
  USING btree
  (doc_code);

-- Index: organization_active
CREATE INDEX organization_active
  ON organizations
  USING btree
  (active);

-- Index: organization_inn
CREATE INDEX organization_inn
  ON organizations
  USING btree
  (inn COLLATE pg_catalog."default");

-- Index: office_active
CREATE INDEX office_active
  ON office
  USING btree
  (active);

-- Index: office_org
CREATE INDEX office_org
  ON office
  USING btree
  (org_id);

-- Index: office_phone
CREATE INDEX office_phone
  ON office
  USING btree
  (phone);

-- Index: user_citizenship
CREATE INDEX user_citizenship
  ON users
  USING btree
  (citizenship_code);

-- Index: user_firstname
CREATE INDEX user_firstname
  ON users
  USING btree
  (firstname);
  
-- Index: user_secondname
CREATE INDEX user_secondname
  ON users
  USING btree
  (secondname);

-- Index: user_middlename
CREATE INDEX user_middlename
  ON users
  USING btree
  (middlename);

-- Index: user_office
CREATE INDEX user_office
  ON users
  USING btree
  (office_id);

-- Index: user_phone
CREATE INDEX user_phone
  ON users
  USING btree
  (phone);

-- Index: user_position
CREATE INDEX user_position
  ON users
  USING btree
  ("position");