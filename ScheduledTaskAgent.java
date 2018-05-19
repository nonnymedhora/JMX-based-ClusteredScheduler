package com.bawaweb.common.mbean;

import java.util.*;
import javax.management.*;
import javax.management.timer.*;
import java.lang.reflect.*;

// bawaweb
import com.bawaweb.common.log.Log;

/*class which is responsible for interacting with the db and sends back */
public class ScheduledTaskAgent {
   
   private static final ScheduledTaskAgent agent;
   
   static {
      agent = new ScheduledTaskAgent();
   }
   
   private ScheduledTaskAgent() {
   }
   
   public static synchronized ScheduledTaskAgent getAgent() {
      return agent;
   }
   
   
   public void executeAllTasks() {
   }
   
   public boolean executeScheduledTask(ScheduledTask task) {
      boolean taskAssigned = false;
      return taskAssigned;
   }
   
   //public boolean 
   
   
   
 
}