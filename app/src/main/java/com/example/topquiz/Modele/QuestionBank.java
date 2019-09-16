package com.example.topquiz.Modele;

import java.util.List;

public class QuestionBank {
    //Attributs
        private List<Question> m_questionList;
        private int m_nextQuestion;

    //Constructeur
        public QuestionBank(List<Question> questionList) {
            m_questionList = questionList;
            m_nextQuestion = 0;
        }

    //Getteurs

    public Question getQuestion() {

        if(m_nextQuestion == m_questionList.size()){
            m_nextQuestion = 0;
        }

        return m_questionList.get(m_nextQuestion++);
    }
}
