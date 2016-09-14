-- Database: "ownRadio"

-- DROP DATABASE "ownRadio";

CREATE DATABASE "ownRadio"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Table: public."User"

-- DROP TABLE public."User";

CREATE TABLE public."User"
(
  "ID" uuid NOT NULL,
  "Name" character varying(100) NOT NULL,
  CONSTRAINT "PK_Users" PRIMARY KEY ("ID")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."User"
  OWNER TO postgres;

  -- Table: public."Device"

-- DROP TABLE public."Device";

CREATE TABLE public."Device"
(
  "ID" uuid NOT NULL, -- Device ID
  "UserID" uuid,
  "Name" character varying(100),
  CONSTRAINT "PK_Device" PRIMARY KEY ("ID"),
  CONSTRAINT "FK_User" FOREIGN KEY ("UserID")
      REFERENCES public."User" ("ID") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."Device"
  OWNER TO postgres;
COMMENT ON COLUMN public."Device"."ID" IS 'Device ID';


-- Index: public.fki_user

-- DROP INDEX public.fki_user;

CREATE INDEX "FKI_User"
  ON public."Device"
  USING btree
  ("UserID");

-- Table: "Track"

-- DROP TABLE "Track";

CREATE TABLE "Track"
(
  "ID" uuid NOT NULL, -- �������������
  "UploadUserID" uuid NOT NULL, -- ����������� ������������
  "LocalDevicePathUpload" character varying(2048), -- ��� �����
  "Path" character varying(2048), -- ���� �� �� ������������
  CONSTRAINT "PK_Track" PRIMARY KEY ("ID"),
  CONSTRAINT "FK_Track_User" FOREIGN KEY ("UploadUserID")
      REFERENCES "User" ("ID") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Track"
  OWNER TO postgres;
COMMENT ON TABLE "Track"
  IS '����������� ����';
COMMENT ON COLUMN "Track"."ID" IS '�������������';
COMMENT ON COLUMN "Track"."UploadUserID" IS '����������� ������������';
COMMENT ON COLUMN "Track"."LocalDevicePathUpload" IS '��� �����';
COMMENT ON COLUMN "Track"."Path" IS '���� �� �� ������������';


-- Index: "FKI_Track_User"

-- DROP INDEX "FKI_Track_User";

CREATE INDEX "FKI_Track_User"
  ON "Track"
  USING btree
  ("UploadUserID");
  
-- Table: "History"

-- DROP TABLE "History";

CREATE TABLE "History"
(
  "ID" uuid NOT NULL DEFAULT uuid_generate_v4(),
  "UserID" uuid NOT NULL,
  "TrackID" uuid NOT NULL,
  "LastListenDateTime" timestamp with time zone NOT NULL DEFAULT now(), -- ����� �������������
  CONSTRAINT "PK_History" PRIMARY KEY ("ID"),
  CONSTRAINT "FK_History_Track" FOREIGN KEY ("TrackID")
      REFERENCES "Track" ("ID") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FK_History_User" FOREIGN KEY ("UserID")
      REFERENCES "User" ("ID") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "History"
  OWNER TO postgres;
COMMENT ON TABLE "History"
  IS '������� �������������';
COMMENT ON COLUMN "History"."LastListenDateTime" IS '����� �������������';


-- Index: "FKI_History_Track"

-- DROP INDEX "FKI_History_Track";

CREATE INDEX "FKI_History_Track"
  ON "History"
  USING btree
  ("TrackID");

-- Index: "FKI_History_User"

-- DROP INDEX "FKI_History_User";

CREATE INDEX "FKI_History_User"
  ON "History"
  USING btree
  ("UserID");



-- Function: registerfile(uuid, character varying, character varying, uuid)

-- DROP FUNCTION registerfile(uuid, character varying, character varying, uuid);

CREATE OR REPLACE FUNCTION registerfile(
    "ID" uuid,
    "LocalDevicePathUpload" character varying,
    "Path" character varying,
    "UserID" uuid)
  RETURNS void AS
$BODY$INSERT INTO "User" ("ID","Name")
SELECT $4, 'Anonymous new user'
WHERE NOT EXISTS(SELECT * FROM "User" WHERE "ID"=$4);

INSERT INTO "Track"("ID", "LocalDevicePathUpload", "Path", "UploadUserID") 
VALUES($1, $2, $3, $4);

INSERT INTO "History"("UserID","TrackID")
VALUES($4,$1)$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION registerfile(uuid, character varying, character varying, uuid)
  OWNER TO postgres;


  
INSERT INTO public."User" ("ID", "Name") VALUES ('12345678-1234-1234-1234-123456789012', 'Test User');
INSERT INTO public."Device" ("ID", "UserID", "Name") VALUES ('00000000-0000-0000-0000-000000000000', '12345678-1234-1234-1234-123456789012', 'TEST-USER-PC');