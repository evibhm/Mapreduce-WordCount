# Mapreduce-WordCount

本项目是 MapReduce 的经典项目 WordCount 的练习，连接远程的 Hadoop 集群/伪集群，完全使用 JAVA API 来操作 HDFS。

第一次写 JAVA，做下笔记。

## Maven

Maven 是一个项目管理工具，可以对 Java 项目进行构建、依赖管理。

## `pom.xml`

Maven 的配置文件，包括项目信息，Java 版本，依赖等。

## `log4j.properties`

log4j 的配置文件，在 idea 中需要进行配置，否则会报错。

## 使用方法

### 远程主机配置Hadoop

省略...

### 本地主机配置

1. 修改hosts，使 `master` 指向远程主机ip地址；
2. 设置ssh密钥登录远程主机，方便后续操作；

### 上传输入文件

本地主机运行 Upload 类

### 执行 Mapreduce 任务

1. 本地主机使用 maven 编译 jar 包；
2. 本地主机使用 scp 上传编译的 jar 包： `scp target/WordCount-0.1.jar hadoop:/home/hadoop/`；
3. 远程主机执行 Mapreduce 任务: `hadoop jar WordCount-0.1.jar Main`;
4. 在网页中查看任务情况 [ResourceManager](http://master:8088);

> Tips: 输入输出路径已经在 Main 中写死了，不需要在命令行中传递参数。

### 下载输出文件

本地主机运行 Output 类
