package com.information.center.quizservice.entity;

public enum AnswerType {
	SIMPLE_ANSWER("SIMPLE_ANSWER"),
	MATH_ANSWER("MATH_ANSWER"),
	CHEMISTRY_ANSWER("CHEMISTRY_ANSWER");


	private String value;

	AnswerType(String value) {
		this.value = value;
	}
}
