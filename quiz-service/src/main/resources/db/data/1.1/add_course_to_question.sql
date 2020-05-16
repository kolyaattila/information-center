ALTER TABLE Question
    ADD COLUMN course_id varchar(50);

ALTER TABLE Question
ALTER COLUMN topic_id TYPE varchar(50);

ALTER TABLE Question
ADD COLUMN school_id bigint;

ALTER TABLE Question
ADD CONSTRAINT question_school_id_fk FOREIGN KEY (school_id)
REFERENCES School(id);
