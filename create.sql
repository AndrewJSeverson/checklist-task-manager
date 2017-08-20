-- Table: email_item

-- DROP TABLE email_item;

CREATE TABLE email_item
(
  email_item_id serial NOT NULL,
  body character varying(2550) NOT NULL,
  sent boolean NOT NULL,
  subject character varying(255) NOT NULL,
  to_email character varying(100) NOT NULL,
  CONSTRAINT email_item_pkey PRIMARY KEY (email_item_id)
);

-- Table: task_user

-- DROP TABLE task_user;

CREATE TABLE task_user
(
  user_id serial NOT NULL,
  email character varying(100) NOT NULL,
  first_name character varying(100) NOT NULL,
  last_name character varying(100) NOT NULL,
  CONSTRAINT task_user_pkey PRIMARY KEY (user_id)
);

-- Table: checklist

-- DROP TABLE checklist;

CREATE TABLE checklist
(
  checklist_id serial NOT NULL,
  creation_date timestamp without time zone,
  last_update_date timestamp without time zone,
  name character varying(255) NOT NULL,
  checklist_catgory_id integer,
  CONSTRAINT checklist_pkey PRIMARY KEY (checklist_id),
  CONSTRAINT fk9cx8u8dganynfxvw1k7eq8t1g FOREIGN KEY (checklist_catgory_id)
      REFERENCES checklist_category (checklist_category_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: checklist_category

-- DROP TABLE checklist_category;

CREATE TABLE checklist_category
(
  checklist_category_id serial NOT NULL,
  name character varying(100) NOT NULL,
  CONSTRAINT checklist_category_pkey PRIMARY KEY (checklist_category_id)
);

-- Table: checklist_user

-- DROP TABLE checklist_user;

CREATE TABLE checklist_user
(
  checklist_user_id serial NOT NULL,
  checklist_id integer NOT NULL,
  user_id integer NOT NULL,
  CONSTRAINT checklist_user_pkey PRIMARY KEY (checklist_user_id),
  CONSTRAINT fkjc79gx47ls1tc221inmnqr3hf FOREIGN KEY (user_id)
      REFERENCES task_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkpbuukwwlxrcyysejas0y90a57 FOREIGN KEY (checklist_id)
      REFERENCES checklist (checklist_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: checklist_task

-- DROP TABLE checklist_task;

CREATE TABLE checklist_task
(
  checklist_task_id serial NOT NULL,
  description character varying(255) NOT NULL,
  due_date timestamp without time zone,
  due_date_email_send boolean,
  reminder_date timestamp without time zone,
  reminder_date_email_send boolean,
  checklist_id integer,
  CONSTRAINT checklist_task_pkey PRIMARY KEY (checklist_task_id),
  CONSTRAINT fkjxt6a9nka2e03fjj6fq7cabrs FOREIGN KEY (checklist_id)
      REFERENCES checklist (checklist_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: task_completion

-- DROP TABLE task_completion;

CREATE TABLE task_completion
(
  task_completion_id serial NOT NULL,
  completion_date timestamp without time zone,
  checklist_task_id integer,
  checklist_user_id integer,
  CONSTRAINT task_completion_pkey PRIMARY KEY (task_completion_id),
  CONSTRAINT fkne2i6p56701xapj5x1krm0p1c FOREIGN KEY (checklist_user_id)
      REFERENCES checklist_user (checklist_user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fkrgt55j7w3jjd8uvfg5tv77n7c FOREIGN KEY (checklist_task_id)
      REFERENCES checklist_task (checklist_task_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);