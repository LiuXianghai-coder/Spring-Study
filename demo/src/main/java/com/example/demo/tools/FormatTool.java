package com.example.demo.tools;

import org.apache.ibatis.io.Resources;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

public class FormatTool {

    private final static String SRC = "src";
    private final static String HREF = "href";

    public static void format(Document doc) {
        Elements elements = doc.getAllElements();
        for (Element element : elements) {
            if (element.hasAttr(SRC)) {
                String val = element.attr(SRC);
                if (!val.startsWith("https")) {
                    element.attr(SRC, "https:" + val);
                }
            }
            if (element.hasAttr(HREF)) {
                String val = element.attr(HREF);
                if (!val.startsWith("https")) {
                    element.attr(HREF, "https:" + val);
                }
            }
        }
        System.out.println(doc);
    }

    public static void format() {
        try (InputStream in = Resources.getResourceAsStream("data.html")) {
            Document document = Jsoup.parse(in, null, "jd.com");
            format(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
