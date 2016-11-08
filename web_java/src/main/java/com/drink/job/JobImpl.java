package com.drink.job;

import org.quartz.*;

import java.util.Date;

public class JobImpl implements Job{
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		    JobDetail jobDetail = context.getJobDetail();
	        String jobName = jobDetail.getName();//任务名称
	        System.out.println( jobName +"执行任务开始");
	        JobDataMap dataMap = jobDetail.getJobDataMap();
//            QuartzService quartzService=(QuartzService)((BeanService) ApplicationContextUtils.getBean(BeanNames.beanService)).getInstance(BeanNames.subQuartzService);
	       // quartzService.removeJob(jobName);
            System.out.println("执行任务时间:"+new Date());
	        System.out.println( jobName+"执行任务结束");
		
	}




}
