package io.kimmking.test;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;

public class TestJSON {

    public static void main(String[] args) {
        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass("io.kimmking.rpcfx.demo.api.UserService");
        request.setMethod("findById");
        Object[] params = new Object[1];
        params[0] = 1992129;
        request.setParams(params);
        System.out.println(JSON.toJSONString(request));
    }
}
