package me.xhy.java.lang.java8.nc1Lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by xuhuaiyu on 2017/2/11.
 *
 * 利用 lambda 和 行为参数化 让代码变的更灵活
 */
public class C6Around {
    public C6Around() throws IOException {
    }

    // 读取固定文件中的一行数据
    public static String processFile() throws IOException {
        // 带资源的 try 语句，不再需要关闭资源
        try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))) { // 只有这一行是有用工作的代码
            return br.readLine();
        }
    }
    // 初始化/准备 代码
    // 任务 1 | 2 | 3 | 4 | 5
    // 清理/结束 代码

    // 想读两行了怎么办 ：行为参数化
    // 1. 行为参数化 ： 需要一个 接收 BufferedReader 返回 String 的 Lambda 。
    // (BufferedReader br) -> br.readLine() + br.readLine()

    // 2. 使用函数式接口来传递行为
    // C6BufferedReaderProcessor

    // 3. 执行一个行为
    public static String processFile(C6BufferedReaderProcessor p) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return p.process(br);
        }
    }

    // 4. 传递 lambda
    String oneLine = processFile(br -> br.readLine());


}
