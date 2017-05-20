/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.CharArrayReader;
import java.io.File;
import java.io.Reader;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;

/**
 *
 * @author Wasif
 */
public class XMLProcessor {

    public static Document getBlankXMLDocument() throws Exception {
        Document doc;

        try {
            //Create instance of DocumentBuilderFactory   
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);

            //Get the DocumentBuilder 
            DocumentBuilder parser = factory.newDocumentBuilder();

            //Create blank DOM  Document
            doc = parser.newDocument();

        } catch (Exception e) {
            throw new Exception("getBlankXMLDocument() has gotten some problem : "
                    + e + " : " + e.getMessage());            
        }
        return doc;
    }

    public static Document getXMLDocumentForXMLFile(String xml_File_URL) throws Exception {
        Document doc;

        try {
            /* Create instance of DocumentBuilderFactory  */
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);

            /* Get the DocumentBuilder */
            DocumentBuilder parser = factory.newDocumentBuilder();

            /* Create blank DOM  Document */ 
            doc = parser.parse(new File(xml_File_URL));

        } catch (Exception e) {
            throw new Exception("getXMLDocumentForXMLFile(String xml_File_URL) has gotten some problem : "
                    + e + " : " + e.getMessage());            
        }
        return doc;
    }

    public static void transformXML(Document doc, Result destination) throws Exception {

        try {
            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();

            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

            /* 5.1 Describes the domain of the indent-amount parameter (apache xst specific, non JAXP generic). */
            aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            /* 5.2 Set source and destination */
            Source src = new DOMSource(doc);

            //Result dest = new StreamResult(System.out);
            //aTransformer.transform(src, dest);
            
            Result dest = destination;

            aTransformer.transform(src, dest);

        } catch (Exception e) {
            throw new Exception("transformXML has gotten some problem : "
                    + e + " : " + e.getMessage());
        }
    }

    public static Document stringToXmlDocument(String stringToConvert) throws Exception {
        Document doc;
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Reader reader;

        try {
            /* create a factory. */
            factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);

            /* use a document builder factory. */
            builder = factory.newDocumentBuilder();

            /* parse the results that were sent back from the server. */
            reader = new CharArrayReader(stringToConvert.toCharArray());
            doc = builder.parse(new org.xml.sax.InputSource(reader));

        } catch (Exception e) {
            throw new Exception("stringToXmlDocument has gotten some problem : "
                    + e + " : " + e.getMessage());
        }

        return doc;
    }
}


