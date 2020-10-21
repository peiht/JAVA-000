package org.spring.server.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author hitopei
 *
 * 自定义classLoader
 */
public class ClassLoaderTest extends ClassLoader{

    @Override
    public Class<?> findClass(String name) {
        byte[] byteArray = null;
        try {
            byteArray = getBytes(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass("Hello", byteArray, 0, byteArray.length);
    }

    private byte[] getBytes(String name) throws IOException {
        FileInputStream inputStream = new FileInputStream(name);
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        outputStream.close();
        bytes = outputStream.toByteArray();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(255 - bytes[i]);
        }
        return bytes;
    }

    public static void main(String[] args) throws Exception{
        ClassLoaderTest classLoaderTest = new ClassLoaderTest();
        File temp = new File("");
        System.out.println(temp.getCanonicalPath());
        String path = temp.getCanonicalPath() + "/spring-server/src/test/java/org/spring/server/classloader/Hello.xlass";
        Class clazz = classLoaderTest.findClass(path);
        Object o = clazz.newInstance();
        Method method = clazz.getMethod("hello");
        method.invoke(o);
    }
}
