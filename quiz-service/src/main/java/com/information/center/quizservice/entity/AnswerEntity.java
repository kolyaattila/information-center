package com.information.center.quizservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "Answer")
@Getter
@Setter
public class AnswerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "external_id", nullable = false, length = 30)
	private String externalId;

	@Column(name = "name", nullable = false, length = 500)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "question_id", nullable = false, updatable = false)
	private QuestionEntity question;

	@Column(name = "correct")
	private boolean correct;

	@Column(name = "reason", length = 500)
	private String reason;

	@Enumerated(EnumType.STRING)
	@Column(name = "key", length = 2, nullable = false)
	private AnswerKey key;

	@Column(name = "question_number")
	private int questionNumber;

	@Column(name = "book", length = 100)
	private String book;

	@Column(name = "created")
	private Date created = new Date();

	@Column(name = "parse_text", length = 2000, nullable = true)
	private String parseText;

	@Column(name = "answer_type", length = 16, nullable = false)
	private AnswerType answerType;

	@ManyToMany(mappedBy = "answerEntities", cascade = CascadeType.PERSIST)
	Collection<AnsweredQuestionEntity> answeredQuestionEntities;
}
