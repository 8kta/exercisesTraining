package MapSideJoins;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.net.URI;


public class UserActivityDriver extends Configured implements Tool {


    public static void main(String[] args) {
        try {
            int status = ToolRunner.run(new UserActivityDriver(), args);
            System.exit(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.printf("Usage: %s [generic options] <input1> <output>\n", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }
        Configuration conf = new Configuration();
        Job job = Job.getInstance();
        job.setJarByClass(getClass());
        job.setJobName("MapSideJoin Example");

        // input path
        FileInputFormat.addInputPath(job, new Path(args[0]));

        // output path
        //FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setMapperClass(UserActivityMapper.class);
        //im not creating a reducer
        job.setNumReduceTasks(0);
        //job.setReducerClass(UserActivityReducer.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(UserActivityVO.class);
        job.addCacheFile(new URI("/home/alonso/IdeaProjects/test/src/main/resources/currency.log"));
        //job.setOutputKeyClass(UserActivityVO.class);
        job.setOutputValueClass(NullWritable.class);
        Path outputPath = new Path(args[1]);
        FileOutputFormat.setOutputPath(job, outputPath);
        outputPath.getFileSystem(conf).delete(outputPath);
        return job.waitForCompletion(true) ? 0 : 1;

    }


}
