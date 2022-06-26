import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Main {
    public static void main(String[] args) {
        // 加载配置文件
        // 注意设置 HADOOP_USER_NAME 变量
        Configuration conf = new Configuration(true);
        conf.addResource("core-site.xml");
        conf.addResource("hdfs-site.xml");
        conf.addResource("mapred-site.xml");
        conf.addResource("yarn-site.xml");
        // 设置要运行的jar
        conf.set("mapred.jar", "target/WordCount-0.1.jar");
        // 设置跨平台
        conf.set("mapreduce.app-submission.cross-platform", "true");
        System.out.println("配置Hadoop完成...");

        try {
            // 创建一个任务
            Job job = Job.getInstance(conf);
            job.setJarByClass(Main.class);
            job.setJobName("WordCount");

            // 设置输入路径
            FileInputFormat.addInputPath(job, new Path("/user/hadoop/wordcount/input/input1.txt"));
            FileInputFormat.addInputPath(job, new Path("/user/hadoop/wordcount/input/input2.txt"));

            // 设置输出路径
            Path outputPath = new Path("/user/hadoop/wordcount/output");
            FileSystem fs = FileSystem.get(conf);
            if (fs.exists(outputPath)) {
                fs.delete(outputPath, true);
            }
            FileOutputFormat.setOutputPath(job, outputPath);

            // 设置类
            job.setMapperClass(WCMapper.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            job.setReducerClass(WCReducer.class);

            // 等待结束
            job.waitForCompletion(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
