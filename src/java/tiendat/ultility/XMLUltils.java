/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.ultility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author DATTTSE62330
 */
public class XMLUltils {

    public static <T> String marshallToString(T obj) {
        try {
            JAXBContext jaxb = JAXBContext.newInstance(obj.getClass());
            Marshaller marshal = jaxb.createMarshaller();
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            
            StringWriter sw = new StringWriter();
            marshal.marshal(obj, sw);
            
            return sw.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(XMLUltils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static <T> void marshallToTransfer(T obj, OutputStream os) {

        try {
            JAXBContext jaxb = JAXBContext.newInstance(obj.getClass());
            Marshaller marshal = jaxb.createMarshaller();
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshal.marshal(obj, os);
        } catch (JAXBException ex) {
            Logger.getLogger(XMLUltils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static <T> boolean validateXML(T obj, String xmlFilePath, String xsdPath) throws IOException {
        try {
            JaxbUtils.marshal(obj, xmlFilePath);

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(xsdPath));
            InputSource source = new InputSource(new BufferedReader(new FileReader(xmlFilePath)));
            Validator validator = schema.newValidator();
            validator.validate(new SAXSource(source));
            System.out.println("Validated");
            return true;
        } catch (SAXException ex) {
            System.out.println("\nERROR validate-----------------------------");
            System.out.println(ex.getMessage() + "\n");
            return false;
        }
    }

    public static Document parseDOMFromFile(String xmlFileName) throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFileName); // nen han che chuyen new File
        return doc;
    }

    public static XPath createXPath() {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        return xpath;
    }
}
