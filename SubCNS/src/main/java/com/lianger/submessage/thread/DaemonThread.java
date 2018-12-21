package com.lianger.submessage.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lianger.submessage.context.Context;

public class DaemonThread implements Runnable {

	private static final Logger logger =LoggerFactory.getLogger(DaemonThread.class);
	
	private int backupsTime;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean flag = true;
		while(flag){
			try {
				Thread.currentThread().sleep(backupsTime*60*1000);
				logger.debug("开始备份词库");
				Context.backup();
				logger.debug("备份词库完成");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				flag = false;
				e.printStackTrace();
				logger.error("守护线程发生异常以停止,无法备份词库");
			}
		}
	}

	public DaemonThread(int backupsTime){
		this.backupsTime = backupsTime;
	}
}
