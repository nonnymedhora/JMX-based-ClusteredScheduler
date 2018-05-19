package com.bawaweb.common.mbean;

import java.util.Date;
import javax.management.*;
import javax.management.timer.*;
import java.lang.reflect.*;
// bawaweb
import com.bawaweb.common.log.Log;

public class ScheduledTaskFactory {
   
   private static final ScheduledTaskFactory factory;
   
   
   static {
      factory = new ScheduledTaskFactory();
   }
   
   private ScheduledTaskFactory() {
   }
   
   public static synchronized ScheduledTaskFactory getFactory() {
      return factory;
   }
   
   
   public ScheduledTaskInfo getAllScheduledTasks() {
      ScheduledTaskInfo scheduledTaskInfo = getTasksFromDatabase();
      Log.info("IsNULL ScheduleTaskInfo: " + scheduledTaskInfo.isEmpty()); 
      return scheduledTaskInfo;
   }
   
   
   private ScheduledTaskInfo getTasksFromDatabase() {
      
      //ScheduledTaskAgent = getAllTasks();
      String sql = "Select * from ScheduledTasks";//ResultSet
      ScheduledTaskInfo info = new ScheduledTaskInfo();
      /*         
      ScheduledTask t = new ScheduledTask("bawaweb:name=listener1,source=timer", "command1", new Date(), Timer.ONE_DAY);
      ScheduledTask t1 = new ScheduledTask("bawaweb:name=listener2,source=timer", "command2", new Date(), Timer.ONE_SECOND * 5, Timer.ONE_MINUTE * 5, 4L);
      ScheduledTask t2 = new ScheduledTask("bawaweb:name=listener3,source=timer", "command2", new Date(), Timer.ONE_SECOND * 5, Timer.ONE_MINUTE * 25, 10L);
      ScheduledTask t3 = new ScheduledTask("bawaweb:name=listener4,source=timer", "command3", new Date(), Timer.ONE_SECOND * 10, Timer.ONE_MINUTE * 5, 10L);
      */
      ScheduledTask t4 = new ScheduledTask("bawaweb:name=listener4,source=timer", "command4", new Date(), Timer.ONE_SECOND * 5, Timer.ONE_SECOND * 20, 5L);
      //ScheduledTask t5 = new ScheduledTask("bawaweb:name=listener5,source=timer", "command5", new Date(), Timer.ONE_SECOND * 5, Timer.ONE_SECOND * 20, 10L);
      
      /*
      info.addScheduledTask(t);
      info.addScheduledTask(t1);
      info.addScheduledTask(t2);
      info.addScheduledTask(t3);
      */
      info.addScheduledTask(t4);
      //info.addScheduledTask(t5);
      
      return info;
   }
     
}