package org.optaplanner.examples.cneat.operators;

import org.optaplanner.examples.cneat.structure.Connection;
import org.optaplanner.examples.cneat.structure.Network;

import java.util.*;


import static org.optaplanner.examples.cneat.operators.Update.updateNetwork;

public class Crossover {


    public static Network crossover(Network parent_1, Network parent_2) {

        // 神经网络的交叉是两串链接list的交叉
        List<Connection> connectionList_1 = parent_1.getConnectionList();
        List<Connection> connectionList_2 = parent_2.getConnectionList();

        long idMax_1 = connectionList_1.get(connectionList_1.size() - 1).getId();
        long idMax_2 = connectionList_2.get(connectionList_2.size() - 1).getId();

        long idMax = idMax_1 > idMax_2 ? idMax_1 : idMax_2;       // 两个父代的最大id值
        List<Connection> offspringList = new ArrayList<>();

        Iterator iterator_1 = connectionList_1.iterator();
        Iterator iterator_2 = connectionList_2.iterator();

        Connection connection_1 = (Connection) iterator_1.next(); // 先取第一个, 同时指针下移
        Connection connection_2 = (Connection) iterator_2.next();
        long id_1;
        long id_2;

        // 最大id值从1开始遍历
        for(int i = 1 ; i <= idMax ; i ++) {
            id_1 = connection_1.getId();
            id_2 = connection_2.getId();

            // 往子代中加
            Connection offspring;
            if(i == id_1 && i == id_2) {
                offspring = Math.random() > 0.5 ? connection_1 : connection_2;    // 两个id对上了, 随机选一个
                if(iterator_1.hasNext()) {
                    connection_1 = (Connection) iterator_1.next();
                }
                if(iterator_2.hasNext()) {
                    connection_2 = (Connection) iterator_2.next();
                }
            } else if (i == id_1) {
                offspring = connection_1;
                if(iterator_1.hasNext()) {
                    connection_1 = (Connection) iterator_1.next();
                }
            } else if (i == id_2) {
                offspring = connection_2;
                if(iterator_2.hasNext()) {
                    connection_2 = (Connection) iterator_2.next();
                }
            } else {
                continue;
            }
            offspringList.add(offspring);
        }
        Network network = new Network();                  // 子代network
        network.setConnectionList(offspringList);         // 将新生成的connectionList赋给子代
        network.setNodeList(parent_1.getNodeList());      // (先)将父代的nodeList赋给子代(含sensor)
        updateNetwork(network);                           // (再)更新子代

        /*System.out.println(offspringList);
        System.out.println(nodeList);*/
        /* function ends */
        return network;
    }

/* class ends */
}
