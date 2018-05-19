package com.bawaweb.common.mbean;

import java.util.*;
import javax.management.*;
import javax.management.timer.*;
import java.lang.reflect.*;

// bawaweb
import com.bawaweb.common.log.Log;


public class ScheduledTaskInfo {
   
   public boolean isEmpty() {
      return tasks.isEmpty() ? true : false;
   }
   
   private static ArrayList tasks;
   
   public ScheduledTaskInfo() {
      tasks = new ArrayList();
   }
   
   public ArrayList getAllScheduledTasks() {
      return tasks;
   }
   
   public void addScheduledTask(ScheduledTask task) {
      tasks.add((ScheduledTask)task);
   }
   
   public void deleteScheduledTask(String name) {
      Iterator it = tasks.iterator();
      while(it.hasNext()) {
         ScheduledTask theTask = (ScheduledTask) it.next();
         String taskName = theTask.getName();
         if (name.equals(taskName)) {
            tasks.remove(theTask);
            return;
         }
      }
         
   }
}