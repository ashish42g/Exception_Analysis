package exceptionMRJob;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ExceptionDriver extends Configured implements Tool {


    @Override
    public int run(String[] args) throws Exception {

        if (args.length != 4) {
            System.out.println("Usage: " + getClass().getSimpleName() + "<property file> <input dir> <intermediate output dir> <Final output dir>");
            return -1;
        }

        //Configuring first Job
        configureExceptionAnalysisJob(args);

        //Configuring Second Job
        Job countExceptionJob = configureCountExceptionJob(args);

        return countExceptionJob.waitForCompletion(true) ? 0 : 1;

    }

    private Job configureCountExceptionJob(String[] args) throws IOException {
        Job countExceptionJob = Job.getInstance(getConf(), "Exception Count");
        countExceptionJob.setJarByClass(ExceptionDriver.class);

        FileInputFormat.setInputPaths(countExceptionJob, new Path(args[2]));
        FileOutputFormat.setOutputPath(countExceptionJob, new Path(args[3]));
        countExceptionJob.setInputFormatClass(TextInputFormat.class);
        countExceptionJob.setOutputFormatClass(TextOutputFormat.class);

        countExceptionJob.setMapperClass(CountExceptionMapper.class);
        countExceptionJob.setMapOutputKeyClass(Text.class);
        countExceptionJob.setMapOutputValueClass(IntWritable.class);

        countExceptionJob.setReducerClass(ExcepCountReducer.class);
        countExceptionJob.setOutputKeyClass(Text.class);
        countExceptionJob.setOutputValueClass(IntWritable.class);
        return countExceptionJob;
    }

    private void configureExceptionAnalysisJob(String[] args)
            throws IOException, InterruptedException, ClassNotFoundException {

        Job excepAnalysisJob = Job.getInstance(getConf(), "Exception Analysis");
        excepAnalysisJob.setJarByClass(ExceptionDriver.class);

        excepAnalysisJob.addCacheFile(new Path(args[0]).toUri());

        FileInputFormat.setInputPaths(excepAnalysisJob, new Path(args[1]));
        FileOutputFormat.setOutputPath(excepAnalysisJob, new Path(args[2]));
        excepAnalysisJob.setOutputKeyClass(Text.class);
        excepAnalysisJob.setOutputValueClass(Text.class);

        excepAnalysisJob.setMapperClass(ExceptionAnalysisMapper.class);
        excepAnalysisJob.setInputFormatClass(TextInputFormat.class);
        excepAnalysisJob.setOutputFormatClass(TextOutputFormat.class);

        excepAnalysisJob.setMapOutputKeyClass(Text.class);
        excepAnalysisJob.setMapOutputValueClass(Text.class);

        excepAnalysisJob.setNumReduceTasks(0);

        excepAnalysisJob.waitForCompletion(true);
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new Configuration(), new ExceptionDriver(), args));
    }
}