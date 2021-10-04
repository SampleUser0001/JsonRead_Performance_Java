package sample.json.performance.task;

import java.util.List;
import sample.json.performance.model.TimestampModel;
import sample.json.performance.util.Util;

public class DoubleCheckTask implements Runnable {
  
  private TimestampModel timestamp;
  
  public DoubleCheckTask(TimestampModel timestamp){
    this.timestamp = timestamp;
  }
  
  @Override
  public void run(){
    Util.doubleCheck(this.timestamp);
  }
}