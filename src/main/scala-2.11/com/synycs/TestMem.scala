package com.synycs

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf

/**
  * Created by synycs on 21/12/16.
  */
object TestMem {

  def main(arr:Array[String]):Unit={

    val conf=new SparkConf().setAppName("MemCheck").
      setMaster("spark://instance-3:7077").
      setSparkHome("/home/synycs/spark-2.0.2-bin-hadoop2.6")
      //setJars(Seq("/home/synycs/scala/proj/target/scala-2.11/proj_2.11-1.0.jar"))

    val spark = SparkSession
      .builder.config(conf)
      .getOrCreate()

    val rows=spark.sparkContext.textFile("/home/synycs/rows.json")

    val df=spark.read.json("/home/synycs/rows.json")
    df.schema.foreach{
      x=>println(x)
    }
    df.show()

    println(rows.count())
    spark.stop()

  }

}
