package com.javaAdvance.redis.lock.core;


/**
 * @author hitopei
 * create by hitopei on 2020/12/31 2:07 下午
 */
public interface Lock {

    public Boolean tryLock(String key);

    public void unlock(String key);
}
