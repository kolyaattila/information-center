ALTER TABLE Topic
ALTER COLUMN external_id TYPE varchar(50);

ALTER TABLE Topic
    ADD COLUMN course_id bigint not null;

ALTER TABLE Topic
ADD CONSTRAINT topic_course_id_fk FOREIGN KEY (course_id)
REFERENCES Course(id);
