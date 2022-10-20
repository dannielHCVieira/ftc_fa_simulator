import classes.Automaton;
import classes.State;
import classes.Transition;

import javax.xml.parsers.ParserConfigurationException;

import exceptions.SentencaNaoAceita;
import org.xml.sax.SAXException;

import java.io.IOException;

import java.util.List;

public class Main {
    public static void main(String[] args) {
         var state = new State("0", true, true, "q0");
        var transition = new Transition("0", "0", "0");
        var transition2 = new Transition("0", "0", "1");// (0 + 1)*
        var automaton = new Automaton(List.of(state), List.of(transition, transition2));

        try{
            var str = "000111";
            Operations.runAutomaton(str, automaton);
            System.out.println("Sentença aceita!");

        } catch (SentencaNaoAceita e) {
            System.out.println("Sentença não foi aceita por autômato");
            //throw new RuntimeException(e);
        }
        //var automaton = Operations.readXml();

        //Operations.saveAutomaton(automaton);
    }
}
