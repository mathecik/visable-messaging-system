-- Table: public.MESSAGE

-- DROP TABLE IF EXISTS public."MESSAGE";

CREATE TABLE IF NOT EXISTS public."MESSAGE"
(
    "ID" integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    "FROM" integer NOT NULL,
    "TO" integer NOT NULL,
    "CONTENT" text COLLATE pg_catalog."default",
    CONSTRAINT "ID" PRIMARY KEY ("ID"),
    CONSTRAINT from_user FOREIGN KEY ("FROM")
        REFERENCES public."LILA_USER" ("ID") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT to_user FOREIGN KEY ("TO")
        REFERENCES public."LILA_USER" ("ID") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."MESSAGE"
    OWNER to postgres;
-- Index: fki_from_user

-- DROP INDEX IF EXISTS public.fki_from_user;

CREATE INDEX IF NOT EXISTS fki_from_user
    ON public."MESSAGE" USING btree
    ("FROM" ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: fki_to_user

-- DROP INDEX IF EXISTS public.fki_to_user;

CREATE INDEX IF NOT EXISTS fki_to_user
    ON public."MESSAGE" USING btree
    ("TO" ASC NULLS LAST)
    TABLESPACE pg_default;