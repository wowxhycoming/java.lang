# java.util.concurrent

## 线程池

1. 线程池

- Executor : 具体Runnable任务的执行者

	Executor 提供一种将任务提交与每个任务未来如何运行的机制（包括线程使用的细节、调度等）分离开来的方法。
	方法 execute() 向线程池提交任务

- ExecutorService 接口 : 一个线程池管理者，其实现类有多种。我们能把 Runnable,Callable 提交到池中让其调度

	一个 ExecutorService 包括运行、关闭、终止三个生命阶段，当一个 ExecutorService 处于关闭状态时，不能再向线程池提交任务。
	线程池管理，提交任务功能扩展 submit

- AbstractExecutorService : ExecutorService的实现

	AbstractExecutorService 的实现类 : ThreadPoolExecutor,ScheduledThreadPoolExecutor ，他们是具体的线程池实现类。
	
- ThreadPoolExecutor

- ScheduledThreadPoolExecutor

	一个 ExecutorService，可安排在给定的延迟后运行或定期执行的命令。

2. CompletionService 接口、ExecutorCompletionService 类

	能返回已完成任务，维护一个已完成任务队列（Future），通过 take() 函数可以获得任务。
	
	CompletionService，ExecutorService的扩展，该接口将生产新的异步任务与使用已完成任务的结果分离开来的服务。
	生产者 submit 执行的任务。使用者 take 已完成的任务，并按照完成这些任务的顺序处理它们的结果。
	
	ExecutorCompletionService，依赖于一个线程池对象 Executor，在一个 Executor 基础上维护一个已完成任务队列。

3. 任务接口

- Callable,Runnable

	Callable 是能够返回结果的可执行任务，Runnable 不返回执行结果。

- Future

	文档中说 Future 表示异步计算的结果，从 CompletionService 接口来理解，感觉不如说 Future 是执行完之后，包含返回结果且可以取消的任务。

- 如何提交

	execute 方法只能提交 Runnable 任务，
	ExecutorService 接口扩展的 submit 方法还可以提交 Callable 任务，
	ExecutorCompletionService 类的 submit 也支持两种任务。

- FutureTask

	实现了 RunnableFuture 接口，且可以由一个 Callable 对象来构造，基本包括了三个接口的功能。

4. 创建线程池

4.1. Executors 类 : 提供了用于此包中所提供的执行程序服务的工厂方法。

- newFixedThreadPool()

固定大小线程池:创建一个可重用固定线程集合的线程池，以共享的无界队列方式来运行这些线程（只有要请求的过来，就会在一个队列里等待执行）。
如果在关闭前的执行期间由于失败而导致任何线程终止，那么一个新线程将代替它执行后续的任务（如果需要）。

- newCachedThreadPool()

无界线程池，可以进行自动线程回收:创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
对于执行很多短期异步任务的程序而言，这些线程池通常可提高程序性能。调用 execute 将重用以前构造的线程（如果线程可用）。
如果现有线程没有可用的，则创建一个新线程并添加到池中。终止并从缓存中移除那些已有 60 秒钟未被使用的线程。
因此，长时间保持空闲的线程池不会使用任何资源。注意，可以使用 ThreadPoolExecutor 构造方法创建具有类似属性但细节不同（例如超时参数）的线程池。

- newSingleThreadExecutor()

单个后台线程:创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程。
（注意，如果因为在关闭前的执行期间出现失败而终止了此单个线程，那么如果需要，一个新线程将代替它执行后续的任务）。
可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的。
与其他等效的 newFixedThreadPool(1) 不同，可保证无需重新配置此方法所返回的执行程序即可使用其他的线程。

> 这些方法返回的都是ExecutorService对象，这个对象可以理解为就是一个线程池。

## 线程同步控制

1. CountDownLatch

一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
CountDownLatch 是一个通用同步工具，它有很多用途。
将计数 1 初始化的 CountDownLatch 用作一个简单的开/关锁存器，或入口：在通过调用 countDown() 的线程打开入口前，所有调用 await 的线程都一直在入口处等待。
用 N 初始化的 CountDownLatch 可以使一个线程在 N 个线程完成某项操作之前一直等待，或者使其在某项操作完成 N 次之前一直等待。（API文档）
await会造成一个线程的阻塞，直到countdown到0；文档中说CountDownLatch 等待的是事件，也就是countdown到0，即之前的所有所需操作完成。

2. CyclicBarrier

一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。
在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。
 
多个线程共同工作，一个常用的比方是：大家一起出游，分别到达集合地点，共同去往目的地；
到达目的地，分散活动，午餐时间集合；餐后分散，结束后集合回程。这种先分别执行，达到某一时间点，线程间相互等待，共同执行后，再分散，在集中的情形。

> 以上二者的区别：
> CountDownLatch适用于有明确先后顺序的两组操作，前一组完成后开启后一组，开启功能完成（countdown到0），失去作用；
	await使线程等待，结束等待条件是countdown到0.
>  
> CyclicBarrier适用于多个线程之间需要相互等待，即线程的任务间有分-合-分-合的关系，只有所有线程都达到条件后才能进行下一步操作（合），
	CyclicBarrier可以循环使用；await使线程等待，结束等待条件是等待的线程达到要求数目。

3. 信号量Semaphore

Semaphore是一个计数信号量，通常用于限制可以访问某些资源（物理或逻辑的）的线程数目。包含一个阻塞队列，信号量维护了一个许可集，在线程访问资源前，先要获取许可，许可通过acquire获取，若许可不可得则线程阻塞。release用于释放许可。流程：获取许可，同步获取资源，释放资源，释放许可。

4. Exchanger
用于线程间成对的交换数据，当一方数据准备好后调用exchange会等待另一方数据准备完成。
每个线程将条目上的某个方法呈现给 exchange 方法，与伙伴线程进行匹配，并且在返回时接收其伙伴的对象。


## 同步集合

1. Queue

2. BlockingQueue接口 : Queue 的子类(extends Queue)

3. AbstractQueue : Queue 的实现类(implements Queue)

4. ArrayBlockingQueue : (extends AbstractQueue implements BlockingQueue)

5. LinkedBlockingQueue : (extends AbstractQueue implements BlockingQueue)

6. SynchronousQueue : (extends AbstractQueue implements BlockingQueue)

7. DelayQueue : (extends AbstractQueue implements BlockingQueue)


## 锁

1. ReentrantLock

Lock 框架是同步的兼容替代品，它提供了 synchronized 没有提供的许多特性，它的实现在争用下提供了更好的性能。
但是，这些明显存在的好处，还不足以成为用 ReentrantLock 代替 synchronized 的理由。
相反，应当根据您是否需要 ReentrantLock 的能力来作出选择。
大多数情况下，您不应当选择它 —— synchronized 工作得很好，可以在所有 JVM 上工作，更多的开发人员了解它，而且不太容易出错。
只有在真正需要 Lock 的时候才用它。

既然如此，我们什么时候才应该使用 ReentrantLock 呢？
答案非常简单 —— 在确实需要一些 synchronized 所没有的特性的时候，比如时间锁等候、可中断锁等候、无块结构锁、多个条件变量或者锁投票。

Lock 和 synchronized 有一点明显的区别 —— lock 必须在 finally 块中释放。
否则，如果受保护的代码将抛出异常，锁就有可能永远得不到释放！这一点区别看起来可能没什么，但是实际上，它极为重要。
忘记在 finally 块中释放锁，可能会在程序中留下一个定时炸弹，当有一天炸弹爆炸时，您要花费很大力气才有找到源头在哪。而使用同步，JVM 将确保锁会获得自动释放。
```
Lock lock = new ReentrantLock();  
lock.lock(); 
try {   
  // update object state  
}  
finally {  
  lock.unlock();   
}
```


# 循序
ExecutorServiceTest
SemaphoreTest
ReentrantLockTest