package com.synycs

import java.io.File

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.{StructType, DoubleType, StructField, StringType}
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by synycs on 13/12/16.
  * work with standard xml to produce results
  */
object SparkXml {
  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isDirectory).toList
    } else {
      List[File]()
    }
  }

  def main(args: Array[String]):Unit= {
    val spark=SparkSession.builder().appName("xml-dataFrame").master("local").getOrCreate()

    val files=getListOfFiles("/home/synycs/Downloads/returs/com/")
    val fileSchema=new File("Test.xml")
    val forSchema=spark.read
      .format("com.databricks.spark.xml")
      .option("rowTag", "TEXT")
      .option("rowTag", "REUTERS")
      .option("rootTag", "class")
      //  .option("charset","UTF-8")
      //      .schema(customSchema)()
      .load(fileSchema.getAbsolutePath)

    var data:Option[DataFrame]=None

    files.foreach{ file=>
      val loaded:DataFrame=spark.read
        .format("com.databricks.spark.xml")
        .option("rowTag", "TEXT")
        .option("rowTag", "REUTERS")
        .option("rootTag", "class")
        //  .option("charset","UTF-8")
        .schema(forSchema.schema)
        .load(file.getAbsolutePath)
      data= data match {
         case Some(x)=>{
           Some( x.union(loaded))
         }
         case None=> {
            Some(loaded)
         }

       }
    }

    data match {
      case Some(all)=>{
        all.printSchema()
        all.createOrReplaceTempView("Content")
        val file=spark.sql("select _NEWID,TEXT.TITLE,TEXT.BODY from content")
        file.write.format("parquet").save("/home/synycs/Downloads/returs/com/content.parquet")
        println("Total count "+file.count())

      }
      case None=>println("no elements are available")
    }


   // data.foreach(x=>print(x))




//
//   val sc= spark.sparkContext
//    val file=sc.textFile("Test.xml")
//    val x=file.filter(_.contains("<BODY>"))
//    x.foreach(x=>println(x))
  }




}
