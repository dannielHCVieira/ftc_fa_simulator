import classes.Automaton;
import classes.State;
import classes.Transition;

import javax.xml.parsers.ParserConfigurationException;

import exceptions.SentencaNaoAceita;
import org.xml.sax.SAXException;

import java.io.IOException;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.*;

import java.awt.event.*;
public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
//         var state = new State("0", true, true, "q0");
//        var transition = new Transition("0", "0", "0");
//        var transition2 = new Transition("0", "0", "1");// (0 + 1)*
//        var automaton = new Automaton(List.of(state), List.of(transition, transition2));
        
        
        Operations.openWindow();
//        var automaton = Operations.readXml();
//        automaton = Operations.transformAFNToAFD(automaton);
//        Operations.saveAutomaton(automaton);
        /* var automaton = Operations.readXml();
        var str = Operations.readInput(); 
        while(str != null){
            try{
                Operations.runAutomaton(str, automaton);
                Operations.showSucessDialog(str);
                str = Operations.readInput();
                
            } catch (SentencaNaoAceita e) {
                Operations.showFailedDialog(str);
                System.out.println(e.getMessage());
                str = Operations.readInput();
            }
        }    */
        //var automaton = Operations.readXml();

        //Operations.saveAutomaton(automaton);
    }
}
