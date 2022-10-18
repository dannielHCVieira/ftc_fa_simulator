import classes.Automaton;
import classes.State;
import classes.Transition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Operations {

    public static void saveAutomaton(Automaton automaton) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        // parse XML file
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document output = db.newDocument();
        Element root = output.createElement("structure");
        output.appendChild(root);
        Element type = output.createElement("type");
        type.setTextContent("fa");
        root.appendChild(type);
        Element test = output.createElement("teste");
        root.appendChild(test);

        automaton.getStates().forEach( state -> createState(state, output, root));
        automaton.getTransitions().forEach(transition -> createTransition(transition, output, root));

        try {
            FileOutputStream outputFile =
                    new FileOutputStream("output.xml");
            writeXml(output, outputFile);
        } catch (IOException e) {
            e.printStackTrace();

        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

    }

    private static void createTransition(Transition transition, Document document, Element root){
        Element elementTransition = document.createElement("transition");

        Element from = document.createElement("from");
        from.setTextContent(transition.getFrom());
        elementTransition.appendChild(from);

        Element to = document.createElement("to");
        to.setTextContent(transition.getTo());
        elementTransition.appendChild(to);

        Element read = document.createElement("read");
        if(!transition.getRead().isEmpty()){
            read.setTextContent(transition.getRead());
        }
        elementTransition.appendChild(read);

        root.appendChild(elementTransition);
    }

    private static void createState(State state, Document document, Element root){
        Element elementState = document.createElement("state");
        elementState.setAttribute("id", state.getId());
        elementState.setAttribute("name", state.getName());

        Element x = document.createElement("x");
        Element y = document.createElement("y");
        x.setTextContent("0.0");
        y.setTextContent("0.0");

        elementState.appendChild(x);
        elementState.appendChild(y);

        if(state.isFinal()){
            Element finalState = document.createElement("final");
            elementState.appendChild(finalState);
        }

        if(state.isInitial()){
            Element initialState = document.createElement("initial");
            elementState.appendChild(initialState);
        }
        root.appendChild(elementState);
    }

    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
}
