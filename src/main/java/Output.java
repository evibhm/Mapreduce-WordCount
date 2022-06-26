import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.File;
import java.io.IOException;

public class Output {
    public static void main(String[] args) throws IOException {
        // 加载配置文件
        // 注意设置 HADOOP_USER_NAME 变量
        Configuration conf = new Configuration(true);
        conf.addResource("core-site.xml");
        conf.addResource("hdfs-site.xml");
        conf.addResource("mapred-site.xml");
        conf.addResource("yarn-site.xml");
        FileSystem fs = FileSystem.get(conf);
        Path outputPath = new Path("/user/hadoop/wordcount/output");
        deletaAllFiles(new File("output"));
        fs.copyToLocalFile(outputPath, new Path("./output"));
        System.out.println("下载输出文件成功");
    }

    /*
    递归删除路径或文件
     */
    private static void deletaAllFiles(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] fileLists = file.listFiles();
                assert fileLists != null;
                for (File f: fileLists) {
                    deletaAllFiles(f);
                }
                if (file.delete()) {
                    System.out.printf("删除目录 %s 成功\n", file.getAbsolutePath());
                } else {
                    System.out.printf("删除目录 %s 失败\n", file.getAbsolutePath());
                }
            } else {
                if (file.delete()) {
                    System.out.printf("删除文件 %s 成功\n", file.getAbsolutePath());
                } else {
                    System.out.printf("删除文件 %s 失败\n", file.getAbsolutePath());
                }
            }
        } else {
            System.out.printf("文件 %s 不存在\n", file.getAbsolutePath());
        }
    }
}
