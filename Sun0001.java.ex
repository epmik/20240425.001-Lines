import Utility.RandomGenerator;
import Utility.Interfaces.IRandomGenerator;
import processing.core.*;

public class Sun0001 implements ISun
{
  Sketch _sketch;
  PGraphics _sunLayer;
    PGraphics _sunLayerMask;
    public float _sunRadius;
  
    public IRandomGenerator RandomGenerator;
  
    private long _randomGeneratorSeed;

    protected int c1, c2;

    public Sun0001() 
    {
      super();

      RandomGenerator = new RandomGenerator();
    }

    public void setup(Sketch sketch)
    {
      _sketch = sketch;

      _sunLayer = sketch.createGraphics(_sketch.SketchWidth, _sketch.SketchHeight, _sketch.Renderer);
      _sunLayer.smooth(16);

      _sunLayerMask = sketch.createGraphics(_sketch.SketchWidth, _sketch.SketchHeight, _sketch.Renderer);
      _sunLayerMask.smooth(16);

      _sunRadius = _sketch.SketchWidth / 2;

      _sketch.colorMode(PConstants.RGB, 255, 255, 255, 255);

      c1 = sketch.color(204, 102, 0);
      c2 = sketch.color(0, 102, 153);

      c1 = sketch.color(255, 9, 158);
      c2 = sketch.color(255, 140, 57);

      c1 = sketch.color(255, 54, 0);
      c2 = sketch.color(255, 144, 0);

      c1 = sketch.color(255, 54, 0);
      c1 = sketch.color(255, 9, 158);
    }

    public void update(float elapsed)
    {
      RandomGenerator.ReSeed(RandomGenerator.Seed());
    }

    public void draw()
    {
      drawBackgroundLayer(_sketch._mainLayer);

      _sketch._mainLayer.flush();
  
      drawSunLayer(_sunLayer);
  
      drawSunLayerMask(_sunLayerMask);
  
      _sunLayer.mask(_sunLayerMask);
  
  
      _sunLayer.flush();
  
      _sketch._mainLayer.beginDraw();
  
      _sketch._mainLayer.image(_sunLayer, 0, 0, _sunLayer.width, _sunLayer.height);
      
      _sketch._mainLayer.endDraw();
      }


    void drawSunLayerMask(PGraphics graphics)
    {
      graphics.beginDraw();
  
      graphics.circle(graphics.width / 2, graphics.height / 2, _sunRadius);
  
      graphics.endDraw();
    }
    
    void drawSunLayer(PGraphics graphics)
    {
      graphics.beginDraw();
  
      graphics.background(255, 255, 255, 0);
  
      graphics.pushMatrix();
  
      graphics.mask(_sunLayerMask);
  
      _sketch.drawGradient(0, 0, graphics.width, graphics.height, c1, c2, _sketch.Y_AXIS, graphics);
  
      graphics.popMatrix();
  
      graphics.endDraw();
    }
    
    void drawBackgroundLayer(PGraphics graphics)
    {
      graphics.beginDraw();
  
      graphics.background(255, 255, 255, 0);
  
      graphics.pushMatrix();
  
      graphics.mask(_sunLayerMask);
  
      _sketch.drawGradient(0, 0, graphics.width, graphics.height, c2, c1, _sketch.Y_AXIS, graphics);
  
      graphics.popMatrix();
  
      graphics.endDraw();
    }
  
  }
