package com.bawaweb.common.mbean;

import java.util.*;
import javax.management.*;
import javax.management.timer.*;
import java.lang.reflect.*;

// bawaweb
import com.bawaweb.common.log.Log;


public class ScheduledCommandListener implements ScheduledCommandListenerMBean, NotificationListener  {
   
   
   private static ArrayList tasks = new ArrayList();
   
   public ScheduledCommandListener() {
   }
   
   public ArrayList getAllScheduledTasks() {
      return tasks;
   }
   
   public ArrayList getTasks() {
      return tasks;
   }
   
   public void setTasks(ArrayList theTasks) {
      tasks = theTasks;
   }
   
   public void handleNotification(Notification n, Object hb) {
      Log.info("INSIDE CLASS ScheduledClassListener handleNotification");
      try {
         TimerNotification tn  = (TimerNotification) n;
         String task_Id= tn.getNotificationID().toString();
         String taskId = tn.getMessage();
         Log.info("Invoked Task - " + taskId );   
         if ( hb == null ) {
            Log.info("Hand BAck Object Is NUll - NOT SLEEPING");      
         }else{
            if ( hb instanceof ScheduledTask ) {
               Log.info("Hand BAck Object Is Not Null: " + hb.getClass().getName() +" Sleeping " + new Date());
               Thread.currentThread().sleep(60000); // 30 seconds 
               Log.info("Time After Sleep " + new Date());
            }
         }
         
      } catch (Exception e) {
         Log.error("EXCEPTION");
         e.printStackTrace();
      }
      /*   
      
         Log.info( "HELLO!!!!!  You got a Notification: " + tn );
         Log.info( "Notification Info: Message = " + tn.getMessage());
         
         System.out.println(tn.getMessage());
         
         actOnNotificationMessage(tn, hb);
         
      */   
   }
   
   private void actOnNotificationMessage(Notification n, Object hb) {
      Log.info("Inside actOnNotification");// calls Scheduler Service Bean.
      
   }
   
   /* removing this logic
   
   private String command;
   private String name;
   private Date timeToExecute;
   private long interval;
   private long duration;
   private long reps;
   
   public void setCommand(String thecommand) {
      command = thecommand;
   }
   
   public void setName(String thename) {
      name = thename;
   }
   
   public void setTime(Date theTime) {
      timeToExecute = theTime;
   }
   
   public void setInterval(long theinterval) {
      interval = theinterval;
   }
   
   public void setDuration(long theduration) {
      duration = theduration;
   }
   
   public void  setRepetitions(long thereps) {
      reps = thereps;
   }
   **/
   
}