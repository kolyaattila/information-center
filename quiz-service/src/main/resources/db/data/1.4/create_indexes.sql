CREATE INDEX I_ANSWER_0 ON ANSWER (QUESTION_ID);
CREATE INDEX I_QUESTION_0 ON QUESTION (SCHOOL_ID);
CREATE INDEX I_ANSWERED_QUESTION_0 ON ANSWERED_QUESTION (QUESTION_ID);
CREATE INDEX I_QUIZ_0 ON QUIZ (SCHOOL_ID);
CREATE INDEX I_SOLVED_QUIZ_0 ON SOLVED_QUIZ (QUIZ_ID);
