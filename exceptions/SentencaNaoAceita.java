package exceptions;

public class SentencaNaoAceita extends Exception{
    private String message;
    public SentencaNaoAceita(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
