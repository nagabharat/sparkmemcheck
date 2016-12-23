
package com.synycs

import java.io.FileInputStream

import com.univocity.parsers.csv.{CsvParserSettings, CsvParser}

object OpenCsvTest{

   def main(args: Array[String]): Unit = {
     call
   }

   def call: Unit ={
     val settings = new CsvParserSettings()
     //the file used in the example uses '\n' as the line separator sequence.
     //the line separator sequence is defined here to ensure systems such as MacOS and Windows
     //are able to process this file correctly (MacOS uses '\r'; and Windows uses '\r\n').
     settings.getFormat.setLineSeparator("\n")
     settings.getFormat.setQuote('"')
     settings.setMaxCharsPerColumn(10000)

     // creates a CSV parser
     val parser = new CsvParser(settings)

     // parses all rows in one go.
     val allRows = parser.parseAll(new FileInputStream("/home/synycs/Downloads/Consumer_Complaints.csv"))

     var col=0;

     allRows.toArray().foreach{
       x=> val y=x.asInstanceOf[Array[String]]
         y.foreach{
           x=>print("["+x+"]")
         }
         col=col+1
         println()
         println("end")

     }
     println(col)

   }

   def openCsvParserData: Unit ={

   }

}