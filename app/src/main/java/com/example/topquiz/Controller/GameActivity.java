package com.example.topquiz.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topquiz.Modele.Question;
import com.example.topquiz.Modele.QuestionBank;
import com.example.topquiz.R;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    //Eléments graphiques
        private TextView m_question;

        private Button m_btn1;
        private Button m_btn2;
        private Button m_btn3;
        private Button m_btn4;

    //Autres éléments
        private QuestionBank m_questionBank;
        private Question m_currentQuestion;

        private int m_maxNumberQuestion;
        private int m_numberOfQuestions;
        private int m_score;

        private boolean m_enableTouchEvent;

    //Constantes
    public static final String  BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String  BUNDLE_STATE_SCORE = "currentScore";
    public static final String  BUNDLE_STATE_QUESTION  = "currentQuestion";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        System.out.println("GameActivity::onCreate()");

        //Référencement des éléments graphiques
            m_question = (TextView) findViewById(R.id.game_activity_question);

            m_btn1 = (Button) findViewById(R.id.game_activity_btn1);
            m_btn2 = (Button) findViewById(R.id.game_activity_btn2);
            m_btn3 = (Button) findViewById(R.id.game_activity_btn3);
            m_btn4 = (Button) findViewById(R.id.game_activity_btn4);

        //Définir un Tag pour chaque question
            m_btn1.setTag(0);
            m_btn2.setTag(1);
            m_btn3.setTag(2);
            m_btn4.setTag(3);

        //Initialiser le nombre question
            m_maxNumberQuestion = 5;

        //Vérifier que le bundle existe
            if(savedInstanceState != null) {
                m_score = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
                m_numberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
                System.out.println("Il y a une sauvegarde !! la val est de " + m_numberOfQuestions);
            } else {
                m_score = 0;
                m_numberOfQuestions = m_maxNumberQuestion;
                System.out.println("PAS DE sauvegarde !!");
            }

        //Initialisationdes autres éléments
            m_questionBank = this.generateQuestions();
            m_questionBank.setNextQuestion(m_maxNumberQuestion - m_numberOfQuestions);
            m_currentQuestion = m_questionBank.getQuestion();
            m_enableTouchEvent = true;


        //Afficher la question
            m_question.setText(m_currentQuestion.getQuestion());

        //Afficher les réponses
            this.displayAnswers(m_currentQuestion);
    }

    private QuestionBank generateQuestions(){
        Question question1 = new Question("Qui est le 3ème président de la 5ème République ?",
                Arrays.asList("Jacques Chirac","Valéry Giscard d'Estaing","Georges Pompidou","François Mitterrand"),
                1);

        Question question2 = new Question("En quelle année Charlemagne se fait-il sacrer empereur ?",
                Arrays.asList("800 ap J.C.","775 ap J.C.","850 ap J.C.","750 ap J.C."),
                0);

        Question question3 = new Question("Qui aurait dit “Malheur aux vaincus” (Vae victis) après avoir mis Rome à sac ?",
                Arrays.asList("Hannibal","Jules César","Vercingétorix","Brennos"),
                3);

        Question question4 = new Question("Avec la laine de quel animal fait-on du cachemire ?",
                Arrays.asList("Du mouton","Du vison","Du lapin","De la chèvre"),
                3);

        Question question5 = new Question("Parmi les villes suivantes, laquelle est à la fois en Asie et en Europe ?",
                Arrays.asList("Alexandrie","Rhodes","Moscou","Istanbul"),
                3);

        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5));

    }

    private void displayAnswers(final Question question){
        m_btn1.setText(question.getChoiceAnswer().get(0));
        m_btn2.setText(question.getChoiceAnswer().get(1));
        m_btn3.setText(question.getChoiceAnswer().get(2));
        m_btn4.setText(question.getChoiceAnswer().get(3));

        m_btn1.setOnClickListener(this);
        m_btn2.setOnClickListener(this);
        m_btn3.setOnClickListener(this);
        m_btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Récupérer l'index du bouton
            int indexBtn = (int) view.getTag();
            Log.i("DEBUG", "L'index du bouton est : " + indexBtn);

        //Vérifier qu'il s'agit de la bonne réponse
            if(indexBtn == m_currentQuestion.getIndexAnswer()){
                //Bonne réponse
                    Toast.makeText(this,"Correct !", Toast.LENGTH_SHORT).show();

                //Incrémenter le score
                    m_score++;
            } else {
                //Mavaise réponse
                    Toast.makeText(this,"Wrong !", Toast.LENGTH_SHORT).show();
            }

        //Désactiver les boutons
            m_enableTouchEvent = false;
        System.out.println("Nb questions : " + m_numberOfQuestions);

        //Mise en place d'un timer
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                //Réactiver le bouton
                    m_enableTouchEvent = true;

                //Passer à la nouvelle question
                    m_numberOfQuestions--;

                    if(m_numberOfQuestions == 0){
                        //Fin du jeu
                            endGame();
                    } else {
                        //Afficher la question
                            m_currentQuestion = m_questionBank.getQuestion();
                            m_question.setText(m_currentQuestion.getQuestion());

                        //Afficher les réponses
                            displayAnswers(m_currentQuestion);
                    }
                }
            }, 500);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putInt(BUNDLE_STATE_SCORE, m_score);
        outState.putInt(BUNDLE_STATE_QUESTION, m_numberOfQuestions);

        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void endGame(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Bravo !")
            .setMessage("Vous avez un score de : " + m_score + " points")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent();
                    intent.putExtra(BUNDLE_EXTRA_SCORE, m_score);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            })
            .create()
            .show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return m_enableTouchEvent && super.dispatchTouchEvent(ev);
    }



    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("GameActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("GameActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("GameActivity::onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        System.out.println("GameActivity::onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("GameActivity::onDestroy()");


    }
}
