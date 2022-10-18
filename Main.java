import classes.Automaton;
import classes.State;
import classes.Transition;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException {
        var state = new State("0", true, true, "q0");
        var transition = new Transition("0", "0", "0");

        var automaton = new Automaton(List.of(state), List.of(transition));


        Operations.saveAutomaton(automaton);
    }
}
