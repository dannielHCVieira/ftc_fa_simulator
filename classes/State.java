package classes;

public class State {
    private String id;
    private String name;
    private boolean isInitial, isFinal;

    public State(){
        
    }

    public State(String id, boolean isInitial, boolean isFinal, String name) {
        this.id = id;
        this.name = name;
        this.isInitial = isInitial;
        this.isFinal = isFinal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }
}
