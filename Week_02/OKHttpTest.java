package com.javaAdvace.netty.nio.homework;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * 作业 okHttp请求地址
 *
 * @author Administrator
 */
public class OKHttpTest {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * 要请求的地址
     */
    private final String address = "http://localhost:8801";
    public void visit(){
        Request request = new Request.Builder()
                .url(address).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("响应失败");
                }

                Headers headers = response.headers();
                //输出hello, nio
                System.out.println(response.body().string());
            }
        });
    }

    public static void main(String[] args) {
        OKHttpTest test = new OKHttpTest();
        test.visit();
    }
}
