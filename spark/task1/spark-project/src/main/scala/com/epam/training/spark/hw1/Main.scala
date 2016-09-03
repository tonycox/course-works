package com.epam.training.spark.hw1

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Anton_Solovev 
  * @since 9/1/2016.
  */
object Main {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("training1")
    val sc = new SparkContext(conf)
    sc.addJar("http://central.maven.org/maven2/eu/bitwalker/UserAgentUtils/1.20/UserAgentUtils-1.20.jar")
    val job = new SparkStatistic(sc)
    if (args.length != 2) {
      Console.err.println(" Usage: Main < input path > < output path >")
      System.exit(-1)
    }
    val rdd = sc.textFile(args(0))
    val result = job.compute(rdd)
    val out = result.sortBy[Long](f => f._3, ascending = false, 1)
    out.saveAsTextFile(args(1))
    Console.print(out.take(5).mkString("top 5 is:", ", ", ""))
    sc.stop()
  }

}
