package com.synycs.sax.sax1;

/**
 * Created by synycs on 12/12/16.
 */
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParserDemo {
    public static void main(String[] args){

        try {
            File inputFile = new File("input.xml");
            InputSource inputSource=new InputSource(new InputStreamReader(new FileInputStream(inputFile)));
            //inputSource.setEncoding("UTF-8");
            SequenceInputStream sequenceInputStream=new SequenceInputStream(
                    Collections.enumeration(Arrays.asList(
                            new InputStream[] {
                                    new ByteArrayInputStream("<?xml version=\"1.1\" ?>".getBytes()),
                                    new ByteArrayInputStream("<dummy>".getBytes()),
                                    new FileInputStream("input.xml"),//bogus xml
                                    new ByteArrayInputStream("</dummy>".getBytes()),
                            })));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(sequenceInputStream, userhandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
