package com.lolinico.technical.open.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by Rico on 2016/8/12.
 */
public class ExecutorpoolServiceApi {
    private final int CODE = 200;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);// 线程池
    private static ExecutorpoolServiceApi executorpoolController;
//    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(15);
//
//    private ThreadPoolExecutor executorService = new ThreadPoolExecutor(15, 15, 1, TimeUnit.MINUTES, queue, new ThreadPoolExecutor.CallerRunsPolicy());

    /****
     * 获取单例
     */
    public static ExecutorpoolServiceApi getInstance() {
        if (executorpoolController == null) {
            executorpoolController = new ExecutorpoolServiceApi();
        }
        return executorpoolController;
    }

    /**
     * 调用该函数执行服务器上相应的API，onResult中返回从服务器上获取到的String
     * ，HttpRequestTask本身已经做好了一个线程池来执行execServerApi
     */

    public void execServerApi(Runnable runnable) {
        executorService.submit(runnable);
    }

}
