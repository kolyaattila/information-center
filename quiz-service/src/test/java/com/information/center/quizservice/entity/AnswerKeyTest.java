package com.information.center.quizservice.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnswerKeyTest {

    @Test
    public void testValueOf() {
        AnswerKey answerKey = AnswerKey.valueOf("A");

        assertEquals(AnswerKey.A, answerKey);
    }

    @Test
    public void testGetKey_expectA() {
        AnswerKey key = AnswerKey.getKey("A.");

        assertEquals(AnswerKey.A, key);
    }

    @Test
    public void testGetKey_expectB() {
        AnswerKey key = AnswerKey.getKey("B.");

        assertEquals(AnswerKey.B, key);
    }

    @Test
    public void testGetKey_expectC() {
        AnswerKey key = AnswerKey.getKey("C.");

        assertEquals(AnswerKey.C, key);
    }

    @Test
    public void testGetKey_expectD() {
        AnswerKey key = AnswerKey.getKey("D.");

        assertEquals(AnswerKey.D, key);
    }

    @Test
    public void testGetKey_expectE() {
        AnswerKey key = AnswerKey.getKey("E.");

        assertEquals(AnswerKey.E, key);
    }

    @Test
    public void testGetKeyWhenSentUnknownKey_expectA() {
        AnswerKey key = AnswerKey.getKey("W.");

        assertEquals(AnswerKey.A, key);
    }
}
