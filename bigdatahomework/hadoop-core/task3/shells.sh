hadoop fs -rmdir --ignore-fail-on-non-empty /training/access_logs/out

export HADOOP_CLASSPATH=~/anton_dev/hw3-mapreduce-1.0.jar

yarn jar hw3-mapreduce-1.0.jar com.epam.training.hadoop.hw3.Main /training/access_logs/input /training/access_logs/out

hadoop fs -text /training/access_logs/out/part-r-00000.snappy