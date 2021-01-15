我下载的版本是kafka_2.12-2.7.0

1、新建三个节点的配置文件，直接拷贝servers.properties文件，分别修改其中的配置项

```bash
#broker的id，每个节点的broker必须不一样
broker.id=1
#列举三个节点的地址
broker.list=localhost:9000,localhost:9001,localhost:9002
#设置服务的端口
listeners=PLAINTEXT://localhost:9000
#设置日志文件地址
log.dirs=/tmp/kafka-logs1
```

我设置的是9000到9002三个端口的服务

2、开启服务

首先启动zookeeper服务

```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```

然后分别用三个配置文件启动kafka的server服务

```bash
bin/kafka-server-start.sh config/server9000.properties
bin/kafka-server-start.sh config/server9001.properties
bin/kafka-server-start.sh config/server9002.properties
```

创建topic

```bash
bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic test-cluster --partition 3 --replication-factor 2
```

创建完成之后，用zooInspector查看zookeeper上的注册信息，如下：

![image-20210114101942564](https://tva1.sinaimg.cn/large/008eGmZEly1gmn0cwn2izj30bw0la3zf.jpg)

可以看到创建的三个broker和topic的信息。

3、然后做了一下基准测试，结果如下：

```bash
hitopei@localhost kafka_2.12-2.7.0 % bin/kafka-producer-perf-test.sh --topic test-cluster --num-records 100000 --record-size 1000 --throughput 2000 --producer-props bootstrap.servers=localhost:9000
9998 records sent, 1999.6 records/sec (1.91 MB/sec), 28.5 ms avg latency, 432.0 ms max latency.
10008 records sent, 2001.2 records/sec (1.91 MB/sec), 0.6 ms avg latency, 14.0 ms max latency.
10004 records sent, 2000.4 records/sec (1.91 MB/sec), 0.6 ms avg latency, 32.0 ms max latency.
10002 records sent, 2000.4 records/sec (1.91 MB/sec), 0.4 ms avg latency, 7.0 ms max latency.
10002 records sent, 2000.0 records/sec (1.91 MB/sec), 0.4 ms avg latency, 13.0 ms max latency.
10002 records sent, 2000.4 records/sec (1.91 MB/sec), 0.3 ms avg latency, 5.0 ms max latency.
10002 records sent, 2000.4 records/sec (1.91 MB/sec), 0.3 ms avg latency, 6.0 ms max latency.
9998 records sent, 1999.6 records/sec (1.91 MB/sec), 0.4 ms avg latency, 10.0 ms max latency.
10000 records sent, 2000.0 records/sec (1.91 MB/sec), 0.4 ms avg latency, 26.0 ms max latency.
100000 records sent, 1999.600080 records/sec (1.91 MB/sec), 3.22 ms avg latency, 432.00 ms max latency, 0 ms 50th, 1 ms 95th, 108 ms 99th, 390 ms 99.9th.
```

