/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.ultility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import tiendat.common.CrawlCommon;

/**
 *
 * @author DATTTSE62330
 */
public class MyURIResolver implements URIResolver, Serializable {

    @Override
    public Source resolve(String href, String base) throws TransformerException {
        if (href != null && (href.indexOf(CrawlCommon.URL_GUITAR_DUY) == 0
                || href.indexOf(CrawlCommon.URL_GUITAR_BADON) == 0
                || href.indexOf(CrawlCommon.URL_GUITAR_STATION) == 0
                || href.indexOf(CrawlCommon.URL_NHACCU_TIENDAT) == 0)) {
            try {
                URL url = new URL(href);
                URLConnection connection = url.openConnection();
                connection.addRequestProperty("User-Agent", CrawlCommon.USER_AGENT);
                connection.setReadTimeout(8 * 1000);
                connection.setConnectTimeout(8 * 1000);
                System.out.println("Connect: " + href);
                InputStream httpResult = connection.getInputStream();
                StreamSource ss = preProcessInputStream(httpResult);
                return ss;
            } catch (MalformedURLException ex) {
                Logger.getLogger(MyURIResolver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MyURIResolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private StreamSource preProcessInputStream(InputStream httpResult) throws IOException {
        String textContent = getString(httpResult);

        textContent = TextUtils.refineHtml(textContent);
        InputStream htmlResult = new ByteArrayInputStream(textContent.getBytes("UTF-8"));
        return new StreamSource(htmlResult);
    }

    private static String getString(InputStream stream) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException ex) {
        }
        return stringBuilder.toString();
    }
}
