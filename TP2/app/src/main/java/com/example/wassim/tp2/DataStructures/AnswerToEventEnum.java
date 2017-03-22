package com.example.wassim.tp2.DataStructures;

/**
 * Created by Louis-Philippe on 3/16/2017.
 */

public enum AnswerToEventEnum {
    YES("Participe"),
    NO("Ne participe pas"),
    MAYBE("Participe peut-etre"),
    NOTANSWERED("Sans reponse");
    String message;
    AnswerToEventEnum(String s){
        message = s;
    }
    public String ToString(){
        return message;
    }
}
