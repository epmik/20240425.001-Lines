import processing.core.*;

public class Main 
{
 
  public static void main(String[] passedArgs) 
  {
      Sketch.Instance = new Sketch();
      String[] processingArgs = {"Sketch"};
      PApplet.runSketch(processingArgs, Sketch.Instance);
  }
}