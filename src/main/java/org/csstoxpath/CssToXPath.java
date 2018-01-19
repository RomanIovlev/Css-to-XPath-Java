package org.csstoxpath;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 1/5/2018.
 */
public class CssToXPath {
    public static String cssToXPath(String css) {
        if (css == null || css.isEmpty()) return "";
        int i = 0; int start; int length = css.length();
        String result = "//";
        while (i < length) {
            char symbol = css.charAt(i);
            if (isTagLetter(symbol)) {
                start = i;
                while(i < length && isTagLetter(css.charAt(i))) i++;
                if (i == length) return result + css.substring(start);
                result += css.substring(start, i);
                continue;
            }
            if (symbol == ' ') {
                result += "//"; i++; continue;
            }
            if (asList('.','#','[').contains(symbol)) {
                List<String> attributes = new ArrayList<>();
                while (i < length && css.charAt(i) != ' ') {
                    switch (css.charAt(i)) {
                        case '.':
                            i++; start = i;
                            while (i < length && isAttrLetter(css.charAt(i))) i++;
                            attributes.add(convertToClass(i == length
                                ? css.substring(1)
                                : css.substring(start, i)));
                            break;
                        case '#':
                            i++; start = i;
                            while (i < length && isAttrLetter(css.charAt(i))) i++;
                            attributes.add(convertToId(i == length
                                ? css.substring(1)
                                : css.substring(start, i)));
                            break;
                        case '[':
                            i++; String attribute = "@";
                            while (i < length && (!asList('=',']').contains(css.charAt(i)))) {
                                attribute += css.charAt(i);
                                i++;
                            }
                            if (css.charAt(i) == '=') {
                                attribute += "=";
                                i++;
                                if (css.charAt(i) != '\'') attribute += "'";
                                while (i < length && css.charAt(i) != ']') {
                                    attribute += css.charAt(i);
                                    i++;
                                }
                                if (i == length)
                                    throw new RuntimeException("Incorrect Css. No ']' symbol");
                                if (attribute.charAt(attribute.length() - 1) != '\'') attribute += "'";
                            }
                            attributes.add(attribute);
                            i++;
                            break;
                        default: throw new RuntimeException(format("Can't process Css. Unexpected symbol %s in attributes", css.charAt(i)));
                    }
                }
                if (result.charAt(result.length()-1) == '/') result += "*";
                result += "[" + String.join(" and ", attributes)+ "]";
                continue;
            }
            throw new RuntimeException(format("Can't process Css. Unexpected symbol '%s'", symbol));
        }
        return result;
    }
    private static String convertToClass(String value) {
        return "contains(@class,'" + value + "')";
    }
    private static String convertToId(String value) {
        return convertToAtribute("id", value);
    }
    private static String convertToAtribute(String attr, String value) {
        return "@" + attr + "='" + value + "'";
    }
    private static boolean isAttrLetter(char symbol) {
        return symbol >= 'a' && symbol <= 'z' ||
            symbol >= 'A' && symbol <= 'Z' ||
            symbol >= '0' && symbol <= '9' ||
            symbol == '-' || symbol == '_' || symbol == '.'
            || symbol == ':';
    }
    private static boolean isTagLetter(char symbol) {
        return symbol >= 'a' && symbol <= 'z';
    }

}
