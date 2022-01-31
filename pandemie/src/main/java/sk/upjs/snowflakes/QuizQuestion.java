package sk.upjs.snowflakes;

import sk.upjs.jpaz2.Turtle;

import java.awt.*;

public class QuizQuestion {
    private String question;

    /** zoznam moznych odpovedi **/
    private String[] options;

    /** index spravnej odpovede **/
    int correctAnswear;

    /** konstruktor **/
    public QuizQuestion(String question, String[] options, int correctAnswear) {
        this.question = question;
        this.options = options;
        this.correctAnswear = correctAnswear;
    }
}
