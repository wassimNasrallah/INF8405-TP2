/**
 * Created by Louis-Philippe on 3/16/2017.
 */

public enum AnswerToEvent {
    YES("Participe"),
    NO("Ne participe pas"),
    MAYBE("Participe peut-etre"),
    NOTANSWERED("Sans reponse");
    String message;
    AnswerToEvent(String s){
        message = s;
    }
    public String ToString(){
        return message;
    }
}
