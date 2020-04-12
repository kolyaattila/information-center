package com.information.center.questionservice.readFile;

import com.information.center.questionservice.entity.AnswerEntity;
import com.information.center.questionservice.entity.AnswerKey;
import com.information.center.questionservice.entity.QuestionDifficulty;
import com.information.center.questionservice.entity.QuestionEntity;
import com.information.center.questionservice.repository.AnswerRepository;
import com.information.center.questionservice.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("/uploadFile")
    public Object uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String text = new String(file.getBytes(), StandardCharsets.UTF_8);
        int i;
        List<Question> questions = new ArrayList<>();
        for (i=1; i<= 1293;i++){
            System.out.println("Index: "+i);
            int index =-1;
            index=     text.indexOf(i + ".");
            if(index<0){
                System.out.println("Nu sa gasit: "+i);
            } else {
                Question question = new Question();
                question.setQuestionNumber(i);
                question.setStartIndex(index);

                int endIndex = text.indexOf((i +1)+ ".");

                if(i==1293) question.setEndIndex(text.length()-1);
                else question.setEndIndex(endIndex > 0 ? endIndex : index);
                question.setValue(text.substring(question.getStartIndex(),question.getEndIndex()));
                questions.add(question);
                if(question.getEndIndex() - question.getStartIndex() > 1000)
                {
                    System.out.println("some problem with: "+i);
                }

                text = text.replace(question.getValue() ,"");
            }
        }
        questions.forEach(this::setBookAndChapter);
        questions.forEach(Question::createAnswer);

        List<QuestionEntity> collect = questions.stream().map(this::convertQuestionToEntity)
                .map(questionRepository::save)
                .collect(Collectors.toList());
        return null;
    }

    private void setBookAndChapter(Question question){
        question.setBook("Chimie organica - Teste pentru admitere");
        if(question.getQuestionNumber()>0 && question.questionNumber<206){
            question.setChapter("Capitolul 1. Solutii. Acizi si baze");

        } else if(question.getQuestionNumber()>=206 && question.questionNumber<260){
            question.setChapter("Capitolul 2. Compozitia si structura compusilor organici");
        }else if(question.getQuestionNumber()>=260 && question.questionNumber<419){
            question.setChapter("Capitolul 3. Compusi hidroxilici");
        }
        else if(question.getQuestionNumber()>=419 && question.questionNumber<531){
            question.setChapter("Capitolul 4. Amine");
        }
        else if(question.getQuestionNumber()>=531 && question.questionNumber<655){
            question.setChapter("Capitolul 5. Aldehide si cetone");
        }
        else if(question.getQuestionNumber()>=665 && question.questionNumber<872){
            question.setChapter("Capitolul 6. Acizi carboxilici si derivati functionali al acizilor carboxilici");
        }else if(question.getQuestionNumber()>=872 && question.questionNumber<989){
            question.setChapter("Capitolul 7. Amino acizi, peptide, proteine");
        }else if(question.getQuestionNumber()>=989 && question.questionNumber<1108){
            question.setChapter("Capitolul 8. Zaharide(glucide)");
        }
        else if(question.getQuestionNumber()>=1108 && question.questionNumber<1193){
            question.setChapter("Capitolul 9. Medicamente, enzime, vidamine, hormoni, acizi nucleici, droguri");
        }else if(question.getQuestionNumber()>=1193 && question.questionNumber<1294){
            question.setChapter("Capitolul 10. Izomerie");
        }
    }

    private QuestionEntity convertQuestionToEntity(Question question) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setBook(question.getBook());
        questionEntity.setChapter(question.getChapter());
        questionEntity.setName(question.getName());
        questionEntity.setQuestionNumber(question.getQuestionNumber());
        questionEntity.setQuestionDifficulty(QuestionDifficulty.EASY);
        questionEntity.setExternalId(getQuestionExternalId());
        questionEntity.setAnswers(question.getAnswerList()
                .stream()
                .map(answer -> {return this.convertAnswerToEntity(answer, questionEntity);})
                .collect(Collectors.toList()));

        return questionEntity;
    }

    private AnswerEntity convertAnswerToEntity(Answer answer, QuestionEntity questionEntity) {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setBook(answer.getBook());
        answerEntity.setKey(AnswerKey.getKey(answer.getKey()));
        answerEntity.setName(answer.getValue());
        answerEntity.setQuestionNumber(answer.getQuestionNumber());
        answerEntity.setBook(answer.getBook());
        answerEntity.setExternalId(getAnswerExternalId());
        answerEntity.setQuestion(questionEntity);
        return answerEntity;
    }

    private String getAnswerExternalId() {
        UUID uuid = UUID.randomUUID();
        if (answerRepository.existsByExternalId(uuid.toString())) {
            return this.getAnswerExternalId();
        }
        return uuid.toString();
    }

    private String getQuestionExternalId() {
        UUID uuid = UUID.randomUUID();
        if (questionRepository.existsByExternalId(uuid.toString())) {
            return this.getQuestionExternalId();
        }
        return uuid.toString();
    }
}
