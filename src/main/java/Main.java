import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Main {
    // 需要在 hosts 里设置NameNode(master)的地址
    public static String nameNodeUrl = "hdfs://master:9000";
    public static Configuration conf;
    public static FileSystem fs;

    public static void main(String[] args) throws Exception {
        // 配置Hadoop
        conf = new Configuration();
        conf.set("fs.defaultFS", nameNodeUrl);
        conf.set("dfs.client.use.datanode.hostname","true");
        conf.set("dfs.replication", "1");
        fs = FileSystem.get(new URI(nameNodeUrl), conf, "hadoop");
        System.out.println(fs);

        // 上传输入文件
        Path uploadPath = new Path(nameNodeUrl, "/user/hadoop/wordcount/input");
        if (!fs.exists(uploadPath)) {
            fs.mkdirs(uploadPath);
        }
        fs.copyFromLocalFile(new Path("./input/input1.txt"),uploadPath);
        fs.copyFromLocalFile(new Path("./input/input2.txt"),uploadPath);
        System.out.println("Upload File to HDFS");

        // 清空输出目录
        Path outputPath = new Path(nameNodeUrl, "/user/hadoop/wordcount/output");
        if (fs.exists(outputPath)) {
            fs.delete(outputPath, true);
        }
        fs.mkdirs(outputPath);

        // 列出目录
        FileStatus[] listStatus = fs.listStatus(new Path("/user/hadoop/wordcount/input"));
        for (FileStatus fileStatus : listStatus) {
            System.out.println(fileStatus.getPermission() + " " + fileStatus.getOwner() + " " + fileStatus.getPath());
        }
    }


}
