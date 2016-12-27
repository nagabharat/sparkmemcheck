package com.synycs

import org.apache.spark.ml.feature.{RegexTokenizer, Tokenizer}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._

import scala.collection.mutable

/**
  * Created by synycs on 15/12/16.
  */
object ParquetCheck {

  def main(args: Array[String]) {
    val spark=SparkSession.builder().appName("parquet").master("local").getOrCreate()
//    val loaded:DataFrame=spark.read
//      .format("parquet")
//      //  .option("charset","UTF-8")
//      .load("/home/synycs/Downloads/returs/com/content.parquet")
//
//    loaded.printSchema()
//    loaded.createOrReplaceTempView("content")
//
//    val data=spark.sql("select BODY from content")

    val sentenceData = spark.createDataFrame(Seq(
      ("0", "Hi I heard about Spark"),
      ("1", "I wish i could use scala and spark and i am good at java"),
      ("2", "i, Logistic regression models are neat")
    )).toDF("label", "sentence")


//    val tokenizer = new RegexTokenizer("").setInputCol("sentence").setOutputCol("words")
//
//    val wordData=tokenizer.transform(sentenceData)
//    wordData.show()

   // idf(sentenceData.select("sentence"),"hi")

  // println(idf(sentenceData.select("sentence"),"i"))

    tokensForEach(sentenceData.rdd).foreach(println(_))

    spark.stop()

  }

  def idf(dataFrame: DataFrame,term:String): Double={
    var count=0
    dataFrame.collect().foreach{
      x=>val words=x(0).toString.split("(\\s|[.]|,)")
        var already=false
        for (word <-words){
          if (term.equalsIgnoreCase(word) && !already){
            already=true
            count+=1
          }
        }
    }

    math.log((dataFrame.count() + 1) / (count + 1))

  }

  /**
    *tokens for each documents
    *
    * @param rdd
    * @return
    */
  def tokensForEach(rdd:RDD[Row]): RDD[(String,Int)] ={

    val pair=rdd.flatMap{x=>val li=x(1).toString.toLowerCase.split("(\\s|[.]|,)")

      val map=new mutable.HashSet[String]()
      for (y <- li){
        if(!map.contains(y)) {
          map.add(y)
        }
      }
      for (y <-map) yield (y,1)

    }.reduceByKey(_+_)
    return pair
  }

  /**
    * tokens for all documents
    *
    * @param rdd
    * @return
    */
  def tokensForAll(rdd:RDD[Row]): RDD[((String),Int)] ={

    val pair=rdd.flatMap{x=>x(1).toString.toLowerCase.split("(\\s|[.]|,)")}
      .map(x=>(x,1)).reduceByKey(_+_)
    return pair
  }




}
