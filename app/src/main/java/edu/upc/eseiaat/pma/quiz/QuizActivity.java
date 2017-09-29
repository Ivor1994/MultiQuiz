package edu.upc.eseiaat.pma.quiz;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private int ids_answers[] = {
            R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4
    };
    private int correct_answer;
    private int current_question;
    private String[] all_question;
    private boolean[] answer_is_correct;
    private  int[] answer;
    private TextView text_question;
    private RadioGroup group;
    private Button btn_check, btn_prev;



    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        text_question = (TextView) findViewById(R.id.text_question);
        group =(RadioGroup) findViewById(R.id.answer_group);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_prev = (Button) findViewById(R.id.btn_prev);

        all_question = getResources().getStringArray(R.array.all_question);
        answer_is_correct = new boolean[all_question.length];
        answer = new int[all_question.length];
        for (int i=0; i < answer.length; i++){
            answer[i]= -1;
        }
        current_question = 0;
        showQuestion();

       // final int correct_answers =getResources().getInteger(R.integer.correct_answer);

        //TODO: cuando clicas el boton hay que pasar de pregunta


        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();

                if(current_question < all_question.length-1) {
                    current_question++;
                    showQuestion();
                } else{
                    int correctas = 0, incorrectas =0;
                    for (boolean b : answer_is_correct){
                        if (b) correctas++;
                        else incorrectas++;
                    }
                    String resultado = String.format("Correctas: %d -- Incorrectas: %d", correctas, incorrectas);

                    Toast.makeText(QuizActivity.this, resultado, Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkAnswer();
                if (current_question > 0){
                    current_question--;
                    showQuestion();
                }
            }
        });

    }

    private void checkAnswer() {
        int id= group.getCheckedRadioButtonId();
        int ans= 1;
        for (int i=0; i < ids_answers.length; i++) {
            if (ids_answers[i] == id){
                ans= i;
            }
        }


        answer_is_correct[current_question] = (ans == correct_answer);
        answer[current_question] = ans;
    }


    private void showQuestion() {
        //TextView text_question = ; String s = ;
        String q = all_question[current_question];
        String[] parts = q.split(";");

        group.clearCheck();
        text_question.setText(parts[0]);
        //String[] answers = getResources().getStringArray(R.array.answers);
        for (int i=0; i < ids_answers.length;i++){
            RadioButton rb= (RadioButton) findViewById(ids_answers[i]);
            String ans = parts[i+1];
            if (ans.charAt(0) == '*') {
                correct_answer = i;
                ans = ans.substring(1);
            }
            rb.setText(ans);
            if (answer[current_question] == i){
                rb.setChecked(true);
            }
        }

        if (current_question == 0){
            btn_prev.setVisibility(View.GONE);
        } else {
            btn_prev.setVisibility(View.VISIBLE);
        }

        if (current_question == all_question.length-1){
            btn_check.setText(R.string.finish);
        } else {
            btn_check.setText(R.string.next);
        }
    }
}

