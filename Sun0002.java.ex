import processing.core.*;

public class Sun0002 extends Sun0001
{
  public int sunRayCount = 400;
  public float _minSunRayLength= 100;
  public float _maxSunRayLength = 200;
  public float _minSunRayWeight = 20;
  public float _maxSunRayWeight = 80;

  @Override
  public void setup(Sketch sketch)
  {
    super.setup(sketch);

    _minSunRayLength = (_sketch.SketchWidth - 200) / 6;
    _maxSunRayLength = (_sketch.SketchWidth - 200) / 4;
  }
  
  @Override
  void drawSunLayerMask(PGraphics graphics)
  {
    graphics.beginDraw();

    graphics.pushMatrix();

    graphics.translate(graphics.width * 0.5f, graphics.height * 0.5f);

    graphics.circle(0, 0, _minSunRayLength);

    graphics.noFill();

    for(var i = 0; i < sunRayCount; i++)
    {
      var angle = RandomGenerator.Value(Utility.Math.Pi2);

      var x = (float)Math.cos(angle);
      var y = (float)Math.sin(angle);
  
      var length = (float) RandomGenerator.Value(_minSunRayLength, _maxSunRayLength);
      
      var weight = (float) RandomGenerator.Value(_minSunRayWeight, _maxSunRayWeight);
  
      graphics.stroke(255, 255, 255);
      graphics.strokeWeight(weight);
      graphics.line(0f, 0f, x * length, y * length);
    }

    graphics.popMatrix();

    graphics.endDraw();
  }
}
