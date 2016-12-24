package com.synycs.consumerdata

import java.io.File

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame

/**
  * Created by synycs on 24/12/16.
  */
object DataXml {
  def main(args: Array[String]) {


    val spark = SparkSession
      .builder
      .appName("").master("local[2]")
      .getOrCreate()

//    val rdf=spark.sparkContext.textFile("/home/synycs/scala/proj/Consumerdata.xml").
//      map{x=>x.replaceAll("<row>","").
//        replaceAll("</row>(\\s)*</response>","</response>")
//      }
//    rdf.saveAsTextFile("/home/synycs/scala/proj/Consumerdata-ref.xml")



        val forSchema=spark.read
          .format("com.databricks.spark.xml")
          .option("rootTag", "response")
          .option("rowTag", "row")
          //  .option("charset","UTF-8")
          //      .schema(customSchema)()
          .load("/home/synycs/data/refinedxml")

    forSchema.write.parquet("/home/synycs/data/parq")

        forSchema.show()
    spark.stop()



  }



}
