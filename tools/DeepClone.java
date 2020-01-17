package org.optaplanner.examples.cneat.tools;

import java.io.*;

public class DeepClone {

    //  ************ 深克隆对象函数 **************
    public static Object deepClone(Object object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            // 将bos作为收集字节的数组中介
            oos = new ObjectOutputStream(bos);
            // 将传入参数object类写入bos中
            oos.writeObject(object);
            // 将读取到数据传入ObjectInpuStream
            ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // JDK1.7后引入 可以同时用|优化代码
            e.printStackTrace();
            return null;
        } finally {
            try {
                bos.close();
                oos.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

/* class ends */
}
