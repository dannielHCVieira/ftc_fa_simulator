package classes;

import java.util.ArrayList;
import java.util.List;

public class Automaton{

    private List<State> states;
    private List<Transition> transitions;

    public Automaton(){
        states = new ArrayList<>();
        transitions = new ArrayList<>();
    }

    public Automaton(List<State> states, List<Transition> transitions) {
        this.states = states;
        this.transitions = transitions;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public List<State> getStates() {
        return states;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }


    


    
}