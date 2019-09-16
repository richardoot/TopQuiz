package com.example.topquiz.Modele;

import java.util.List;

public class Question {
    //Attributs
        private String m_question;
        private List<String> m_choiceAnswer;
        private int m_indexAnswer;

    //Constructor
        public Question(String question, List<String> choiceAnswer, int indexAnswer) {
            this.m_question = question;
            this.m_choiceAnswer = choiceAnswer;
            this.m_indexAnswer = indexAnswer;
        }

    //Getteurs
        public String getQuestion() {
            return m_question;
        }

        public List<String> getChoiceAnswer() {
            return m_choiceAnswer;
        }

        public int getIndexAnswer() {
            return m_indexAnswer;
        }


    //Setteurs
        public void setQuestion(String question) {
            m_question = question;
        }

        public void setChoiceAnswer(List<String> choiceAnswer) {
            m_choiceAnswer = choiceAnswer;
        }

        public void setIndexAnswer(int indexAnswer) {
            m_indexAnswer = indexAnswer;
        }
}
