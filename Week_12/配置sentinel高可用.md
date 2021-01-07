1、搭建sentinel服务

我还是选用docker作为redis-sentinel的容器，首先初始化三个服务作为sentinel的服务：

```bash
docker run -it --name sentinel-2 -p 26381:26379 -v /Users/hitopei/DownLoads/sentinel.conf:/usr/local/etc/redis/sentinel.conf -d redis /bin/bash
```

sentinel的主要配置如下：

```bash
#检测master的ip端口等信息
sentinel monitor mymaster 172.17.0.3 6379 2

#master down掉之后30秒开始选举新的master
sentinel down-after-milliseconds mymaster 30000

#sentinel的日志位置
logfile "/var/log/redis/sentinel.log"

```

在三个docker服务上分别部署、启动

```bash
docker exec -it sentinel-1 bash

# 运行docker上的sentinel服务
root@65291a775ed1:/data# redis-sentinel /usr/local/etc/redis/sentinel.conf
```

查看日志信息

![](/Users/hitopei/Documents/GitHub/JAVA-000/Week_12/sentinel输出信息.png)

可以看到已经检测到了master 172.17.0.3和 两个slave 172.17.0.4和172.17.0.5。

后面可以看到检测到了另外两个sentinel的服务。说明sentinel已经搭建完成了。

2、验证sentinel服务

切换到redis master的服务，把他down掉

```bash
127.0.0.1:6379[1]> info replication
# Replication
role:master
connected_slaves:2
slave0:ip=172.17.0.4,port=6379,state=online,offset=356690,lag=0
slave1:ip=172.17.0.5,port=6379,state=online,offset=356690,lag=0
master_replid:5044245705a19dd7d217f29cb0a1a6182bc5cd17
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:356690
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:356690
127.0.0.1:6379[1]> shutdown
not connected> %
```

查看slave的状态已经是down的状态

```
127.0.0.1:6379> info replication
# Replication
role:slave
master_host:172.17.0.3
master_port:6379
master_link_status:down
master_last_io_seconds_ago:-1
master_sync_in_progress:0
slave_repl_offset:364292
master_link_down_since_seconds:11
slave_priority:100
slave_read_only:1
connected_slaves:0
master_replid:5044245705a19dd7d217f29cb0a1a6182bc5cd17
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:364292
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:364292
```

等大概30s之后，查看sentinel的日志，发现检测到主库down掉之后，开始选举新的master，新的master是172.17.0.4这个服务。

![](/Users/hitopei/Documents/GitHub/JAVA-000/Week_12/sentinel选举日志信息.png)

然后登上172.17.0.4 这个服务查看备份信息，当前库已经是master了。验证完毕

```
127.0.0.1:6379> info replication
# Replication
role:master
connected_slaves:1
slave0:ip=172.17.0.5,port=6379,state=online,offset=372727,lag=1
master_replid:0f05ebc5102fc8c53324066410d9d78d6327f69c
master_replid2:5044245705a19dd7d217f29cb0a1a6182bc5cd17
master_repl_offset:373132
second_repl_offset:364293
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:373132
```

