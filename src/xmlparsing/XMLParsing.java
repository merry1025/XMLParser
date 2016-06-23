package xmlparsing;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParsing {

    /**
     * A method to loop through all elements and print them out. This method is
     * put in a try...catch block for exception handling.
     */
    public static void parse() {

        try {

            File xmlFile = new File("resources/class.xml");

            /* -- 1. Create a DocumentBuilder --*/
            // Factory design pattern. instead of instantiating an object each time with new. The Factory class takes care of building a Document instance.
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            //Document builder is asked to parse the xml file
            Document doc = docBuilder.parse(xmlFile);

            //Print out the tag name of the root element
            //doc.getDocumentElement() will get first element.
            System.out.println("Document Element: " + doc.getDocumentElement().getTagName() + "\n");

            /*-- 2. Look for all the students --*/
            // Each element is called a node.
            // We will use NodeList to print them out.
            // Will return a list of elements with tag name "student"
            // This can be done if we know the tag names    
            NodeList students = doc.getElementsByTagName("student");

            // Loop through the list
            for (int idx = 0; idx < students.getLength(); idx++) {
                Node student = students.item(idx); // Each student is a node
                Element studentElm = (Element) student; // Parse the node to element to get the access the attributes of each element.

//System.out.println(studentElm.getChildNodes().item(5).getTextContent());
                System.out.println("ID: " + studentElm.getAttribute("id"));
                System.out.print("Firstname: ");
                //Get  the Text inside of the opening tags  <firstname> and closing tags: </firstname>
                System.out.println(studentElm.getElementsByTagName("firstname").item(0).getTextContent());
                System.out.print("Lastname: ");
                System.out.println(studentElm.getElementsByTagName("lastname").item(0).getTextContent());
                System.out.print("Age: ");
                System.out.println(studentElm.getElementsByTagName("age").item(0).getTextContent() + "\n ");
                //studentElm.getElementsByTagName("age").item(0).getNodeType();

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    
    
        public static void create() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element rootElement = doc.createElement("class");
        doc.appendChild(rootElement);
//creating the first student
        Element student1 = doc.createElement("student");
        student1.setAttribute("id", "123456");//set an attribute to the tag name student
        Element firstname1 = doc.createElement("firstname");
        firstname1.setTextContent("Josiane"); //append this text inside the firstname opening and closing tags
        student1.appendChild(firstname1);//append the firstname to the student
        Element lastname1 = doc.createElement("lastname");
        lastname1.setTextContent("Gamgo");
        student1.appendChild(lastname1);
        rootElement.appendChild(student1); // attach the first student element inside the root element

//creating the second student
        Element student2 = doc.createElement("student");
        Attr student2Id = doc.createAttribute("id");

        student2Id.setValue("987654");
        student2.setAttributeNode(student2Id);
        Element firstname2 = doc.createElement("firstname");
        firstname2.setTextContent("James");
        student2.appendChild(firstname2);

        rootElement.appendChild(student2);//attach the second student to the root

//Use a transformer to create the xml document with the created element
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domsource = new DOMSource(doc);
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        StreamResult stream = new StreamResult(new File("resources/newclass.xml"));
        transformer.transform(domsource, stream);
      
       
        // System.out.println(doc.toString());
    }

    public static void main(String[] args) {
        XMLParsing.parse();
        
        try{
        XMLParsing.create();
    } catch (Exception e){
     e.printStackTrace();        
    }

}
}
