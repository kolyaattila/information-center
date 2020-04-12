package com.information.center.questionservice.readFile;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Question {

    int questionNumber;
    String name;
    List<Answer> answerList = new ArrayList<>();
    boolean verified;
    int startIndex;
    String value;
    int endIndex;
    String chapter;
    String book;

    public void createAnswer(){

        List<String> keys = Arrays.asList(this.questionNumber+".","A.","B.","C.","D.","E.");

        keys.forEach(key -> {
            int indexOf = this.value.indexOf(key);

            if(indexOf<0 && (key.equals("A." ) || key.equals("B." ) || key.equals("C." ) || key.equals("D." ) || key.equals("E." ))){
                System.out.println("Question "+questionNumber+" does not have: "+key);
            }
        });

        System.out.println("Parcurg intrebarea "+this.questionNumber);
        try {
            setNameValue();

            setAnswer("A.", "B.");
            setAnswer("B.", "C.");
            setAnswer("C.", "D.");
            setAnswer("D.", "E.");
            setAnswer("E.", null);
        } catch (Exception e){
            System.out.println("~~~~~~~~~Exception for  "+this.questionNumber);
        }
    }

    private void setAnswer(String key1, String key2) {
        int endIndex;
        if(key2==null){
            endIndex=this.value.length()-1;
        }
        else
            endIndex = this.value.indexOf(key2);
        int startIndex = this.value.indexOf(key1);

        Answer answer = new Answer();
        answer.setValue(this.value.substring(startIndex,endIndex));
        answer.setKey(key1);
        answer.setQuestionNumber(this.questionNumber);
        answer.setBook(this.book);
        this.answerList.add(answer);
    }

    private void setNameValue() {
        int startIndex = this.value.indexOf(this.questionNumber+".");
        int endIndex = this.value.indexOf("A.");

        this.name = this.value.substring(startIndex,endIndex);

    }
}

