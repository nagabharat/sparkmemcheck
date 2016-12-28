package com.synycs

import java.text.SimpleDateFormat
import java.time._
import java.time.format.DateTimeFormatter
import org.apache.spark.sql.SparkSession

/**
  * Created by synycs on 28/12/16.
  */
object DateTmeTest {


  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName("TfIdfExample").master("local")
      .getOrCreate()
    val df=spark.createDataFrame(Seq((1, "2015-11-02T04:43:32"),(2, "2016-05-01T04:43:32"))).toDF("id", "ts")

    val timeroles=df.rdd.map{
      x=>val time=x.getAs[String]("ts")
        val id=x.getAs[Int]("id")
        val newDate=LocalDate.parse(time,DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        (id.toString,newDate.getYear.toString,newDate.getMonthValue.toString,newDate.getDayOfMonth.toString)
    }
    val timedf= spark.createDataFrame(timeroles).toDF("id","Y","M","D")

    val join=timedf.join(df,"id")

    join.show()



  }

}
