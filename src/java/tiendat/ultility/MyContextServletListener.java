/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.ultility;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import tiendat.common.CrawlCommon;

/**
 *
 * @author DATTTSE62330
 */
@WebListener()
public class MyContextServletListener implements ServletContextListener {
    private final String XML_OUTPUT = "WEB-INF/classes/tiendat/fileOutput/output_badon_guitar.xml";
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("APPLICATION IS DEPLOYING.....");
        ServletContext context = sce.getServletContext();
        String realPath = context.getRealPath("/");
        String xmlFilePath = realPath + XML_OUTPUT;
        System.out.println("REAL PATH: " + realPath);
        System.out.println("XML FILE PATH: " + xmlFilePath );
        try {
            Document doc = XMLUltils.parseDOMFromFile(xmlFilePath);
            if (doc != null) {
                context.setAttribute("DOM", doc);
            }
        } catch (SAXException ex) {
            Logger.getLogger(MyContextServletListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MyContextServletListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyContextServletListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
