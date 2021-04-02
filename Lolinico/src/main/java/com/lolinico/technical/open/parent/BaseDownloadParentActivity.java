package com.lolinico.technical.open.parent;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.zeasn.c.download.SingletonTaskObserverProxy;
import com.zeasn.c.download.TaskConst;
import com.zeasn.c.download.TaskServerMananger;
import com.zeasn.c.download.exception.WrongUrlException;
import com.zeasn.c.download.hdl.HandlersManager;
import com.zeasn.c.download.model.TaskInfo;

/**
 * ---------------------------------------------------------<br />
 * desc：<br />
 * author：Darren Chen <br />
 * date：2019/2/15<br />
 * email：darren.chen@zeasn.com<br />
 * ---------------------------------------------------------<br />
 */
public abstract class BaseDownloadParentActivity extends BaseParentActivity {

    //    private String mDownloadUrl;
    private TaskInfo mInfo;
    private SingletonTaskObserverProxy proxy;

    protected void setDownloadUrl(String url, String pkg) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(pkg)) throw new IllegalArgumentException("url or pkg must not be null or empty!! ");
        this.mInfo = new TaskInfo();
        this.mInfo.setTaskUrl(url);
        this.mInfo.setPkg(pkg);
        this.mInfo.setAutoInstall(true);
        TaskServerMananger.getInstance().registSingletonProxy(this.mInfo, this.proxy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        proxy = new SingletonTaskObserverProxy(obtainMsgHandler());
    }

    /**
     * desc：开始下载任务<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected void download() {
        if (this.mInfo == null)
            throw new IllegalArgumentException("download file url is empty please call setDownloadUrl after activity created.");
        else {
            try {
                TaskServerMananger.getInstance().startTask(this.mInfo, this.proxy);
            } catch (WrongUrlException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * desc：返回要下载文件的下载信息<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected TaskInfo getTaskInfo() {
        if (this.mInfo == null)
            throw new IllegalArgumentException("download file url is empty please call setDownloadUrl after activity created.");
        else
            return TaskServerMananger.getInstance().getTaskInfo(this.mInfo.getPkg());
    }

    /**
     * desc：获取当前下载文件的状态信息<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected int getTaskStatus() {
        if (this.mInfo == null)
            throw new IllegalArgumentException("download file url is empty please call setDownloadUrl after activity created.");
        else {
            if (getTaskInfo() == null) {
                return TaskConst.TASK_IDEL;
            } else {
                return getTaskInfo().getTaskStatus();
            }
        }
    }

    /**
     * desc：判断下载任务是否暂停<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected boolean isDownloadPaused() {
        if (this.mInfo == null) {
            throw new IllegalArgumentException("download file url is empty please call setDownloadUrl after activity created.");
        } else {
            TaskInfo taskInfo = TaskServerMananger.getInstance().getTaskInfo(this.mInfo.getPkg());
            int taskStatus = taskInfo.getTaskStatus();
            return taskStatus == TaskConst.TASK_PAUSED || taskStatus == TaskConst.TASK_ERR;
        }
    }

    /**
     * desc：暂停下载任务<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected void pauseDownload() {
        if (this.mInfo == null) {
            throw new IllegalArgumentException("download file url is empty please call setDownloadUrl after activity created.");
        } else {
            TaskServerMananger.getInstance().pauseTask(this.mInfo.getPkg());
        }
    }

    /**
     * desc：取消下载任务<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected void cancleDownload() {
        if (this.mInfo == null)
            throw new IllegalArgumentException("download file url is empty please call setDownloadUrl after activity created.");
        else
            TaskServerMananger.getInstance().cancleTask(this.mInfo.getPkg());
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.obj instanceof TaskInfo) {
            TaskInfo taskInfo = (TaskInfo) msg.obj;
            int taskStatus = taskInfo.getTaskStatus();
            //处理返回的状态结果，然后刷新界面ui
            switch (taskStatus) {
                case TaskConst.TASK_CANCLED:
                    taskCancle(taskInfo);
                    break;
                case TaskConst.TASK_ERR:
                    taskErr(taskInfo);
                    break;
                case TaskConst.TASK_FINISH:
                    taskFinish(taskInfo);
                    break;
                case TaskConst.TASK_IDEL:
                    taskIdel(taskInfo);
                    break;
                case TaskConst.TASK_PAUSED:
                    taskPause(taskInfo);
                    break;
                case TaskConst.TASK_STARTED:
                    taskStart(taskInfo);
                    break;
                case TaskConst.TASK_RUNNING:
                    taskProgress(taskInfo);
                    break;
                case TaskConst.TASK_WAITING:
                    taskWaitting(taskInfo);
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        HandlersManager.getInstance().unRegistHandlerListener(this);
        if (this.mInfo != null) {
            TaskServerMananger.getInstance().unRegistProxy(this.mInfo, this.proxy);
        }
        super.onDestroy();
    }

    /**
     * desc：取消下载<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected abstract void taskCancle(TaskInfo info);

    /**
     * desc：下载错误<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected abstract void taskErr(TaskInfo info);

    /**
     * desc：下载完成<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected abstract void taskFinish(TaskInfo info);

    /**
     * desc：下载正在准备<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected abstract void taskIdel(TaskInfo info);

    /**
     * desc：暂停下载<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected abstract void taskPause(TaskInfo info);

    /**
     * desc：开始下载<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected abstract void taskStart(TaskInfo info);

    /**
     * desc：下载进度反馈<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected abstract void taskProgress(TaskInfo info);

    /**
     * desc：下载等待<br />
     * author：Darren Chen<br />
     * date：2019/2/12<br />
     */
    protected abstract void taskWaitting(TaskInfo info);
}
