-- Sequences
CREATE SEQUENCE SEQ_COMPANY;
CREATE SEQUENCE SEQ_JOB_OFFER;
CREATE SEQUENCE SEQ_TAG;
CREATE SEQUENCE SEQ_TAG_FK;
CREATE SEQUENCE SEQ_STOP_WORD;

-- Tables

CREATE TABLE "COMPANY"
(
	"ID"             BIGINT DEFAULT nextval('SEQ_COMPANY') NOT NULL,
	"CITY"           CHARACTER VARYING(255),
	"CONTACT_PERSON" CHARACTER VARYING(255),
	"CRAWLER_NAME"   CHARACTER VARYING(255),
	"LOCATION_CODE"  CHARACTER VARYING(255),
	"NAME"           CHARACTER VARYING(255)                NOT NULL,
	"PHONE"          CHARACTER VARYING(255),
	"STREET"         CHARACTER VARYING(255),
	"LAST_UPDATE"    TIMESTAMP,

	CONSTRAINT COMPANY_PKEY PRIMARY KEY ("ID")
);

CREATE TABLE "JOB_OFFER"
(
	"ID"                    BIGINT DEFAULT nextval('SEQ_JOB_OFFER') NOT NULL,
	"COMPANY_ID"            BIGINT,
	"COMPANY_NAME"          CHARACTER VARYING(255),
	"DESCRIPTION"           TEXT,
	"JOB_ANNOUNCEMENT_TIME" TIMESTAMP,
	"JOB_ID"                TEXT,
	"JOB_ID_HASH"           INTEGER                                 NOT NULL,
	"JOB_TITLE"             CHARACTER VARYING(255),
	"JOB_URL"               CHARACTER VARYING(255),
	"OBSOLETE"              BOOLEAN                                 NOT NULL,

	CONSTRAINT JOB_OFFER_PKEY PRIMARY KEY ("ID")
);

CREATE TABLE "TAG"
(
	"ID"        BIGINT DEFAULT nextval('SEQ_TAG') NOT NULL,
	"TAG_FIELD" CHARACTER VARYING,

	CONSTRAINT "TAG_PKEY" PRIMARY KEY ("ID"),
	CONSTRAINT "TAG_FIELD_UNIQUE" UNIQUE ("TAG_FIELD")
);

CREATE TABLE "TAG_FK"
(
	"ID"          BIGINT DEFAULT nextval('SEQ_TAG_FK') NOT NULL,
	"COMPANY_ID"  BIGINT,
	"JOB_OFFER_ID" BIGINT,
	"TAG_ID"      BIGINT,
	"TFIDF"        DOUBLE PRECISION,

	CONSTRAINT "TAG_FK_PKEY" PRIMARY KEY ("ID")
);

-- INDEXES
CREATE INDEX IX_JOB_ID_HASH
ON "JOB_OFFER"
USING BTREE ("JOB_ID_HASH");

CREATE INDEX IX_TAG_FK_JOB_OFFER_ID
ON "TAG_FK"
USING BTREE ("JOB_OFER_ID");
