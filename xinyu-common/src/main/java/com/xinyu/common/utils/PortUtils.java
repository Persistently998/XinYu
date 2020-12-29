package com.xinyu.common.utils;

import java.io.IOException;
import java.net.*;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*端口扫描*/
public class PortUtils {


    /**
     * @param ip 输入ip
     */
    public void ip(String ip) {
        System.out.println(ip);
        for (int i = 0; i <= 65535; i++) {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, i), 5000);
                if (socket.isBound()) {
                    System.out.println(ip);
                    System.out.println(i + "端口开发\n");
                }
            } catch (Exception e) {
                System.out.println(i + "未开放");
            }
        }
    }

    /**
     * 扫描方式二：针对一个待扫描的端口的Set集合进行扫描
     *
     * */

    /**
     * 多线程扫描目标主机指定Set端口集合的开放情况
     *
     * @param ip           待扫描IP或域名,eg:180.97.161.184 www.zifangsky.cn
     * @param portSet      待扫描的端口的Set集合
     * @param threadNumber 线程数
     * @param timeout      连接超时时间
     */
    public void scanLargePorts(String ip, Set<Integer> portSet,
                               int threadNumber, int timeout) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNumber; i++) {

            ScanMethod2 scanMethod2 = new ScanMethod2(ip, portSet,
                    threadNumber, i, timeout);
            threadPool.execute(scanMethod2);
        }
        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                long a = System.currentTimeMillis();
                System.out.println("扫描结束");
                System.out.print("程序执行时间为：");
                System.out.println(System.currentTimeMillis() - a + "毫秒");
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class ScanMethod2 implements Runnable {
        private String ip; // 目标IP
        private Set<Integer> portSet; // 待扫描的端口的Set集合
        private int threadNumber, serial, timeout; // 线程数，这是第几个线程，超时时间

        public ScanMethod2(String ip, Set<Integer> portSet, int threadNumber,
                           int serial, int timeout) {
            this.ip = ip;
            this.portSet = portSet;
            this.threadNumber = threadNumber;
            this.serial = serial;
            this.timeout = timeout;
        }

        public void run() {

            int port = 0;
            Integer[] ports = portSet.toArray(new Integer[portSet.size()]); // Set转数组
            try {
                InetAddress address = InetAddress.getByName(ip);
                Socket socket;
                SocketAddress socketAddress;
                if (ports.length < 1)
                    return;
                for (port = 0 + serial; port <= ports.length - 1; port += threadNumber) {
                    socket = new Socket();
                    socketAddress = new InetSocketAddress(address, ports[port]);
                    try {
                        socket.connect(socketAddress, timeout);
                        socket.close();
                        System.out.println("端口 " + ports[port] + " ：开放");
                    } catch (IOException e) {
                         //System.out.println("端口 " + ports[port] + " ：关闭");
                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }


        }

    }

}
