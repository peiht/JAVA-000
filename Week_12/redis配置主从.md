我是使用docker进行配置的一主两从的一个主从架构

1、准备好三个redis的容器

redis-test作为master主库

redis-slave1、redis-slave2作为两个从库

```bash
hitopei@localhost ~ % docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
cbe8c0a53042        redis               "docker-entrypoint.s…"   16 minutes ago      Up 16 minutes       0.0.0.0:6381->6379/tcp   redis-slave2
d1101db70e24        redis               "docker-entrypoint.s…"   17 minutes ago      Up 17 minutes       0.0.0.0:6380->6379/tcp   redis-slave1
5a9f3b1ebdc9        redis               "docker-entrypoint.s…"   6 days ago          Up 43 hours         0.0.0.0:6379->6379/tcp   redis-test
```

2、查看master的ip和端口信息（我只截取网络部分）

```bash
hitopei@bogon ~ % docker inspect 5a9f3b1ebdc9

"NetworkSettings": {
            "Bridge": "",
            "SandboxID": "3650a7c73ccf887179443246162e1ba7ac6063691137ef43e5e494948ec1aedb",
            "HairpinMode": false,
            "LinkLocalIPv6Address": "",
            "LinkLocalIPv6PrefixLen": 0,
            "Ports": {
                "6379/tcp": [
                    {
                        "HostIp": "0.0.0.0",
                        "HostPort": "6379"
                    }
                ]
            },
            "SandboxKey": "/var/run/docker/netns/3650a7c73ccf",
            "SecondaryIPAddresses": null,
            "SecondaryIPv6Addresses": null,
            "EndpointID": "403286b7797c1059e0611a8283b23488c44e5cab77b0eb2b7a3ef652882d2fa9",
            "Gateway": "172.17.0.1",
            "GlobalIPv6Address": "",
            "GlobalIPv6PrefixLen": 0,
            "IPAddress": "172.17.0.2",
            "IPPrefixLen": 16,
            "IPv6Gateway": "",
```

ip是172.17.0.1 端口是6379。

3、运行其中一个slave的镜像

```bash
hitopei@localhost ~ % docker exec -it cbe8c0a53042 redis-cli
```

查看他的角色信息。目前看默认还是master的状态，没有从库的信息。

```bash
127.0.0.1:6379> info replication
# Replication
role:master
connected_slaves:0
master_replid:558e6f07e228768f6eadc232cd58c585aecebd68
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:0
second_repl_offset:-1
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```

设置当前库为master的主库

```bash
127.0.0.1:6379> SLAVEOF 172.17.0.2 6379
OK
```

然后再次查看当前库的角色信息

```bash
127.0.0.1:6379> info replication
# Replication
role:slave
master_host:172.17.0.2
master_port:6379
master_link_status:up
master_last_io_seconds_ago:6
master_sync_in_progress:0
slave_repl_offset:14
slave_priority:100
slave_read_only:1
connected_slaves:0
master_replid:ba15bbe85d160cc9caff3b0df01235d177cfc27b
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:14
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:14
```

已经显示当前角色是slave，谈话还输出了主库的一些信息。

另外一个salve库也做了同样的配置。

最后在验证一下：

```bash
# 在主库中设置一个key master value 是1
127.0.0.1:6379[1]> set "master" 1
OK

# 在两个存库中查询结果为：
127.0.0.1:6379[1]> keys *
1) "hello"
2) "master"
127.0.0.1:6379[1]> get master
"1"

127.0.0.1:6379[1]> keys *
1) "hello"
2) "master"
127.0.0.1:6379[1]> master
(error) ERR unknown command `master`, with args beginning with:
127.0.0.1:6379[1]> get master
"1"

```

验证完成，主库的信息可以同步到从库。