import processing.core.*;

public class Main 
{
 
  public static void main(String[] passedArgs) 
  {
      //Sketch.Instance = new SketchTrajectoryCurvy001();
      // Sketch.Instance = new SketchTrajectoryStraight001();
      Sketch.Instance = new Sketch002();

      PApplet.runSketch(new String[] {"Sketch"}, Sketch.Instance);
  }
}