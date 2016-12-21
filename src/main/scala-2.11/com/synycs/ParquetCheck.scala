package com.synycs

import org.apache.spark.sql._

/**
  * Created by synycs on 15/12/16.
  */
object ParquetCheck {

  def main(args: Array[String]) {
    val spark=SparkSession.builder().appName("parquet").master("local").getOrCreate()
    val loaded:DataFrame=spark.read
      .format("parquet")
      //  .option("charset","UTF-8")
      .load("/home/synycs/Downloads/returs/com/content.parquet")

    loaded.printSchema()
    loaded.createOrReplaceTempView("content")

    spark.sql("select BODY from content").show(20)

  }

}
