-- Table: talk

-- DROP TABLE talk;

CREATE TABLE talk
(
  id serial NOT NULL,
  title character varying(255),
  CONSTRAINT talk_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE talk
  OWNER TO postgres;
