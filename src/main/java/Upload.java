import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class Upload {
    public static void main(String[] args) throws IOException {
        // 加载配置文件
        // 注意设置 HADOOP_USER_NAME 变量
        Configuration conf = new Configuration(true);
        conf.addResource("core-site.xml");
        conf.addResource("hdfs-site.xml");
        conf.addResource("mapred-site.xml");
        conf.addResource("yarn-site.xml");
        FileSystem fs = FileSystem.get(conf);
        Path uploadPath = new Path("/user/hadoop/wordcount/input");
        if (!fs.exists(uploadPath)) {
            fs.mkdirs(uploadPath);
        }
        fs.copyFromLocalFile(new Path("./input/input1.txt"),uploadPath);
        fs.copyFromLocalFile(new Path("./input/input2.txt"),uploadPath);
        System.out.println("上传文件到HDFS成功");
    }
}
