package com.synycs

import java.io.File

import org.apache.spark.sql._

/**
  * Created by synycs on 17/12/16.
  */
object TestCsv {
  def main(args: Array[String]): Unit = {
    val spark=SparkSession.builder().appName("csv").master("local").getOrCreate()

    //val dat= spark.read.option("header","true").option("inferSchema","true").csv("test.csv")

//    val data=spark.sparkContext.textFile("test.csv")
//    data.foreach{x=>val r=x.split(",")
//    r.foreach(print(_))
//      println()
//    }
//


    val dat=spark.read.format("csv").option("header","true").option("inferSchema","false").
      option("parserLib", "UNIVOCITY").
      load("temp.csv")

//    dat.explain()
    dat.show()
    dat.select("Model").foreach{
      x=>print(x)
        println("end")
    }
   // dat.pa
  //  val x=dat.repartition(2)

   // x.show()

  }

}
