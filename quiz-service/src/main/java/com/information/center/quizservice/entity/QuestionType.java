package com.information.center.quizservice.entity;

public enum QuestionType {
	SIMPLE_QUESTION("SIMPLE_QUESTION"),
	MATH_QUESTION("MATH_QUESTION"),
	CHEMISTRY_QUESTION("CHEMISTRY_QUESTION");

	private String value;

	QuestionType(String value) {
		this.value = value;
	}
}
