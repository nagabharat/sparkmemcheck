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
    val loaded:DataFrame=spark.read
      .format("parquet")
      //  .option("charset","UTF-8")
      .load("/home/synycs/Downloads/returs/com/content.parquet")

    loaded.printSchema()
    loaded.createOrReplaceTempView("content")

    val data=spark.sql("select _NEWID,BODY from content")

    data.show()


    val count=data.count()

    val cachedRdd=data.rdd.filter(x=>x!=null&&x(0)!=null&&x(1)!=null).cache()
    val rdd=tokensForEach(cachedRdd)
    val idfRdd= rdd.map{
      x=>val y=(count+1)/(x._2+1)
       val y1=math.log(y)
        (x._1,y1)
    }
    idfRdd.foreach(println(_))

    val totalWord=tokensForAll(cachedRdd)

    val ic=idfRdd.join(totalWord).map{
      case (id,(idf,totcount))=>{
        val countlog=math.log(totcount)
        (id,idf,idf*countlog)
      }
    }

    ic.foreach{
      println(_)
    }


    spark.stop()

  }



  /**
    *tokens counted once for each documents
    *
    * @param rdd
    * @return
    */
  def tokensForEach(rdd:RDD[Row]): RDD[(String,Double)] ={

    val pair=rdd.flatMap{x=> val li=x(1).toString.toLowerCase.split("(\\s|[.]|,)")

      val map=new mutable.HashSet[String]()
      for (y <- li){
        if(!map.contains(y)) {
          map.add(y)
        }
      }
      for (y <-map) yield (y,1.0)

    }.reduceByKey(_+_)
     pair
  }

  /**
    * tokens for all documents
    *
    * @param rdd
    * @return
    */
  def tokensForAll(rdd:RDD[Row]): RDD[((String),Double)] ={

    val pair=rdd.flatMap{x=>
      x(1).toString.toLowerCase.split("(\\s|[.]|,)") }
      .map(x=>(x,1.0)).reduceByKey(_+_)
     pair
  }




}
