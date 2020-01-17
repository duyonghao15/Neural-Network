package org.optaplanner.examples.cneat.operators;

import org.optaplanner.examples.cneat.structure.Connection;
import org.optaplanner.examples.cneat.structure.Network;
import org.optaplanner.examples.cneat.structure.Node;
import org.optaplanner.examples.cneat.tools.IdComparatorDown;
import org.optaplanner.examples.cneat.tools.IdComparatorUp;

import java.util.*;

public class Update {

    // ************ 根据connectionList更新network ************
    public static void updateNetwork(Network network) {
        List<Connection> connectionList = network.getConnectionList();
        List<Node> totalNodeList = network.getNodeList();

        // 1. 记录input 和 output nodes
        Set<Node> nodeSet = new HashSet<>();
        for(Node node : totalNodeList) {
            node.getInConnectionSet().clear();                       // 先清空In/Out connectionList
            node.getOutConnectionSet().clear();
            if(node.getLayerType().equals("sensor")) {
                nodeSet.add(node);                                   // 起点和终点必须存在
            } else {
                break;                                               // 遍历到hidden了, 即可跳出
            }
        }

        // 2. 把多余的connection删除
        Collections.sort(connectionList, new IdComparatorDown());    // id从大到小排序(方便删除)
        for(int i = connectionList.size() - 1 ; i >= 0 ; i --) {
            if(i == connectionList.size()) {                         // 不知道这里为啥会报错(相等)
                continue;
            }
            Connection connection = connectionList.get(i);
            double weight = connection.getWeight();
            long startNode = connection.getStartNode().getId();
            long endNode = connection.getEndNode().getId();

            for(int j = i - 1 ; j >= 0 ; j --) {
                // 起点和终点分别都相同
                if(startNode == connectionList.get(j).getStartNode().getId() && endNode == connectionList.get(j).getEndNode().getId()) {
                    weight += connectionList.get(j).getWeight();     // 相同的weight累加
                    connectionList.remove(j);                        // 然后把相同的删除
                }
            }
            connection.setWeight(weight);                            // 更新weight
        }
        Collections.sort(connectionList, new IdComparatorUp());      // 再id从小到大排序(回)


        // 3. 根据connection生成node
        for(Connection connection : connectionList) {
            Node startNode = connection.getStartNode();
            Node endNode = connection.getEndNode();
            if(startNode.getId().equals(endNode.getId())) {
                System.out.println("error: 存在自闭环的节点: " + startNode);
            }
            // 判断起点终点是否存在
            int existStartNode = 0;
            int existEndNode = 0;
            for(Node node : nodeSet) {
                if(startNode.getId().equals(node.getId())) {
                    node.getOutConnectionSet().add(connection);      // 该startNode指出了该connection
                    existStartNode = 1;                              // 该startNode已存在
                }
                if(endNode.getId().equals(node.getId())) {
                    node.getInConnectionSet().add(connection);       // 该connection指向该endNode
                    existEndNode = 1;                                // 该endNode已存在
                }
            }
            // 不存在该startNode, 新加入list
            if(existStartNode == 0) {
                startNode.getInConnectionSet().clear();
                startNode.getOutConnectionSet().clear();
                startNode.getOutConnectionSet().add(connection);     // 该startNode指出了该connection
                nodeSet.add(startNode);
            }
            // 不存在该endNode, 也新加入list
            if(existEndNode == 0) {
                endNode.getInConnectionSet().clear();
                endNode.getOutConnectionSet().clear();
                endNode.getInConnectionSet().add(connection);        // 该connection指向该endNode
                nodeSet.add(endNode);
            }
        }
        List<Node> nodeList = new ArrayList<>(nodeSet);
        Collections.sort(nodeList, new IdComparatorUp());            // node id从小到大排序
        network.setNodeList(nodeList);
        /* function ends */
    }



/* class ends */
}
