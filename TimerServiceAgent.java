package com.bawaweb.common.mbean;

import javax.management.*;
import javax.management.timer.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.reflect.*;
// bawaweb
import com.bawaweb.common.log.Log;

/**
 * class that is the servicing agent for the Timer MBean
 * it interacts with the ScheduledTaskFactory
 * and the ScheduledTaskAgent
 * The ScheduledTaskFactory is set up to generate instances of ScheduledTasks
 * these instances can be returned in the form of a ScheduledTaskInfo
 * which essentially is an ArrayList of ScheduledTask objects.
 * The ScheduledTaskAgent interacts with the database
 * and executes the tasks 
 */

public class TimerServiceAgent {   
   
   private static Timer theTimer;
   
   private MBeanServer server = null;   
   private ObjectName timer = null;
   
   private static final TimerServiceAgent timerAgent;
   private static final ScheduledTaskFactory taskFactory;
   private static final ScheduledTaskAgent taskAgent;
   
   
   static {
      theTimer = new Timer();
      timerAgent = new TimerServiceAgent();
      taskFactory = ScheduledTaskFactory.getFactory();
      taskAgent = ScheduledTaskAgent.getAgent();
   }
   
   
   private TimerServiceAgent() {
      initTimerService();
   }
   
   public static synchronized TimerServiceAgent  getAgent() {
      return timerAgent;
   }
   
   public void invokeAllScheduledTasks() {
      try {
         ScheduledTaskInfo taskInfo = taskFactory.getAllScheduledTasks();
         invokeScheduledTasks(taskInfo);
      } catch (Exception e) {
         Log.error("EXCEPTION");
         e.printStackTrace();
      }
   }
   
   public ScheduledCommandListener addScheduleListener(ScheduledTask task)   {
      ScheduledCommandListener listener = null;
      try {
         if (task != null) {
            listener = new ScheduledCommandListener();
            
            String name = task.getName();
            
            ObjectName commandName = new ObjectName(name);
            
            if (!server.isRegistered(commandName)) {
               server.registerMBean(listener, commandName);
            }               
         }
      } catch (Exception e) {
         Log.error("Exception");
         e.printStackTrace();
      }
      
      return listener;
   }
   
   public Integer executeCommand(ScheduledTask task, ScheduledCommandListener listner) {
      Integer id = null;
      id = theTimer.addNotification(
         "timer.notification",         // type - dot-delimited notification type 
         task.getCommand(),            // message - the notification message
         task,                         // userData - [Optional User Data]  -- used for null resultset to change command dynamically
         task.getTimeToExecute(),      // date - date when notification will occur
         task.getDuration(),           // period - the interval in milliseconds between notification occurrences, repeating notifications not enabled if period is zero or null
         task.getReps());              // occurrences - the total number of times that the notification will occur. if zero/null & period is defined - then - repaets indefinitely
      
      return id;
   }
   
   public void invokeScheduledTasks(ScheduledTaskInfo info) {
      try {
         
         ArrayList tasks = info.getAllScheduledTasks();
         Iterator it = tasks.iterator();
         Log.debug("The tasks are: " + tasks);
         int i = 0;
         while(it.hasNext()) {
            /*  use this to pass a null object **
            Object userData = null;
            if ( i == 0 ) {
               userData = new Object();
            }
            i++;
            /**   **/
            
            ScheduledTask theTask = (ScheduledTask) it.next();
            
            ScheduledCommandListener listener = addScheduleListener(theTask);
            
            Integer id = executeCommand(theTask, listener);
            
            /* moved logic below to executeCommand method --         
                     
                     String name = theTask.getName();
                     String command = theTask.getCommand();
                     Date executionTime = theTask.getTimeToExecute();
                     long interval = theTask.getInterval();
                     long duration = theTask.getDuration();
                     long reps = theTask.getReps();
                     
                     
                     Log.info("Name is : " + name);
                     Log.info("Command is : " + command);
                     
                     
                     Log.info("CREATING ObjectName");
                     // this point is throwing exception since name is null - ? is that happending
                     ObjectName commandName = new ObjectName(name);
                     
                     Log.info("ObjectName: " + commandName.getCanonicalName());
                     
                     ScheduledCommandListener listener = new ScheduledCommandListener();
                     
                     listener.setCommand(command);
                     listener.setTime(executionTime);
                     listener.setInterval(interval);
                     listener.setDuration(duration);
                     listener.setRepetitions(reps);
                     
                     if (!server.isRegistered(commandName)) {
                        server.registerMBean(listener, commandName);
                     }
         */            
            //            Integer id = timer.addNotification("timer.notification", command, null, executionTime, new Long(duration));
            
            /*            
            Integer id = (Integer) server.invoke(
                           timer,                //Timer MBean - commandName,
                           "addNotification",         // operation - addnotification is being added to the Timer MBean -
                           new Object[] {             // arguments:
                              "timer.notification",   // type   
                              command,                // message
                              null,                   // user data
                              executionTime,          // time to start executing
                              new Long(duration)     // wait duration for repeated invoking
                              },
                           
                           new String[] {
                              String.class.getName(),
                              String.class.getName(),
                              Object.class.getName(),
                              Date.class.getName(),
                              Long.TYPE.getName()
                              }      
                           
                           );   
               */
            
            NotificationFilter filter = new TimerFilter(id);
            
            server.addNotificationListener( timer, listener, filter, theTask );//userData
            
         }
      } catch (Exception e) {
         Log.error("EXCEPTION");
         e.printStackTrace();
      }
      
   }
   
   
   public void invokeScheduledTask(ScheduledTask task) {
   }
   
   
   public void executeScheduledTasks() {
      ScheduledTaskInfo info = taskFactory.getAllScheduledTasks();
      invokeScheduledTasks(info);
   }
   
   public void initTimerService() {      
      List list = MBeanServerFactory.findMBeanServer( null );      
      server = (MBeanServer)list.get(0);      
      try {         
         // register the timer and receiver mbeans
         timer = new ObjectName( "service:name=timer" );
         
         // Check to see if the Timer MBean exists and has already been registered
         if (!server.isRegistered(timer)) {
            Log.info("Timer was not registered, Registering Timer");
            server.registerMBean( theTimer, timer );
         }
         
         // start the timer service
         Log.info("Starting timer service");
         server.invoke(timer, "start", null, null);
         
      } catch (Exception e) {
         Log.error("EXCEPTION OCCURRED");
         e.printStackTrace();
      }
   }
   
   
   
   //
   // Notification filter implementation.
   //
   class TimerFilter implements NotificationFilter {
      private Integer id = null;
      
      TimerFilter(Integer id) {
         this.id = id;
      }
      
      public boolean isNotificationEnabled(Notification n) {
         if (n.getType().equals("timer.notification")) {
            //            Log.info("Handling notification in TimerFilter - isNotificationEnabled(n)");
            TimerNotification notif = (TimerNotification)n;
            /*
            Log.info("Timer notificationID is = " + notif.getNotificationID() );
                        Log.info( "Notification Info: Message = " + n.getMessage());
                        Log.info( "Notification Info: Type = " + n.getType());
                        Log.info( "Notification Info: TimeStamp = " + n.getTimeStamp());
                        
            */            if (notif.getNotificationID().equals(id))
               return true;
         }
         return false;
      }
   }// ends class TimerFilter
   
}
