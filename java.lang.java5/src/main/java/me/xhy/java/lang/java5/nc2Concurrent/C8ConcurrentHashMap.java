package me.xhy.java.lang.java5.nc2Concurrent;

/**
 * Created by xuhuaiyu on 2017/3/11.
 * <p>
 * ConcurrentHashMap since 1.5
 * ConcurrentHashMap 是线程安全的 HashMap
 * <p>
 * 1. HashTable since 1.0 是线程安全的
 * 底层实现为 Hash 表。
 * (1) 在访问 HashTable 的时候，会锁整个表。 多个线程访问 HashTable 的时候，会发生并行转串行。
 * (2) 对 HashTable 进行复合操作的时候存在安全问题，如：“若存在则删除”、“若不存在则添加”。
 * if(!table.contains(key) {
 * ======= 在这里被剥夺执行权，put 可能存在已有元素 =======
 * table.put("","")
 * }
 * <p>
 * 效率非常低，即便要考虑线程安全问题，也不实用 HashTable。
 * <p>
 * 2. HashMap since 1.2 是线程不安全的
 * 底层与 HashTable 一样，都是 Hash 表。
 * <p>
 * 3. ConcurrentHashMap since 1.5
 * 采用“锁分段”机制
 * (1). concurrentLevel 分段级别，默认为16，表示将 ConcurrentHashMap 分为16个 Segment。
 * (2). 每个 Segment 有默认16个 Hash表。
 * (3). 每个 Hash表中存放着链表。
 * <p>
 * 每个 Segment 拥有独立的锁，也就是说当有多个线程访问不同的 Segment 的时候，是并发效果，并且线程安全。
 * <p>
 * 4. JDK1.5之前使用多线程
 * 将 HashMap 转换成 SynchronizedMap ，就是将 HashMap 中的所有方法都加上了 synchronized 关键字。
 * Collections.synchronized(new HashMap())
 * <p>
 * 5. concurrent 包下的其他并发容器
 * 用于多线程上下文中的 Collection 实现：ConcurrentHashMap、ConcurrentSkipListMap、ConcurrentSkipListSet、CopyOnWriteArrayList 和 CopyOnWriteArraySet。
 * (1). 当期望许多线程访问一个给定 collection 时，ConcurrentHashMap 通常优于同步的 HashMap，ConcurrentSkipListMap 通常优于同步的 TreeMap。
 * (2). 当期望的读数和遍历远远大于列表的更新数时，CopyOnWriteArrayList 优于同步的 ArrayList。
 * (3). ConcurrentLinkedQueue 是一个先进先出的队列。它是非阻塞队列。
 * (4). ConcurrentSkipListMap 、 ConcurrentSkipListSet 可以在高效并发中替代 SoredMap 、 SoredSet（例如用Collections.synchronizedMap 包装的 TreeMap 、 TreeSet）
 */
public class C8ConcurrentHashMap {

    // Map 怎么用 ConcurrentMap 就怎么用，没什么特别的
    public static void main(String[] args) {
        // 增加了复合操作
        // putIfAbsent 若不存在 就添加
        // remove(Object key, Object value) 给的定 key 对应给定的 value，就删除
        // replace(K key, V oldValue, V newValue) 给的定 key 对应给定的 oldValue，就替换成 newValue
    }
}
