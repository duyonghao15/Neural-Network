package org.optaplanner.examples.cneat;

import org.optaplanner.examples.cneat.structure.Connection;
import org.optaplanner.examples.cneat.structure.Network;
import org.optaplanner.examples.cneat.structure.Node;

import java.util.ArrayList;
import java.util.List;

import static org.optaplanner.examples.cneat.algorithm.Generation.generation;
import static org.optaplanner.examples.cneat.algorithm.Initializer.initializer;
import static org.optaplanner.examples.cneat.algorithm.Fitness.fitness;
import static org.optaplanner.examples.cneat.algorithm.Initializer.newConnection;
import static org.optaplanner.examples.cneat.algorithm.Initializer.newNode;
import static org.optaplanner.examples.cneat.operators.Update.updateNetwork;
import static org.optaplanner.examples.cneat.tools.ReaderSample.normalization;
import static org.optaplanner.examples.cneat.tools.ReaderSample.readSampleInputs;
import static org.optaplanner.examples.cneat.tools.ReaderSample.readSampleOutputs;

public class CneatHelloWorld {

    public static void main(String[] args) throws Exception {

        // 1. 样本(输入和标签)导出
        List<double[]> inputsList = readSampleInputs("C:/Users/Think/Desktop/data.csv");     // createDemoInputsList();
        inputsList = normalization(inputsList);                                                   // 0-1标准化
        List<double[]> outputsList = readSampleOutputs("C:/Users/Think/Desktop/data.csv");   // createDemoOutputsList();

        // 2. 初始化网络
        List<Network> initialPopulation = initializer(50, inputsList, outputsList);

        // 3. 迭代
        List<Network> newPopulation = initialPopulation;
        for(int i = 1 ; i <= 40 ; i ++) {
            System.out.println("第 " + i + " 代:");
            newPopulation = generation(newPopulation, inputsList, outputsList);
        }

        // 4. 结果
        double fitness;
        for(Network network : newPopulation) {
            System.out.println("\n");
//            updateNetwork(network);
            System.out.println(network.getConnectionList());
            System.out.println(network.getNodeList());
            System.out.println(network.getNodeList().get(17) + " " + network.getNodeList().get(17).getInConnectionSet());

            fitness = fitness(network, inputsList, outputsList);
            System.out.println(network.getNodeList().size() + " " + fitness);
//            System.out.println(network.getNodeList());
        }

        // 5. 检验



        /* main function ends */
    }


    public static List<double[]> createDemoInputsList() {
        List<double[]> inputsList = new ArrayList<>();
        inputsList.add(new double[] {1, 1, 1, 1});
        inputsList.add(new double[] {1, 1, 1, 1});
        inputsList.add(new double[] {1, 1, 1, 1});
        inputsList.add(new double[] {1, 1, 1, 1});
        return inputsList;
    }

    public static List<double[]> createDemoOutputsList() {
        List<double[]> outputsList = new ArrayList<>();
        outputsList.add(new double[] {1});
        outputsList.add(new double[] {1});
        outputsList.add(new double[] {1});
        outputsList.add(new double[] {1});
        return outputsList;
    }

    public static List<Network> createDemoNetwork() {

        List<Node> nodeList_1 = new ArrayList<>();
        List<Connection> connectionList_1 = new ArrayList<>();
        List<Node> nodeList_2 = new ArrayList<>();
        List<Connection> connectionList_2 = new ArrayList<>();

        // 1. 生成第一个nodeList
        for(int i = 1 ; i <= 5 ; i ++) {
            Node node = new Node();
            if(i <= 3) {
                node = newNode((long) i, "sensor", "input");
                node.setInputValue(1);
            } else if(i == 4) {
                node = newNode((long) i, "hidden", "");
            } else if(i == 5) {
                node = newNode((long) i, "sensor", "output");
            }
            nodeList_1.add(node);
        }

        // 2. 生成第一个ConnectionList
        for(int i = 1 ; i <= 5 ; i ++) {
            Connection connection = new Connection();
            switch (i) {
                case 1:
                    connection = newConnection(1L, nodeList_1.get(0), nodeList_1.get(3));
                    break;
                case 2:
                    connection = newConnection(2L, nodeList_1.get(1), nodeList_1.get(3));
                    break;
                case 3:
                    connection = newConnection(4L, nodeList_1.get(1), nodeList_1.get(4));
                    break;
                case 4:
                    connection = newConnection(5L, nodeList_1.get(2), nodeList_1.get(4));
                    break;
                case 5:
                    connection = newConnection(6L, nodeList_1.get(3), nodeList_1.get(4));
                    break;
            }
            connectionList_1.add(connection);
        }


        // 3. 生成第二个nodeList
        for(int i = 1 ; i <= 6 ; i ++) {
            Node node = new Node();
            if(i <= 3) {
                node = newNode((long) i, "sensor", "input");
                node.setInputValue(1);
            } else if(i == 4 || i == 6) {
                node = newNode((long) i, "hidden", "");
            } else if(i == 5) {
                node = newNode((long) i, "sensor", "output");
            }
            nodeList_2.add(node);
        }

        // 4. 生成第二个ConnectionList
        for(int i = 1 ; i <= 7 ; i ++) {
            Connection connection = new Connection();
            switch (i) {
                case 1:
                    connection = newConnection(1L, nodeList_2.get(0), nodeList_2.get(3));
                    break;
                case 2:
                    connection = newConnection(2L, nodeList_2.get(1), nodeList_2.get(3));
                    break;
                case 3:
                    connection = newConnection(3L, nodeList_2.get(2), nodeList_2.get(3));
                    break;
                case 4:
                    connection = newConnection(4L, nodeList_2.get(1), nodeList_2.get(4));
                    break;
                case 5:
                    connection = newConnection(6L, nodeList_2.get(3), nodeList_2.get(4));
                    break;
                case 6:
                    connection = newConnection(7L, nodeList_2.get(0), nodeList_2.get(5));
                    break;
                case 7:
                    connection = newConnection(8L, nodeList_2.get(5), nodeList_2.get(3));
                    break;
            }
            connectionList_2.add(connection);
        }

        Network network_1 = new Network();
        Network network_2 = new Network();
        List<Network> networkList = new ArrayList<>();
        network_1.setId(1L);
        network_1.setNodeList(nodeList_1);
        network_1.setConnectionList(connectionList_1);
        network_2.setId(2L);
        network_2.setNodeList(nodeList_2);
        network_2.setConnectionList(connectionList_2);
        networkList.add(network_1);
        networkList.add(network_2);
        /* function ends */
        return networkList;
    }


/* class ends */
}
