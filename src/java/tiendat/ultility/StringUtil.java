/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.ultility;

/**
 *
 * @author DATTTSE62330
 */
public class StringUtil {

    public static String replaceCodeCharacter(String value) {

        return value
                .replaceAll("&amp;", "&")
                .replaceAll("&Agrave;", "À")
                .replaceAll("&Aacute;", "Á")
                .replaceAll("&Acirc;", "Â")
                .replaceAll("&Atilde;", "Ã")
                .replaceAll("&Auml;", "Ä")
                .replaceAll("&Aring;", "Å")
                .replaceAll("&agrave;", "à")
                .replaceAll("&aacute;", "á")
                .replaceAll("&acirc;", "â")
                .replaceAll("&atilde;", "ã")
                .replaceAll("&auml;", "ä")
                .replaceAll("&aring;", "å")
                .replaceAll("&AElig;", "Æ")
                .replaceAll("&aelig;", "æ")
                .replaceAll("&szlig;", "ß")
                .replaceAll("&Ccedil;", "Ç")
                .replaceAll("&ccedil;", "ç")
                .replaceAll("&Egrave;", "È")
                .replaceAll("&Eacute;", "É")
                .replaceAll("&Ecirc;", "Ê")
                .replaceAll("&Euml;", "Ë")
                .replaceAll("&egrave;", "è")
                .replaceAll("&eacute;", "é")
                .replaceAll("&ecirc;", "ê")
                .replaceAll("&euml;", "ë")
                .replaceAll("&#131;", "ƒ")
                .replaceAll("&Igrave;", "Ì")
                .replaceAll("&Iacute;", "Í")
                .replaceAll("&Icirc;", "Î")
                .replaceAll("&Iuml;", "Ï")
                .replaceAll("&igrave;", "ì")
                .replaceAll("&iacute;", "í")
                .replaceAll("&icirc;", "î")
                .replaceAll("&iuml;", "ï")
                .replaceAll("&Ntilde;", "Ñ")
                .replaceAll("&ntilde;", "ñ")
                .replaceAll("&Ograve;", "Ò")
                .replaceAll("&Oacute;", "Ó")
                .replaceAll("&Ocirc;", "Ô")
                .replaceAll("&Otilde;", "Õ")
                .replaceAll("&Ouml;", "Ö")
                .replaceAll("&ograve;", "ò")
                .replaceAll("&oacute;", "ó")
                .replaceAll("&ocirc;", "ô")
                .replaceAll("&otilde;", "õ")
                .replaceAll("&ouml;", "ö")
                .replaceAll("&Oslash;", "Ø")
                .replaceAll("&oslash;", "ø")
                .replaceAll("&#140;", "Œ")
                .replaceAll("&#156;", "œ")
                .replaceAll("&#138;", "Š")
                .replaceAll("&#154;", "š")
                .replaceAll("&Ugrave;", "Ù")
                .replaceAll("&Uacute;", "Ú")
                .replaceAll("&Ucirc;", "Û")
                .replaceAll("&Uuml;", "Ü")
                .replaceAll("&ugrave;", "ù")
                .replaceAll("&uacute;", "ú")
                .replaceAll("&ucirc;", "û")
                .replaceAll("&uuml;", "ü")
                .replaceAll("&#181;", "µ")
                .replaceAll("&#215;", "×")
                .replaceAll("&Yacute;", "Ý")
                .replaceAll("&#159;", "Ÿ")
                .replaceAll("&yacute;", "ý")
                .replaceAll("&yuml;", "ÿ");

    }
}
