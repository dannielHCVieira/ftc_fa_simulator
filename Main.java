import classes.Automaton;
import classes.State;
import classes.Transition;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.*;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        /* var state = new State("0", true, true, "q0");
        var transition = new Transition("0", "0", "0");*/

        var automaton = Operations.readXml(); 

        Operations.saveAutomaton(automaton);
    }
}
