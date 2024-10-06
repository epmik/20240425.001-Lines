import Utility.OpenSimplexSummedNoiseGenerator;
import Utility.Interfaces.INoiseGenerator;
import processing.core.*;

public class Sun0003 extends Sun0001
{
  public int sunRayCount = 400;
  public float _minSunRayLength= 100;
  public float _maxSunRayLength = 200;
  public float _minSunRayWeight = 20;
  public float _maxSunRayWeight = 80;
  public float _angleDeviationMinMin = -4;
  public float _angleDeviationMinMax = -25;
  public float _angleDeviationMaxMin = 4;
  public float _angleDeviationMaxMax = 25;
  public INoiseGenerator NoiseGenerator;

  @Override
  public void setup(Sketch sketch)
  {
    super.setup(sketch);

    NoiseGenerator = new OpenSimplexSummedNoiseGenerator();

    _minSunRayLength = (_sketch.SketchWidth - 200) / 6;
    _maxSunRayLength = (_sketch.SketchWidth - 200) / 4;
  }
  
  @Override
  void drawSunLayerMask(PGraphics graphics)
  {
    graphics.beginDraw();

    graphics.pushMatrix();

    graphics.translate(graphics.width * 0.5f, graphics.height * 0.5f);

    // graphics.circle(0, 0, _minSunRayLength);

    graphics.noFill();

    graphics.stroke(255, 255, 255);
    graphics.strokeWeight(20);
    graphics.line(0f, 0f, 400, 400);

    var angle = RandomGenerator.Value(Utility.Math.Pi2);
    var angleDeviationMin = RandomGenerator.Value(Math.toRadians(_angleDeviationMinMin), Math.toRadians(_angleDeviationMinMax));
    var angleDeviationMax = RandomGenerator.Value(Math.toRadians(_angleDeviationMaxMin), Math.toRadians(_angleDeviationMaxMax));
    
    var steps = 200;
    var stepsMultiplier = 0.00125f;

    var x = 0f;
    var y = 0f;

    for (var i = 0; i < steps; i++)
    {
      

      graphics.line(x, x, 400, 400);


    }





    graphics.popMatrix();

    graphics.endDraw();
  }
 
  @Override
  void drawSunLayer(PGraphics graphics)
  {
    graphics.beginDraw();

    graphics.background(255, 255, 255, 0);

    graphics.pushMatrix();

    graphics.mask(_sunLayerMask);

    graphics.rect(0, 0, graphics.width, graphics.height);

    // _sketch.drawGradient(0, 0, graphics.width, graphics.height, c1, c2, _sketch.Y_AXIS, graphics);

    graphics.popMatrix();

    graphics.endDraw();
  }}
