/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.tomcat.util.codec.binary.StringUtils;
import tiendat.common.CrawlCommon;
import tiendat.ultility.MyCrawler;

/**
 *
 * @author DATTTSE62330
 */
public class TestCrawler {

    public static void main(String[] args) {
        String resultFilePath;
        String xmlConfigPath;
        String xslPath;
        
        xmlConfigPath = CrawlCommon.CONFIG_WEBCRAWL;       
        
        // NHACCUTIENDAT
        resultFilePath = CrawlCommon.PATH_XML_OUTPUT + "output_nhaccu_tiendat.xml";        
        xslPath = CrawlCommon.XSL_NHACCU_TIENDAT;
        runCrawl(xmlConfigPath, xslPath, resultFilePath);
        //DUY GUITAR
        resultFilePath = CrawlCommon.PATH_XML_OUTPUT + "output_duy_guitar.xml";
        xslPath = CrawlCommon.XSL_DUYGUITAR;
        runCrawl(xmlConfigPath, xslPath, resultFilePath);
        //BADON DUITAR       
        resultFilePath = CrawlCommon.PATH_XML_OUTPUT + "output_badon_guitar.xml";
        xslPath = CrawlCommon.XSL_BADONGUITAR;
        runCrawl(xmlConfigPath, xslPath, resultFilePath);
        // GUITAR_STATION 
        resultFilePath = CrawlCommon.PATH_XML_OUTPUT + "output_guitar_station.xml";        
        xslPath = CrawlCommon.XSL_GUITAR_STATION;
        runCrawl(xmlConfigPath, xslPath, resultFilePath);
    }

    public static void runCrawl(String xmlConfigPath, String xslPath, String resultFilePath) {
        DOMResult rs;
        try {
            rs = MyCrawler.crawl(xmlConfigPath, xslPath);
            MyCrawler.saveToXMLFile(rs, resultFilePath);
            MyCrawler.saveToDB(resultFilePath, "");
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(TestCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(TestCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(TestCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
