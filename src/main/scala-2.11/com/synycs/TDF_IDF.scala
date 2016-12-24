package com.synycs

import org.apache.spark.ml.feature._
import org.apache.spark.sql.SparkSession

/**
  * Created by synycs on 12/12/16.
  */
object TDF_IDF {

  def main(args: Array[String]):Unit= {
    val spark = SparkSession
      .builder
      .appName("TfIdfExample").master("local")
      .getOrCreate()
    val sentenceData = spark.createDataFrame(Seq(
      (0.0, "Hi I heard about Spark"),
      (0.0, "I wish Java could use case classes"),
      (1.0, "Logistic regression models are neat")
    )).toDF("label", "sentence")

    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val wordsData = tokenizer.transform(sentenceData)


    wordsData.show()

    wordsData.foreach{
      x=>println(x)
    }

    val hashingTF = new HashingTF()
      .setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(64)
    val cvModel: CountVectorizerModel = new CountVectorizer()
      .setInputCol("words")
      .setOutputCol("features")
      .fit(wordsData)

    val cvval=cvModel.transform(wordsData)
    cvval.foreach(println(_))



    val featurizedData = hashingTF.transform(wordsData)

    featurizedData.show()


    println("data ")
    featurizedData.foreach{
      x=>println(x)
    }
    // alternatively, CountVectorizer can also be used to get term frequency vectors

    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)

    val rescaledData = idfModel.transform(featurizedData)
    rescaledData.foreach(println(_))
    // $example off$

    spark.stop()

  }

}
