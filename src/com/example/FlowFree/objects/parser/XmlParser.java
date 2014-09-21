package com.example.FlowFree.objects.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristinn on 19.9.2014.
 * Reference: http://www.java-tips.org/java-se-tips/javax.xml.parsers/how-to-read-xml-file-in-java.html
 */
public class XmlParser {

    public XmlParser(){

    }

    public List<Flow> parseXML()
    {
        List<Flow> returnList = new ArrayList<Flow>();
        Flow myFlow;
        String size;
        String flows;

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream stream = getClass().getResourceAsStream("packs/regular.xml");
            Document doc = db.parse(stream);
            doc.getDocumentElement().normalize();

            NodeList nodeChallenges = doc.getElementsByTagName("challenge");

                NodeList nodePuzzles = doc.getElementsByTagName("puzzle");

                for (int p = 0; p < nodePuzzles.getLength(); p++) {

                    Node puzzleNode = nodePuzzles.item(p);
                    Element puzzleElmnt = (Element) puzzleNode;

                    NodeList sizeNmElmntLst = puzzleElmnt.getElementsByTagName("size");
                    Element sizeNmElmnt = (Element) sizeNmElmntLst.item(0);
                    NodeList fstNm = sizeNmElmnt.getChildNodes();
                    size = ((Node) fstNm.item(0)).getNodeValue();

                    NodeList flowsNmElmntLst = puzzleElmnt.getElementsByTagName("flows");
                    Element flowsNmElmnt = (Element) flowsNmElmntLst.item(0);
                    NodeList lstNm = flowsNmElmnt.getChildNodes();
                    flows = (((Node) lstNm.item(0)).getNodeValue());

                    myFlow = new Flow(size, flows);
                    returnList.add(myFlow);

                }
            stream = getClass().getResourceAsStream("packs/mania.xml");
            doc = db.parse(stream);
            doc.getDocumentElement().normalize();

            nodeChallenges = doc.getElementsByTagName("challenge");

            nodePuzzles = doc.getElementsByTagName("puzzle");

            for (int p = 0; p < nodePuzzles.getLength(); p++) {

                Node puzzleNode = nodePuzzles.item(p);
                Element puzzleElmnt = (Element) puzzleNode;

                NodeList sizeNmElmntLst = puzzleElmnt.getElementsByTagName("size");
                Element sizeNmElmnt = (Element) sizeNmElmntLst.item(0);
                NodeList fstNm = sizeNmElmnt.getChildNodes();
                size = ((Node) fstNm.item(0)).getNodeValue();

                NodeList flowsNmElmntLst = puzzleElmnt.getElementsByTagName("flows");
                Element flowsNmElmnt = (Element) flowsNmElmntLst.item(0);
                NodeList lstNm = flowsNmElmnt.getChildNodes();
                flows = (((Node) lstNm.item(0)).getNodeValue());

                myFlow = new Flow(size, flows);
                returnList.add(myFlow);

            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        return returnList;
    }
}
