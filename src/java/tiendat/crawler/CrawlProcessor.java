/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.crawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import tiendat.common.CrawlCommon;
import tiendat.test.TestCrawler;
import static tiendat.test.TestCrawler.runCrawl;
import tiendat.ultility.MyCrawler;

/**
 *
 * @author DATTTSE62330
 */
public class CrawlProcessor implements Serializable {

    public void crawl(String realPath) {
        String resultFilePath;
        String xmlConfigPath;
        String xslPath;
        System.out.println("REALPATH: " + realPath);

        xmlConfigPath = CrawlCommon.CONFIG_WEBCRAWL;
        
        // GOLDMUSIC
        resultFilePath = CrawlCommon.PATH_XML_OUTPUT + "output_goldmusic.xml";
        xslPath = CrawlCommon.XSL_GOLDMUSIC;
        runCrawl(xmlConfigPath, xslPath, resultFilePath, realPath);
        // NHACCUTIENDAT
        resultFilePath = CrawlCommon.PATH_XML_OUTPUT + "output_nhaccu_tiendat.xml";
        xslPath = CrawlCommon.XSL_NHACCU_TIENDAT;
        runCrawl(xmlConfigPath, xslPath, resultFilePath, realPath);
        //DUY GUITAR
        resultFilePath = CrawlCommon.PATH_XML_OUTPUT + "output_duy_guitar.xml";
        xslPath = CrawlCommon.XSL_DUYGUITAR;
        runCrawl(xmlConfigPath, xslPath, resultFilePath, realPath);
        //BADON DUITAR       
        resultFilePath = CrawlCommon.PATH_XML_OUTPUT + "output_badon_guitar.xml";
        xslPath = CrawlCommon.XSL_BADONGUITAR;
        runCrawl(xmlConfigPath, xslPath, resultFilePath, realPath);
        // GUITAR_STATION 
        resultFilePath = CrawlCommon.PATH_XML_OUTPUT + "output_guitar_station.xml";
        xslPath = CrawlCommon.XSL_GUITAR_STATION;
        runCrawl(xmlConfigPath, xslPath, resultFilePath, realPath);
    }

    private void runCrawl(String xmlConfigPath, String xslPath, String resultFilePath, String realPath) {
        DOMResult rs;
        try {
            rs = MyCrawler.crawl(realPath+xmlConfigPath, realPath+xslPath);
            MyCrawler.saveToXMLFile(rs, realPath+resultFilePath);
            MyCrawler.saveToDB(realPath+resultFilePath, realPath);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(TestCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (JAXBException ex) {
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
