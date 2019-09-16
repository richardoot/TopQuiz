package com.example.topquiz.Modele;

public class User {
    private String m_name;


    //Getteur
        public String getName() {
            return m_name;
        }

    //Setteur
        public void setName(String name) {
            m_name = name;
        }

    //Autres
        @Override
        public String toString() {
            return "User{" +
                    "m_name='" + m_name + '\'' +
                    '}';
        }
}
