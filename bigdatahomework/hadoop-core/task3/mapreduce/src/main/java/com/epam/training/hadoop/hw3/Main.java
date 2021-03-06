package com.epam.training.hadoop.hw3;

import com.epam.training.hadoop.hw3.mapreduce.CombineStatistic;
import com.epam.training.hadoop.hw3.mapreduce.MapStatistic;
import com.epam.training.hadoop.hw3.mapreduce.ReduceStatistic;
import com.epam.training.hadoop.hw3.mapreduce.utils.WritableInfo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author Anton_Solovev
 * @since 8/12/2016.
 */
public class Main extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf());
        job.setJobName("training");

        job.setJarByClass(Main.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(WritableInfo.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(WritableInfo.class);

        job.setMapperClass(MapStatistic.class);
        job.setReducerClass(ReduceStatistic.class);
        job.setCombinerClass(CombineStatistic.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        FileOutputFormat.setCompressOutput(job, true);
        FileOutputFormat.setOutputCompressorClass(job, SnappyCodec.class);
        return job.waitForCompletion(true) ? 1 : 0;
    }

    private static final String baseUrl = "hdfs://sandbox.hortonworks.com";

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println(" Usage: Main < input path > < output path >");
            System.exit(-1);
        }
        Configuration conf = new Configuration();
        conf.set("fs.default.name", baseUrl);
        conf.set("mapreduce.textoutputformat.separatorText", ",");
        int result = ToolRunner.run(conf, new Main(), args);
        System.exit(result);
    }

}
