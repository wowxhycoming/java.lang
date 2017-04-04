package me.xhy.java.lang.java5.nc2Concurrent;

/*
 * 题目：判断打印的 "one" or "two" ？
 *
 * 1. 两个普通同步方法，两个线程，标准打印， 打印? //one  two
 * 2. 新增 Thread.sleep() 给 getOne() ,打印? //one  two
 * 3. 新增普通方法 getThree() , 打印? //three  one   two
 * 4. 两个普通同步方法，两个 Number 对象，打印?  //two  one
 * 5. 修改 getOne() 为静态同步方法，打印?  //two   one
 * 6. 修改两个方法均为静态同步方法，一个 Number 对象?  //one   two
 * 7. 一个静态同步方法，一个非静态同步方法，两个 Number 对象?  //two  one
 * 8. 两个静态同步方法，两个 Number 对象?   //one  two
 *
 * 线程锁是否互斥的关键：
 * 1. 非静态方法的锁默认为 this,  静态方法的锁为 对应的 Class 实例
 * 2. 某一个时刻内，只能有一个线程持有锁，无论几个方法。
 */
public class C7Thread9LockSize {

    public static void main(String[] args) {

        // 两个普通方法无锁
//        s1();

        // 非静态方法上的 synchronized 锁的是 <this> ， 只 new 一个对象的情况下，锁是相同的，要排队。
//        s2();

        // 普通方法与锁无关
//        s3();

        // 非静态方法上的 synchronized 在多对象的情况下互不干预，因为锁的是 this，多个对象，多个this，多个锁。
//        s4();

        // 静态方法上的锁是 Object.class ， 相同对象下的两个方法锁不同，一个是Object.class，一个是this
//        s5();

        // 两个方法都是静态方法，锁相同，排队
//        s6();

        //
//        s7();

        // 两个对象的方法中，锁都是 Object.class
//        s8();

        // 可重入锁，也叫做递归锁，指的是同一线程 外层函数获得锁之后 ，内层递归函数仍然有获取该锁的代码，但不受影响。
        // 在JAVA环境下 ReentrantLock 和synchronized 都是 可重入锁
        s9();
    }


    // 同一对象上的 不同 synchronized 方法
    private static void s1() {

        Number1 number1 = new Number1();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number1.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number1.getTwo();
            }
        }).start();
    }

    private static void s2() {

        Number2 number2 = new Number2();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number2.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number2.getTwo();
            }
        }).start();
    }

    private static void s3() {

        Number3 number3 = new Number3();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number3.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number3.getTwo();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number3.getThree();
            }
        }).start();
    }

    private static void s4() {

        Number4 number4_1 = new Number4();
        Number4 number4_2 = new Number4();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number4_1.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number4_2.getTwo();
            }
        }).start();

    }

    private static void s5() {

        Number5 number5 = new Number5();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number5.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number5.getTwo();
            }
        }).start();

    }

    private static void s6() {

        Number6 number6 = new Number6();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number6.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number6.getTwo();
            }
        }).start();

    }

    private static void s7() {

        Number7 number7_1 = new Number7();
        Number7 number7_2 = new Number7();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number7_1.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number7_2.getTwo();
            }
        }).start();

    }

    private static void s8() {

        Number8 number8_1 = new Number8();
        Number8 number8_2 = new Number8();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number8_1.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number8_2.getTwo();
            }
        }).start();

    }

    private static void s9() {
        Number9 number9 = new Number9();

        new Thread(() -> number9.method1()).start();
    }

}

class Number1 {

    public synchronized void getOne() {
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }

}

class Number2 {

    public synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }

}

class Number3 {

    public synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }

    public void getThree() {
        System.out.println("three");
    }

}

class Number4 {

    public synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }

}

class Number5 {

    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }

}

class Number6 {

    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public static synchronized void getTwo() {
        System.out.println("two");
    }

}

class Number7 {

    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }

}

class Number8 {

    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public static synchronized void getTwo() {
        System.out.println("two");
    }

}

class Number9 {

    public synchronized static void method1() {
        System.out.println("method1");
        method2();
    }

    public synchronized static void method2() {
        System.out.println("method1");
        method3();
    }

    public synchronized static void method3() {
        System.out.println("method1");
    }
}