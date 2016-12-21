package com.synycs.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UserHandler extends DefaultHandler {

   boolean bFirstName = false;
   boolean bLastName = false;
   boolean bNickName = false;
   boolean bMarks = false;

   @Override
   public void startElement(String uri,  String localName, String qName, Attributes attributes)
      throws SAXException {

      System.out.println(qName+" ");

      switch (qName.toUpperCase()){
         case "REUTERS":
            break;
         case "DATE":
            break;
         case "TOPICS":
            break;
         case "PLACES":
            break;
         case "PEOPLE":
            break;
         case "ORGS":
            break;
         case "EXCHANGES":
            break;
         case "COMPANIES":
            break;
         case "UNKNOWN":
            break;
         case "TEXT":
            break;
         case "TITLE":
            break;
         case "DATELINE":
            break;
         case "BODY":
            break;

      }

   }

   @Override
   public void endElement(String uri, 
   String localName, String qName) throws SAXException {
      if (qName.equalsIgnoreCase("REUTERS")) {
         System.out.println("End Element :" + qName);
      }
   }

   @Override
   public void characters(char ch[], 
      int start, int length) throws SAXException {
      String df=new String(ch,start,length);
     System.out.println(df);

//      if (bFirstName) {
//         System.out.println("First Name: "
//            + new String(ch, start, length));
//         bFirstName = false;
//      } else if (bLastName) {
//         System.out.println("Last Name: "
//            + new String(ch, start, length));
//         bLastName = false;
//      } else if (bNickName) {
//         System.out.println("Nick Name: "
//            + new String(ch, start, length));
//         bNickName = false;
//      } else if (bMarks) {
//         System.out.println("Marks: "
//            + new String(ch, start, length));
//         bMarks = false;
//      }
   }
}
