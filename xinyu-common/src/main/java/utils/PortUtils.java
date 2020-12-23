package utils;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
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
                        // System.out.println("端口 " + ports[port] + " ：关闭");
                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) {
   /*     PortUtils portScanDemo = new PortUtils();
        //方式2
        Set<Integer> portSet = new LinkedHashSet<Integer>();
        Integer[] ports = new Integer[]{21, 22, 23, 25, 26, 37, 47, 49, 53, 69, 70, 79, 80, 81, 82, 83, 84, 88, 89, 110, 111, 119, 123, 129, 135,
                137, 139, 143, 161, 175, 179, 195, 311, 389, 443, 444, 445, 465, 500, 502, 503, 512, 513, 514, 515, 520,
                523, 530, 548, 554, 563, 587, 593, 623, 626, 631, 636, 660, 666, 749, 751, 771, 789, 873, 888, 901, 902, 990,
                992, 993, 995, 1000, 1010, 1023, 1024, 1025, 1080, 1088, 1099, 1111, 1177, 1200, 1234, 1311, 1325, 1352,
                1400, 1433, 1434, 1471, 1515, 1521, 1599, 1604, 1723, 1741, 1777, 1883, 1900, 1911, 1920, 1962, 1991,
                2000, 2049, 2067, 2081, 2082, 2083, 2086, 2087, 2121, 2123, 2152, 2181, 2222, 2323, 2332, 2333, 2375,
                2376, 2379, 2404, 2433, 2455, 2480, 2601, 2604, 2628, 3000, 3001, 3128, 3260, 3269, 3283, 3299, 3306,
                3307, 3310, 3311, 3312, 3333, 3386, 3388, 3389, 3460, 3478, 3493, 3541, 3542, 3560, 3661, 3689, 3690,
                3702,
                3749, 3794, 3780, 3784, 3790, 4000, 4022, 4040, 4063, 4064, 4070, 4200, 4343, 4369, 4400, 4440, 4443,
                4444,
                4500, 4550, 4567, 4664, 4730, 4782, 4786, 4800, 4840, 4848, 4899, 4911, 4949, 5000, 5001, 5006, 5007,
                5008,
                5009, 5060, 5094, 5222, 5269, 5353, 5357, 5431, 5432, 5433, 5555, 5560, 5577, 5601, 5631, 5632, 5666,
                5672,
                5683, 5800, 5801, 5858, 5900, 5901, 5938, 5984, 5985, 5986, 6000, 6001, 6014, 6082, 6371, 6372, 6373, 6374,
                6379, 6390, 6664,
                6666, 6667, 6881, 6969, 7000, 7001, 7002, 7071, 7080, 7218, 7474, 7547, 7548, 7549, 7657, 7777, 7779,
                7903,
                7905, 8000, 8001, 8008, 8009, 8010, 8060, 8069, 8080, 8081, 8082, 8083, 8086, 8087, 8088, 8089, 8090,
                8098,
                8099, 8112, 8126, 8139, 8140, 8161, 8181, 8191, 8200, 8291, 8307, 8333, 8334, 8443, 8554, 8649, 8688,
                8800, 8834, 8880, 8883, 8888, 8889, 8899, 9000, 9001, 9002, 9009, 9014, 9042, 9043, 9050, 9051, 9080,
                9081, 9090, 9092, 9100, 9151, 9160, 9191, 9200, 9300, 9306, 9418, 9443, 9595, 9600, 9869, 9903, 9943,
                9944, 9981, 9990, 9998, 9999, 10000, 10001, 10050, 10051, 10243, 10554, 11211, 11300, 12345, 13579, 14147,
                16010, 16992, 16993, 17000, 17778, 18081, 18245, 18505, 20000, 20547, 21025, 21379, 21546, 22022, 22222,
                23023, 23389, 23424, 25105, 25565, 27015, 27016, 27017, 27018, 27019, 28015, 28017, 28561, 30000, 30718,
                32400,
                32764, 32768, 32769, 32770, 32771, 33389, 33890, 33899, 37777, 38190, 40001, 40049, 40650, 41706, 42178,
                43382, 44818, 47808, 48899, 49152, 49153, 50000, 50010, 50011, 50015, 50030, 50050, 50060, 50070, 50100,
                51106, 53413, 54138, 55443, 55553, 55554, 62078, 64738, 65535};
        portSet.addAll(Arrays.asList(ports));
        portScanDemo.scanLargePorts("119.3.191.196", portSet, 100, 800);*/
        if(!"18086616848".equals("18086616848")||!"1808661684".equals("1808661684")){
            System.out.println("不容许归档");
        }else {
            System.out.println("false");
        }
    }
}
