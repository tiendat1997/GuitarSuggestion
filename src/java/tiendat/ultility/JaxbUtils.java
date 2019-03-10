/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.ultility;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 *
 * @author DATTTSE62330
 */
public class JaxbUtils {

    public static <T> boolean marshal(T obj, String outputFilePath){
        try {
            JAXBContext ctx = JAXBContext.newInstance(obj.getClass());
            Marshaller mar = ctx.createMarshaller();
            mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(obj, new File(outputFilePath));
            return true;
        } catch (JAXBException ex) {
            Logger.getLogger(Marshaller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public static <T> T unmarshal(T obj, String inputFilePath) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File f = new File(inputFilePath);
        T result = (T) unmarshaller.unmarshal(f);
        return result;
    }
    
    
    public static void generate(String filePath) throws IOException {
        String output = "src/java";
        SchemaCompiler sc = XJC.createSchemaCompiler();
        sc.setErrorListener(new ErrorListener() {
            @Override
            public void error(SAXParseException saxpe) {
                System.out.println("Error: " + saxpe.getMessage());
            }

            @Override
            public void fatalError(SAXParseException saxpe) {
                System.out.println("Fatal: " + saxpe.getMessage());
            }

            @Override
            public void warning(SAXParseException saxpe) {
                System.out.println("Warning: " + saxpe.getMessage());
            }

            @Override
            public void info(SAXParseException saxpe) {
                System.out.println("Info: " + saxpe.getMessage());
            }
        });
        sc.forcePackageName("tiendat.generatedObject");
        File schema;
        schema = new File(filePath);
        InputSource is = new InputSource(schema.toURI().toString());
        sc.parseSchema(is);
        S2JJAXBModel mode = sc.bind();
        JCodeModel code = mode.generateCode(null, null);
        code.build(new File(output));

        System.out.println("Finished generated Objects");
    }
    
}
