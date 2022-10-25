import classes.Automaton;
import classes.State;
import classes.Transition;
import exceptions.SentencaNaoAceita;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.*;

public class Operations {

    public static String getFilePath() {
        String path = "";
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int r = j.showOpenDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getAbsolutePath();
        } else
            System.out.println("Cancelou abertura!");

        return path;
    }

    public static void runAutomaton(String sentence, Automaton automaton) throws SentencaNaoAceita {

        var currentState = automaton.getStates()
                .stream()
                .filter(State::isInitial)
                .findFirst().orElseThrow();//adicionar excecao para quando nao existe estado inicial

        for (char x :sentence.toCharArray()){

            State finalCurrentState = currentState;

            var stateTransitions = automaton.getTransitions()
                    .stream()
                    .filter(transition1 -> transition1.getFrom().equals(finalCurrentState.getId()))
                    .toList();

            var transitionToMake = stateTransitions.stream()
                    .filter(transition -> transition.getRead().equals(String.valueOf(x)))
                    .findFirst()
                    .orElseThrow(() -> new SentencaNaoAceita("Sentença não %s pertence à linguagem do autômato".formatted(sentence)));


            currentState = automaton.getStates()
                    .stream()
                    .filter( state -> state.getId().equals(transitionToMake.getTo()))
                    .findFirst()
                    .orElseThrow(() -> new SentencaNaoAceita("Sentença não %s pertence à linguagem do autômato".formatted(sentence)));
        }

        if(!currentState.isFinal()){
            throw new SentencaNaoAceita("Sentença %s não pertence à linguagem do autômato".formatted(sentence));
        }

    }
//    pegar estado inicial
//    pegar transicoes que saem desse estado
//    pegar transicao que le caractere x

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

        automaton.getStates().forEach(state -> createState(state, output, root));
        automaton.getTransitions().forEach(transition -> createTransition(transition, output, root));

        try {
            FileOutputStream outputFile = new FileOutputStream("output.xml");
            writeXml(output, outputFile);
        } catch (IOException e) {
            e.printStackTrace();

        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

    }

    private static void createTransition(Transition transition, Document document, Element root) {
        Element elementTransition = document.createElement("transition");

        Element from = document.createElement("from");
        from.setTextContent(transition.getFrom());
        elementTransition.appendChild(from);

        Element to = document.createElement("to");
        to.setTextContent(transition.getTo());
        elementTransition.appendChild(to);

        Element read = document.createElement("read");
        if (!transition.getRead().isEmpty()) {
            read.setTextContent(transition.getRead());
        }
        elementTransition.appendChild(read);

        root.appendChild(elementTransition);
    }

    private static void createState(State state, Document document, Element root) {
        Element elementState = document.createElement("state");
        elementState.setAttribute("id", state.getId());
        elementState.setAttribute("name", state.getName());

        Element x = document.createElement("x");
        Element y = document.createElement("y");
        x.setTextContent("0.0");
        y.setTextContent("0.0");

        elementState.appendChild(x);
        elementState.appendChild(y);

        if (state.isFinal()) {
            Element finalState = document.createElement("final");
            elementState.appendChild(finalState);
        }

        if (state.isInitial()) {
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

    public static Automaton readXml() throws ParserConfigurationException, SAXException, IOException {
        String path = getFilePath();
        Document doc;

        try {
            File xmlFile = new File(path);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xmlFile);

            // Extrair do arquivo XML a lista de estados e transições
            List<State> stateList = getStatesXML(doc);
            List<Transition> transitionList = getTransitionsXML(doc);
            Automaton automatonRead = new Automaton(stateList, transitionList);
            return automatonRead;
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        Automaton automatonRead = new Automaton(); // Passar a lista de transições e estados
        return automatonRead;
    }

    public static String readInput(){
        String inputValue = JOptionPane.showInputDialog("Por favor, digita a palavra:");
        return inputValue;
    }

    public static void showSucessDialog(String word){
        JFrame j = new JFrame();
        JOptionPane.showMessageDialog(j, "Sentença " + word + " aceita!", "Sucesso",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void showFailedDialog(String word){
        JFrame j = new JFrame();
        JOptionPane.showMessageDialog(j, "Sentença " + word + " não aceita!", "Falha",JOptionPane.WARNING_MESSAGE);
    }

    public static List<State> getStatesXML(Document doc) {
        NodeList stateList = doc.getElementsByTagName("state");

        int len = stateList.getLength();
        List<State> states = new ArrayList<State>(len);

        for (int i = 0; i < len; i++) {
            Node node = stateList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String elementId = element.getAttribute("id");
                String elementName = element.getAttribute("name");
                Boolean elementInitial = element.getElementsByTagName("initial").getLength() > 0 ? true : false;
                Boolean elementFinal = element.getElementsByTagName("final").getLength() > 0 ? true : false;

                State state = new State(elementId, elementInitial, elementFinal, elementName);

                states.add(state);
            }
        }

        return states;
    }

    public static List<Transition> getTransitionsXML(Document doc) {
        NodeList transitionList = doc.getElementsByTagName("transition");

        int len = transitionList.getLength();
        List<Transition> transitions = new ArrayList<Transition>(len);

        for (int i = 0; i < len; i++) {
            Node node = transitionList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String elementFrom = element.getElementsByTagName("from").item(0).getTextContent();
                String elementTo = element.getElementsByTagName("to").item(0).getTextContent();
                String elementRead = element.getElementsByTagName("read").item(0).getTextContent();

                Transition transition = new Transition(elementFrom, elementTo, elementRead);

                transitions.add(transition);
            }
        }

        return transitions;
    }
}
