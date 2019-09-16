package com.example.topquiz.Controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.topquiz.Modele.User;
import com.example.topquiz.R;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Déclaration des éléments graphiques
        EditText m_inputName;
        Button m_btn;
        TextView m_message;

        private User m_user;
        private SharedPreferences m_preferences;

        public static final int GAME_ACTIVITY_REQUESTE_CODE = 42;
        public static final String PREF_KEY_NAME = "PREF_KEY_NAME";
        public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Référencement graphique
            m_inputName = (EditText) findViewById(R.id.main_activity_input);
            m_btn = (Button) findViewById(R.id.main_activity_btn);
            m_message = (TextView) findViewById(R.id.main_activity_message);

        //Initialisation d'autres éléments
            m_user = new User();
            m_preferences = getPreferences(MODE_PRIVATE);

        //Désactiver le bouton
            m_btn.setEnabled(false);

        //Vérifier si l'utilisateur a déjaà fait une partie
            Map<String,?> mapPreferences = m_preferences.getAll();
            Log.i("DEBUG", "OUI IL A DEJA FAIT UNE PARTIE !" + m_preferences.getAll());


            Log.i("DEBUG", "Pseudo:: " + m_preferences.getAll().get(PREF_KEY_NAME));


        if(m_preferences.getAll().size() != 0){
                m_inputName.setVisibility(View.GONE);
                m_btn.setEnabled(true);
                m_message.setText("Hey "
                                + m_preferences.getAll().get(PREF_KEY_NAME)
                                + " la dernière fois t'as fais " + m_preferences.getAll().get(PREF_KEY_SCORE)
                                + " point(s) est-ce que tu feras mieux cette fois ?");
        }

        //Mise en place Listener sur l'input
            m_inputName.addTextChangedListener(inputListener);

        //Mise en place Listener Bouton
            m_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Récupérer le nom de l'utilisateur
                        String pseudo = m_inputName.getText().toString();
                        m_user.setName(pseudo);

                        m_preferences.edit().putString(PREF_KEY_NAME, m_user.getName()).apply();
                        Log.i("DEBUG",m_user.getName());

                    //Envoyer l'utilisateur à la GameActivity
                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        startActivityForResult(intent, GAME_ACTIVITY_REQUESTE_CODE);
                }
            });
    }

    private TextWatcher inputListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            m_btn.setEnabled(charSequence.length() != 0);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(GAME_ACTIVITY_REQUESTE_CODE == requestCode && RESULT_OK == resultCode){
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            m_preferences.edit().putInt(PREF_KEY_SCORE, score).apply();
        }
    }
}
