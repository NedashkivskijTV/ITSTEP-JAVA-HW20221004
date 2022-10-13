package edu.itstep;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Question implements Serializable {
    public String question;
    public String rightAnswer;
    public String answer1;
    public String answer2;
    public String answer3;
    public Question() {
    }
    public Question(String objString) {
        Pattern pattern = Pattern.compile("(.*;)(.*;)(.*;)(.*;)(.*;)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(objString);
        //System.out.println(matcher);
        if (matcher.matches()) {
            question = matcher.group(1);
            rightAnswer = matcher.group(2);
            answer1 = matcher.group(3);
            answer2 = matcher.group(4);
            answer3 = matcher.group(5);
        } else {
            throw new RuntimeException();
        }
    }
    public Question(String question, String rightAnswer, String answer1, String answer2, String answer3) {
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                ", answer1='" + answer1 + '\'' +
                ", answer2='" + answer2 + '\'' +
                ", answer3='" + answer3 + '\'' +
                '}';
    }
}
