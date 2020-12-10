package com.okay;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @create 2020-07-31 15:11
 */
public class ZookeeperSyncWatcher implements Watcher {

    private static CountDownLatch latch = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        String path = "/username";
        zk = new ZooKeeper("127.0.0.1:2181",5000, new ZookeeperSyncWatcher());
        latch.await();
        byte[] data = zk.getData(path, true, stat);
        String dataStr = new String(data, "UTF-8");
        System.out.println(dataStr);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) { // 成功连接
            System.out.println("成功连接到zookeeper");
            if (Event.EventType.None == watchedEvent.getType() &&
                null == watchedEvent.getPath()) {
                System.out.println("无需要监控的配置");
                latch.countDown();
            } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) { // 节点数据变化
                try {
                    byte[] data = zk.getData(watchedEvent.getPath(), true, stat);
                    String dataStr = new String(data, "UTF-8");
                    System.out.println("监控到节点数据变化=>"+dataStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
