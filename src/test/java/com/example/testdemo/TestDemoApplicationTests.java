package com.example.testdemo;

import com.example.config.CustomConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootTest
class TestDemoApplicationTests {

//    @Test
//    void contextLoads() throws Exception {
//
//        //不用mapper，避免每次自动提交，实现一次性提交
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.7:3306/fl?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT", "root", "123456");
//        connection.setAutoCommit(false);
//
//        final String sql = "insert into fl.sys_user (userName,gender,hobby,phone,email,address,displayName,idCard) values ";
//
//        List<String> lastNameList = Arrays.asList("赵", "钱", "孙", "李", "周", "吴", "郑", "王", "田", "朱", "谢", "余", "邓", "姚", "易");
//
//        List<String> firstNameList = Arrays.asList("一", "二", "三", "梦", "无", "西", "望", "浩", "霖", "峰", "雨", "奇", "田", "", "");
//
//        List<String> phonePrefixList = Arrays.asList("131", "132", "137", "177", "155", "136", "186", "199", "139", "138", "175");
//
//        Random random = new Random();
//
//        for (int i = 0; i < 10; i++) {
//            final int count = i;
//            StringBuilder sb = new StringBuilder();
//            PreparedStatement ps;
//            try {
//                ps = connection.prepareStatement(" ");
//            } catch (SQLException e) {
//                e.printStackTrace();
//                return;
//            }
//            for (int j = count * 1000000; j < count * 1000000 + 1000000; j++) {
//                try {
//                    if ((j + 1) % 100000 != 0) {
//                        String lastName = lastNameList.get(random.nextInt(lastNameList.size()));
//                        int i1 = (j + 1) % 2;
//                        if (i1 == 0) {
//                            lastName = lastName + firstNameList.get(random.nextInt(lastNameList.size())) + firstNameList.get(random.nextInt(lastNameList.size()));
//                        } else {
//                            lastName += firstNameList.get(random.nextInt(lastNameList.size()));
//                        }
//                        sb.append("('").append(lastName).append("',").append(i1).append(",'打游戏','")
//                                .append(phonePrefixList.get(random.nextInt(phonePrefixList.size())))
//                                .append(99999999 - random.nextInt(10000000)).append("','")
//                                .append(j)
//                                .append("@qq.com','四川成都','帅哥','")
//                                .append(j)
//                                .append("'),");
//                        continue;
//                    }
//                    ps.addBatch(sql + sb.substring(0, sb.length() - 1));
//                    ps.executeBatch();
//                    connection.commit();
//                    sb = new StringBuilder();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                ps.close();
//                System.gc();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

    @Resource
    private RedisTemplate<String, Object> customRedisTemplate;

    volatile static boolean over = false;

    volatile static boolean start = false;

    @Test
    public void test() throws InterruptedException {
        final String key = "SECONDS-KILL";
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicInteger atomicInteger = new AtomicInteger(10);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //返回的是之前的值
                int i = atomicInteger.get();
                atomicInteger.getAndDecrement();
                if (i == 10) {
                    System.out.println("倒计时开始:\r\n10");
                } else if (i > 0) {
                    System.out.println(i);
                } else {
                    start = true;
                    timer.cancel();
                }
            }
        }, new Date(), 1000);

        while (!start) {

        }

        customRedisTemplate.opsForValue().set(key, 50);

        CountDownLatch countDownLatch = new CountDownLatch(200);

        System.out.println("抢购开始");
        int i = 0;
        ReentrantLock lock = new ReentrantLock();
        //先往reids存100个元素，有序去取，取完将结果缓存到本地，其他线程可见
        while (i++ < 200) {
            executorService.execute(() -> {
                if (over) {
                    System.out.println(Thread.currentThread().getName() + ",秒杀结束，over");
                } else {
                    Integer count = (Integer) customRedisTemplate.opsForValue().get(key);
                    if (count == null) {
                        count = 0;
                    }
                    if (count >= 1) {
                        if (count == 1) {
                            over = true;
                        }
                        customRedisTemplate.opsForValue().set(key, --count, -1);
                        System.out.println(Thread.currentThread().getName() + ",抢到了，余量:" + count);
                    } else {
                        System.out.println(Thread.currentThread().getName() + ",秒杀结束，over");
                    }
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("秒杀结束");
    }

    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>(Arrays.asList("a","b","c","d"));
        int size = list.size();
//        for (int i = 0 ; i < size ; i++){
//            if(i == 2){
////                list.remove(i);
////                list.add(i,"e");
//                list.set(i,"e");
//            }
//        }
        for (String str : list){
            if("b".equals(str)){
                list.set(1,"e");
            }
        }
        System.out.println(list);
    }
}

