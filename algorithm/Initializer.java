package org.optaplanner.examples.cneat.algorithm;

import org.optaplanner.examples.cneat.structure.Connection;
import org.optaplanner.examples.cneat.structure.Network;
import org.optaplanner.examples.cneat.structure.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Initializer {

    // ************ 1. 初始化网络 ************
    public static List<Network> initializer(int popSize, List<double[]> inputsList, List<double[]> outputsList) {

        int inputNum = inputsList.get(0).length;         // 网络input node数
        int outputNum = outputsList.get(0).length;       // 网络output node数
        if(inputsList.size() != outputsList.size() || inputNum * outputNum == 0) {
            System.out.println("error: 样本输入与输出不匹配");
        }


        List<Network> networkList = new ArrayList<>();
        for(int i = 1; i <= popSize ; i ++) {
            Network network = new Network();
            network.setId((long) i);

            List<Node> nodeList = new ArrayList<>();
            List<Node> inputList = new ArrayList<>();
            List<Node> outputList = new ArrayList<>();
            List<Connection> connectionList = new ArrayList<>();

            // a. 新建输入node
            for(int j = 1; j <= inputNum ; j ++) {
                Node node = newNode(j, "sensor", "input");
                inputList.add(node);
                nodeList.add(node);
            }

            // b. 新建输出node
            for(int j = 1; j <= outputNum ; j ++) {
                Node node = newNode(j + inputNum, "sensor", "output");
                outputList.add(node);
                nodeList.add(node);
            }

            long connectionId = 1L;
            // c. 新建链接(全链接)
            for(Node inputNode : inputList) {
                for(Node outputNode : outputList) {
                    Connection connection = newConnection(connectionId, inputNode, outputNode);
                    connectionList.add(connection);
                    connectionId ++;
                }
            }
            network.setConnectionList(connectionList);
            network.setNodeList(nodeList);
            networkList.add(network);
        }
        /* function ends */
        return networkList;
    }


    // ************ 2. 新建node ************
    public static Node newNode(long id, String layerType, String inOutPut) {
        List<String> activationFunctionList = new ArrayList<>();
        activationFunctionList.add("log-sigmoid");
        if(!inOutPut.equals("output")) {                 // 0-1分类问题, 故输出的值须在0-1之间
            activationFunctionList.add("tan-sigmoid");
            activationFunctionList.add("relu");
        }

        Node node = new Node();
        node.setId(id);
        node.setLayerType(layerType);
        node.setInOutPut(inOutPut);
        node.setBiasValue(0);
        node.setMeanValue(0);
        node.setVariance(1);
        node.setActivationFunctionList(activationFunctionList);
        node.setInConnectionSet(new HashSet<>());
        node.setOutConnectionSet(new HashSet<>());
        String activeFunction = activationFunctionList.get((int) (Math.random() * activationFunctionList.size()));
        node.setActivationFunction(activeFunction);

        return node;
    }


    // ************ 3. 新建connection ************
    public static Connection newConnection(long id, Node startNode, Node endNode) {
        Connection connection = new Connection();
        connection.setId(id);
        connection.setStartNode(startNode);
        connection.setEndNode(endNode);
        connection.setActive(true);
        connection.setWeight(1);
        connection.setMeanValue(0);
        connection.setVariance(1);
        return connection;
    }


/* class ends */
}
