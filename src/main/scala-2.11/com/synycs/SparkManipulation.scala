package com.synycs

import java.nio.file.Path
import java.util.stream

import org.apache.spark.{SparkContext, SparkConf}
import  java.io._
/**
  * Created by synycs on 13/12/16.
  *
  * change the format of xml doc to standard xml
  */
object SparkManipulation {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("xmltext").setMaster("local")
    val sc=new SparkContext(conf)
    getListOfFiles("/home/synycs/Downloads/returs/allsgm").foreach{
      x=>etlOnFile(sc,x)
    }


  }

  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def etlOnFile(sc:SparkContext,f:File): Unit ={
    val x=sc.textFile(f.getAbsolutePath)
    val y=x.filter(!_.contains("<!DOCTYPE lewis SYSTEM \"lewis.dtd\">"))
    val top=sc.parallelize(Seq("<?xml version=\"1.1\" ?>","<dummy>"))
    val  bot=sc.parallelize(Seq("</dummy>"))
    val all=top.union(y).union(bot)
   val rep= all.map(x=>x.replaceAll("&#[0-9]([0-9])*;",""))
    rep.repartition(1).saveAsTextFile("/home/synycs/Downloads/returs/com/"+f.getName)
  }



}
