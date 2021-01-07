创建六个docker 容器 三个作为master 三个作为slave

1、cluster的主要配置信息如下， 保存为集群节点的配置文件，作为docker挂载的配置文件。分别创建六个配置文件

```bash
# 端口
port 7000
# 保护模式，开启之后会需要配置绑定的ip或者设置访问密码，现在设置关闭
protected-mode no
# 开启集群模式
cluster-enabled yes
# 集群节点文件
cluster-config-file nodes.conf
# 节点链接超时的时间
cluster-node-timeout 5000
# 集群节点的ip，这个是docker gateway的地址，配置的其他节点都可以通过这个ip访问，我就配置的这个
cluster-announce-ip 172.17.0.1
# 集群节点映射的端口
cluster-announce-port 7000
# 集群节点总线的端口，不能和映射的端口一样，不然启动集群的时候会一直等待。。。
cluster-announce-bus-port 17000
# 开启aof
appendonly yes
```

2、然后创建六个容器分别作为三个master和三个slave

```bash
hitopei@localhost cluster % for port in `seq 7000 7005`; do \
docker run -d -ti -p ${port}:${port} -p 1${port}:1${port} \
-v /Users/hitopei/redis/cluster/${port}/conf/redis.conf:/usr/local/etc/redis/redis.conf \
-v /Users/hitopei/redis/cluster/${port}/data:/data \
--restart always --name redis-${port} \
--sysctl net.core.somaxconn=1024 redis redis-server /usr/local/etc/redis/redis.conf; \
done
a3cf355af104b4275544e4328a6b9610d8513c984e15f67b14d45a5f812f9cc2
fb57db16fa3f8c575ef6e461fc0e5263c85d6f69559dca860c4b7d1376da15f2
1797966238a38947773405b5524ab0df4fcf7b1fd66cc17e4c73d8f22a66cab1
e21f3c5a83c0ce97a5c1584ef613348aaf64d0836661161d544c049b3e750038
cdccb1ba864195cd15e0ed952faeca70900eb534d004d902cc80bdab410c83ba
062db972720c45093750127fd288a2076cc00e6aed1427b067febc429df829f1
```

3、创建完成之后开始用redis-cli登上其中一个redis示例进行集群的配置，具体操作如下:

```bash
root@b5ab6905082a:/data# redis-cli --cluster create 172.17.0.1:7000 172.17.0.1:7001 172.17.0.1:7002 172.17.0.1:7003 172.17.0.1:7004 172.17.0.1:7005 --cluster-replicas 1
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 172.17.0.1:7004 to 172.17.0.1:7000
Adding replica 172.17.0.1:7005 to 172.17.0.1:7001
Adding replica 172.17.0.1:7003 to 172.17.0.1:7002
>>> Trying to optimize slaves allocation for anti-affinity
[WARNING] Some slaves are in the same host as their master
M: 8812947bcd35da399c203cde065f1e9e2e2c6c20 172.17.0.1:7000
   slots:[0-5460] (5461 slots) master
M: f7eececbd60686ca8b9e65cfde986853e0387aeb 172.17.0.1:7001
   slots:[5461-10922] (5462 slots) master
M: c35fa575d1c7e785d124f107ee1e9d748444b7f2 172.17.0.1:7002
   slots:[10923-16383] (5461 slots) master
S: 5dc90f80049b3fa09a4e983aa3a553c6d21a417a 172.17.0.1:7003
   replicates 8812947bcd35da399c203cde065f1e9e2e2c6c20
S: b68d89ef1ac84c65ee04c712cd5e8eb1540872fa 172.17.0.1:7004
   replicates f7eececbd60686ca8b9e65cfde986853e0387aeb
S: 0bb0c2b3f94c121e70734706221e53d6b0716c4d 172.17.0.1:7005
   replicates c35fa575d1c7e785d124f107ee1e9d748444b7f2
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
.
>>> Performing Cluster Check (using node 172.17.0.1:7000)
M: 8812947bcd35da399c203cde065f1e9e2e2c6c20 172.17.0.1:7000
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
M: f7eececbd60686ca8b9e65cfde986853e0387aeb 172.17.0.1:7001
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
M: c35fa575d1c7e785d124f107ee1e9d748444b7f2 172.17.0.1:7002
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: 5dc90f80049b3fa09a4e983aa3a553c6d21a417a 172.17.0.1:7003
   slots: (0 slots) slave
   replicates 8812947bcd35da399c203cde065f1e9e2e2c6c20
S: b68d89ef1ac84c65ee04c712cd5e8eb1540872fa 172.17.0.1:7004
   slots: (0 slots) slave
   replicates f7eececbd60686ca8b9e65cfde986853e0387aeb
S: 0bb0c2b3f94c121e70734706221e53d6b0716c4d 172.17.0.1:7005
   slots: (0 slots) slave
   replicates c35fa575d1c7e785d124f107ee1e9d748444b7f2
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

4.创建完成之后开始验证

登录其中一个redis实例， 输入命令 cluster nodes查看集群节点信息

```bash
172.17.0.1:7000> cluster nodes
f7eececbd60686ca8b9e65cfde986853e0387aeb 172.17.0.1:7001@17001 master - 0 1609989821584 2 connected 5461-10922
c35fa575d1c7e785d124f107ee1e9d748444b7f2 172.17.0.1:7002@17002 master - 0 1609989823100 3 connected 10923-16383
8812947bcd35da399c203cde065f1e9e2e2c6c20 172.17.0.1:7000@17000 myself,master - 0 1609989821000 1 connected 0-5460
5dc90f80049b3fa09a4e983aa3a553c6d21a417a 172.17.0.1:7003@17003 slave 8812947bcd35da399c203cde065f1e9e2e2c6c20 0 1609989822088 1 connected
b68d89ef1ac84c65ee04c712cd5e8eb1540872fa 172.17.0.1:7004@17004 slave f7eececbd60686ca8b9e65cfde986853e0387aeb 0 1609989821585 2 connected
0bb0c2b3f94c121e70734706221e53d6b0716c4d 172.17.0.1:7005@17005 slave c35fa575d1c7e785d124f107ee1e9d748444b7f2 0 1609989822596 3 connected
```

端口7000-7002是master， 7003-7005是slave