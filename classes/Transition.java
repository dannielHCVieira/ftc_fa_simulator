package classes;

public class Transition {
    private String from;
    private String to;
    private String read;//se for ler lambda deve ter valor vazio == ""

    public Transition(){
        
    }

    public Transition(String from, String to, String read) {
        this.from = from;
        this.to = to;
        this.read = read;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}
