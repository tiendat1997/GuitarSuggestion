/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.ultility;

import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import tiendat.common.CrawlCommon;
import tiendat.dao.AttributeDAO;
import tiendat.dao.CategoryDAO;
import tiendat.dao.GuitarDAO;
import tiendat.generatedObject.Attribute;
import tiendat.generatedObject.Categories;
import tiendat.generatedObject.Category;
import tiendat.generatedObject.Guitar;

/**
 *
 * @author DATTTSE62330
 */
public class MyCrawler implements Serializable {

    public static String covertStringToURL(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void saveToXMLFile(DOMResult dom, String resultFileName) throws TransformerConfigurationException, TransformerException, FileNotFoundException {

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        StreamResult sr = new StreamResult(new FileOutputStream(resultFileName));
        transformer.transform(new DOMSource(dom.getNode()), sr);
    }

    public static DOMResult crawl(String configPath, String xslpath) throws FileNotFoundException, TransformerConfigurationException, TransformerException {
        // init files 
        StreamSource xslCate = new StreamSource(xslpath);
        InputStream is = new FileInputStream(configPath);
        //init transformer api 
        TransformerFactory factory = TransformerFactory.newInstance();
        DOMResult domRs = new DOMResult();
        MyURIResolver resolver = new MyURIResolver();
        // apply uriREsolver 
        factory.setURIResolver(resolver);
        Transformer transformer = factory.newTransformer(xslCate);
        // transform xml config by input xsl 
        StreamSource streamSrc = new StreamSource(is);
        transformer.transform(streamSrc, domRs);
        return domRs;
    }

    public static void saveToDB(String fileInput, String realPath) throws JAXBException, IOException, NamingException, SQLException, ClassNotFoundException {
        Categories categories = (Categories) JaxbUtils.unmarshal(new Categories(), fileInput);
        CategoryDAO categoryDao = new CategoryDAO();
        GuitarDAO guitarDao = new GuitarDAO();
        AttributeDAO attrDao = new AttributeDAO();
        for (Category category : categories.getCategory()) {
            System.out.println("Category " + category.getCategoryName() + " " + category.getLink());
            String xmlOutput = realPath + CrawlCommon.PATH_XML_OUTPUT + "out_category.xml";
            String categorySchemaPath = realPath + CrawlCommon.PATH_SCHEMA + "category.xsd";
            boolean isValidated = XMLUltils.validateXML(category, xmlOutput, categorySchemaPath);
            if (isValidated) {

                for (Guitar guitar : category.getGuitar()) {
                    if (guitar.getCategory().toLowerCase().contains("phụ kiện") == false) {
                        int existedGuitar = guitarDao.getGuitarByName(guitar.getName());
                        if (existedGuitar == 0) {
                            // ADD GUITAR 
                            System.out.println("Guitar: ");
                            System.out.println(guitar.getName());
                            System.out.println(guitar.getPrice());
                            System.out.println(guitar.getCategory());
                            System.out.println(guitar.getImageUrl());
                            
//                            UUID uniqueId = UUID.randomUUID();
//                            String imageName = uniqueId.toString();
//
//                            String rootUrl = realPath.replace("build\\web\\", "");
//                            String outImageUrl = rootUrl + CrawlCommon.URL_IMAGE + "/" + imageName + ".png";                            
//                            String imgUrl = "images?name=" + imageName + ".png";
//                            ImageUtil.saveImageFromURL(guitar.getImageUrl(), outImageUrl);
//                            guitar.setImageUrl(imgUrl);
                            guitarDao.addGuitar(guitar);
                            // GET ID OF GUITAR 
                            int guitarId = guitarDao.getGuitarByName(guitar.getName());
                            // ADD ATTRIBUTE
                            for (Attribute attr : guitar.getAttributes().getAttribute()) {
                                System.out.println("Attribute " + attr.getName() + " " + attr.getContent());
                                if (!attr.getName().trim().isEmpty() && !attr.getContent().trim().isEmpty()){                                                                        
                                    attr.setName(StringUtil.replaceCodeCharacter(attr.getName()));
                                    attr.setContent(StringUtil.replaceCodeCharacter(attr.getContent()));
                                    attrDao.addAttribute(attr, guitarId);
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("---------------------------------------");
        }
    }
}
