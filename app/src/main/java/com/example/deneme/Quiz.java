package com.example.deneme;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

public class Quiz extends AppCompatActivity {
    private TextView question;
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private Button choice4;
    private ArrayList<Question> questions;
    private int questionNo;
    private int correctAnswer;
    private int numberOfCorrectAnswers;
    private TextView score;

    private void readQuestionsFromFile(){
        InputStream stream = getResources().openRawResource(R.raw.filmsorular);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-16")));
        Question newQuestion;
        String question;
        questions = new ArrayList<Question>();
        try {
            while ((question = reader.readLine()) != null) {
                String[] questionContent = question.split(";");
                if ((questionContent[0].equalsIgnoreCase("T") || questionContent[0].equalsIgnoreCase("M") || questionContent[0].equalsIgnoreCase("Y"))
                        && (questionContent[6].equalsIgnoreCase("A") || questionContent[6].equalsIgnoreCase("B") || questionContent[6].equalsIgnoreCase("C") || questionContent[6].equalsIgnoreCase("D"))){
                    newQuestion = new Question(questionContent[0], questionContent[1], questionContent[2], questionContent[3], questionContent[4], questionContent[5], questionContent[6]);
                    questions.add(newQuestion);
                }
            }
        } catch (IOException e) {
        }
    }
    private Question getCurrentQuestion(){
        int whichQuestion=0, i;
        if (questionNo < 10){
            whichQuestion = questionNo;

        }
        i = 0;
        for (Question question: questions){

                if (i == whichQuestion){
                    return question;
                } else {
                    i++;
                }

        }
        return null;
    }
    private void showCurrentQuestion(){
        Question currentQuestion;
        currentQuestion = getCurrentQuestion();
        if (currentQuestion != null){
            question.setText((questionNo + 1) + ") " + currentQuestion.getQuestion());
            choice1.setText("A) " + currentQuestion.getChoice1());
            choice2.setText("B) " + currentQuestion.getChoice2());
            choice3.setText("C) " + currentQuestion.getChoice3());
            choice4.setText("D) " + currentQuestion.getChoice4());
            score.setText("Score:"+numberOfCorrectAnswers*10);
            if (currentQuestion.getCorrectAnswer().equalsIgnoreCase("A")){
                correctAnswer = 1;
            } else {
                if (currentQuestion.getCorrectAnswer().equalsIgnoreCase("B")){
                    correctAnswer = 2;
                } else {
                    if (currentQuestion.getCorrectAnswer().equalsIgnoreCase("C")){
                        correctAnswer = 3;
                    } else {
                        correctAnswer = 4;
                    }
                }
            }
        }
    }
    private void startExam(){

        questionNo = 0;
        numberOfCorrectAnswers = 0;
        Collections.shuffle(questions);
        showCurrentQuestion();
        choice1.setEnabled(true);
        choice2.setEnabled(true);
        choice3.setEnabled(true);
        choice4.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question = (TextView) findViewById(R.id.question);
        question.setEnabled(false);
        choice1 = (Button) findViewById(R.id.choice1);
        choice2 = (Button) findViewById(R.id.choice2);
        choice3 = (Button) findViewById(R.id.choice3);
        choice4 = (Button) findViewById(R.id.choice4);
        score = (TextView)findViewById(R.id.score);
        Button startExam = (Button) findViewById(R.id.sinaviBaslat);
        choice1.setOnClickListener(choiceClick);
        choice2.setOnClickListener(choiceClick);
        choice3.setOnClickListener(choiceClick);
        choice4.setOnClickListener(choiceClick);
        Button exitMenu = (Button)findViewById(R.id.EXIT);
        exitMenu.setOnClickListener(exitExamClick);



        startExam.setOnClickListener(startExamClick);
        readQuestionsFromFile();
        showCurrentQuestion();

        startExam();

    }
    public View.OnClickListener choiceClick = new View.OnClickListener() {
        public void onClick(View v){
            if (((String)v.getTag()).equalsIgnoreCase(Integer.toString(correctAnswer))){
                numberOfCorrectAnswers++;

            }
            questionNo++;
            if (questionNo < 10){
                showCurrentQuestion();

            } else {
                String message = "You answered " + numberOfCorrectAnswers + " questions correctly!";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                choice1.setEnabled(false);
                choice2.setEnabled(false);
                choice3.setEnabled(false);
                choice4.setEnabled(false);
            }
        }
    };
    public View.OnClickListener startExamClick = new View.OnClickListener() {
        public void onClick(View v){
            startExam();
        }
    };
    public View.OnClickListener exitExamClick = new View.OnClickListener() {

        public void onClick(View v) {
            Intent intocan = new Intent(Quiz.this,MainActivity.class);
            startActivity(intocan);

        }
    };

}
