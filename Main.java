import processing.core.*;

public class Main 
{
 
  public static void main(String[] passedArgs) 
  {
      //Sketch.Instance = new SketchTrajectoryCurvy001();
      // Sketch.Instance = new SketchTrajectoryStraight001();
      AbstractSketch.Instance = new Sketch();

      PApplet.runSketch(new String[] {"Sketch"}, AbstractSketch.Instance);
  }
}