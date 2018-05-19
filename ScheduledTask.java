
package com.bawaweb.common.mbean;

import java.util.*;
import javax.management.*;
import javax.management.timer.*;
import java.lang.reflect.*;


// bawaweb
import com.bawaweb.common.log.Log;

public class ScheduledTask {
   
   private String name;             // the name of the scheduled task
   private String command;          // the command which is to be executed by the scheduled task
   private Date timeToExecute;      // the time at which to start execution 
   private long interval;           // the timer interval between each repetition
   private long duration;           // the duration in milliseconds - for how long should this task execute
   private long reps;                // the number of repetitions
   
   public ScheduledTask() {
   }
   
   public ScheduledTask(String taskname, String theCommand, Date theTime, long theInterval, long theDuration, long theReps) {
      name = taskname;
      command = theCommand;
      timeToExecute = theTime;
      duration = theDuration;
      reps = theReps;      
   }
   
   public ScheduledTask(String taskName, String theCommand, Date theTime, long theInterval) {
      this(taskName, theCommand, theTime, theInterval, 0L, 0L);
   }
   
   public String toString() {
      return "ScheduleTask { name = '" + name + "', command = '" + command + "', timeToExecute = '" + timeToExecute + 
         "', duration = '" + duration + "', reps = '" + reps + "' }";
      
   }
   
   
   public String getName() {
      return name;
   }
   
   public String getCommand() {
      return command;
   }
   
   public Date getTimeToExecute() {
      return timeToExecute;
   }
   
   public long getInterval() {
      return interval;
   }
   
   public long getDuration() {
      return duration;
   }
   
   public long getReps() {
      return reps;
   }
   
   public void setName(String theName) {
      name = theName;
   }
   
   public void setCommand(String theCommand) {
      command = theCommand;
   }
   
   public void setTimeToExecute(Date time) {
      timeToExecute = time;
   }
   
   public void setDuration(long theDuration) {
      duration = theDuration;
   }
   
   public void setReps(long repetitions) {
      reps = repetitions;
   }
   
   
   
}