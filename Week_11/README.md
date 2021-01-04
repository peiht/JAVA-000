4.（必做）基于 Redis 封装分布式数据操作：
在 Java 中实现一个简单的分布式锁；
在com.javaAdvance.redis.lock.core中，使用redisTemplate实现的。

在 Java 中实现一个分布式计数器，模拟减库存。
在目录com.javaAdvance.redis.lock.inventory中，实现了初始化库存和减库存.

5.（必做）基于 Redis 的 PubSub 实现订单异步处理
在目录com.javaAdvance.redis.lock.listener中。
OrderPublish 是消息发送者， RedisListener是消息消费者，监听着一个channel。
监听的配置信息在RedisPubSubConfig配置中。
ResultBean是模拟的消息体的结构.