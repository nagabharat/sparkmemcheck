name := "proj"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.0.2"

libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.0.2"

libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "2.0.2"

libraryDependencies += "org.apache.tika" % "tika-core" % "1.14"

libraryDependencies += "org.apache.tika" % "tika-parsers" % "1.14"

libraryDependencies += "com.databricks" % "spark-xml_2.11" % "0.4.1"

libraryDependencies += "com.opencsv" % "opencsv" % "3.8"