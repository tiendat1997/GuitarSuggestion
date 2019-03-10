/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.generator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiendat.common.CrawlCommon;
import tiendat.ultility.JaxbUtils;

/**
 *
 * @author DATTTSE62330
 */
public class XJCGenerateJavaObject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String[] schemas = new String[]{"attribute.xsd","guitar.xsd","category.xsd","categories.xsd",};
            for (String schema : schemas) {
                JaxbUtils.generate(CrawlCommon.PATH_SCHEMA + schema);
            }
            System.out.println("Finish object generating");
        } catch (IOException ex) {
            Logger.getLogger(XJCGenerateJavaObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
