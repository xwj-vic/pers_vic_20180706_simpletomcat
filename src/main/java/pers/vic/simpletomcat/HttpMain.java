package pers.vic.simpletomcat;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import pers.vic.simpletomcat.data.BooksMap;
import pers.vic.simpletomcat.data.ResourceList;
import pers.vic.simpletomcat.thread.MyServerThread;
import java.util.concurrent.*;

/**
 * Create by Vic Xu on 2018/7/6
 * @author Administrator
 */
public class HttpMain {


    public static void main(String[] args) {
        BooksMap.initBooks();
        ResourceList.initUrlList();
        ResourceList.initUrlSuffixList();
        ResourceList.initRedirectMap();
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("http-pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(20, 200, 5,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        pool.execute(new MyServerThread());
        pool.shutdown();
    }
}
