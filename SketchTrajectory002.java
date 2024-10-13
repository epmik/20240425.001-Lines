import java.util.ArrayList;

import Geometry.Vector2;
import Utility.Color;
import Utility.ColorPickerConstraint;
import Utility.ColorPicker.ColorScheme;
import processing.core.*;
import processing.event.KeyEvent;

public class SketchTrajectory002 extends Sketch
{
  private int _sunRay001Count = 128;
  private int _sunRay002Count = 512;

  ArrayList<AbstractSunRay> _sunRay001ArrayList = new ArrayList<AbstractSunRay>();
  ArrayList<AbstractSunRay> _sunRay002ArrayList = new ArrayList<AbstractSunRay>();

  public SunBackgroundVerticalGradiant Background;

  public ColorPickerConstraint ColorPickerConstraint;

  public RadialGradient RadialGradient = new RadialGradient();

  private boolean _visualizeNoise = false;
  
  public enum RayColorMode 
  {
    StaticRayColor,
    RayColor,
    InvertedBackgroundGradient,
    RadialGradient;

    private static final RayColorMode[] _values = values();
    
    public RayColorMode Next() 
    {
        return _values[(ordinal() + 1) % _values.length];
    }    
  }
  
  public int StaticRayColor = 0;
  public RayColorMode ColorMode = RayColorMode.RayColor;
  
  public int RayColor(ISunRay sunRay, float time, float x, float y)
  {
    // var t = this.getClass().getDeclaredConstructor(null).newInstance();

    switch (ColorMode) 
    {
      case StaticRayColor: 
      {
        return StaticRayColor;
      }
      case InvertedBackgroundGradient: 
      {
        float t = 1f - (y / (float)Graphics.height);

        return Background.Gradient.Color(t).ToInt();
      }
      case RadialGradient: 
      {
        var d = new Vector2(x, y).Subtract(new Vector2(Graphics.width * 0.5f, Graphics.height * 0.5f)).Length();

        return RadialGradient.Color(d).ToInt();
      }
      default: 
      {
        return sunRay.Color();
      }
    }
  }
  
  public Color[] PickColors(ColorScheme scheme, int count)
  {
    count = Utility.Math.Clamp(count, 1, Integer.MAX_VALUE);

    if (scheme == ColorScheme.None)
    {
      return Utility.ColorPicker.RandomColors(ColorPickerConstraint, count);
    }

    count = Utility.Math.Clamp(count, 3, 5);

    return Utility.ColorPicker.RandomColors(ColorPickerConstraint, scheme, count);
  }
  
  private void SetupColorsBackgroundAndRadialGradient()
  {
    var count = RandomGenerator.Value(0, 100) < 10 ? 1 : 2;

    var colorScheme = RandomGenerator.Value(0, 100) < 5 ? ColorScheme.None : ColorScheme.Adjacent;

    if (colorScheme != ColorScheme.None) 
    {
      count = RandomGenerator.Value(0, 100) < 50 ? 3 : 5;
    }

    System.out.println("ColorScheme: " + colorScheme + ", " + count + " colors");

    var colors = PickColors(colorScheme, count);

    if (Background == null) 
    {
      Background = new SunBackgroundVerticalGradiant();
    } 
    else 
    {
      Background.Gradient.Clear();
    }

    var step = 1f / (float) colors.length;
    var time = 0f;

    for (var i = 0; i < colors.length; i++) 
    {
      Background.Gradient.Insert(time, colors[i]);

      time += step;
    }

    Background.Gradient.Insert(1f, colors[colors.length - 1]);


    if (RadialGradient == null) 
    {
      RadialGradient = new RadialGradient();
    } 
    else 
    {
      RadialGradient.Clear();
    }

    RadialGradient.Insert(0f, Background.Gradient.RandomColor(0f, 1f));
    RadialGradient.Insert(Graphics.height * 0.5f, Background.Gradient.RandomColor(0f, 1f));
  }

  private void SetupRays()
  {
    for(var i = 0; i < _sunRay001Count; i++)
    {
      var t = new TrajectoryCurvy001();
      t.MinAngleStep = RandomGenerator.Value(0.001f, 0.01f);
      t.MaxAngleStep = RandomGenerator.Value(t.MinAngleStep, 0.025f);
      t.Length(RandomGenerator.Value(1000, 2000));
      t.WalkCount = RandomGenerator.Value(2, 6);
      t.WalkDistanceDeviation = RandomGenerator.Value(0f, 0.25f);

      var s = i < _sunRay001ArrayList.size() ? (SunRayStaticWidth) _sunRay001ArrayList.get(i) : null;

      if (s == null) {
        s = new SunRayStaticWidth();
        _sunRay001ArrayList.add(s);
      }

      // ((SunRayStaticWidth)s).NoiseInputMultiplier = 0.01f;
      s.Trajectory(t);

      var u = (SunRayStaticWidth) s;

      u.Width = (float) RandomGenerator.Value(4, 16);
      u.Angle = (float) RandomGenerator.Value(0f, Math.PI * 2f);

      s.Setup();
    }
    
    for(var i = 0; i < _sunRay002Count; i++)
    {
      var t = new TrajectoryStraight001();
      t.Length(RandomGenerator.Value(400, 800));

      var s = i < _sunRay002ArrayList.size() ? (SunRayStaticWidth)_sunRay002ArrayList.get(i) : null;

      if(s == null)
      {
        s = new SunRayStaticWidth();
        _sunRay002ArrayList.add(s);
      }

      s.Trajectory(t);

      var u = (SunRayStaticWidth) s;

      u.Width = (float)RandomGenerator.Value(4, 16);
      u.Angle = (float) RandomGenerator.Value(0f, Math.PI * 2f);

      s.Setup();
    }

    SetupRayColors();
  }

  private void SetupRayColors()
  {
    for (var r : _sunRay001ArrayList) 
    {
      r.Color(Background.Gradient.RandomColor(0.00f, 1f).ToInt());
    }

    for (var r : _sunRay002ArrayList) 
    {
      r.Color(Background.Gradient.RandomColor(0.00f, 1f).ToInt());
    }
  }
  
  public void settings() 
  {
      super.settings();
  }
   
	@Override
  public void setup() 
  {
    super.setup();

    ColorPickerConstraint = new ColorPickerConstraint();
    ColorPickerConstraint.MinSaturation = 0.45f;
    ColorPickerConstraint.MaxSaturation = 0.85f;
    ColorPickerConstraint.MinBrightness = 0.65f;
    ColorPickerConstraint.MaxBrightness = 0.90f;

    SetupColorsBackgroundAndRadialGradient();

    SetupRays();
    
    SetupRayColors();

    StaticRayColor = Background.Gradient.RandomColor().ToInt();
  }
  
	@Override
  void update(float elapsed)
  {
    super.update(elapsed);
  }
      
	@Override
  public void draw() 
  {
    long millis = millis();
    var elapsed = (millis - _millis) * 0.001f;
    _millis = millis;

    update(elapsed);

    g.background(255, 255, 255, 0);

    if(_visualizeNoise)
    {
      DrawNoise((SunRayEasingWidth)_sunRay001ArrayList.get(0), Graphics);

      g.image(Graphics, 0, 0, g.width, g.height);

      return;
    }

    Graphics.beginDraw();
  
    Graphics.background(255, 0, 0, 0);

    Background.Draw(Graphics);
  
    Graphics.pushMatrix();

    Graphics.translate(Graphics.width / 2, Graphics.height / 2);

    _sunRay002ArrayList.forEach((t) -> {
      t.Draw(Graphics);
    });

    _sunRay001ArrayList.forEach((t) -> {
      t.Draw(Graphics);
    });

    Graphics.popMatrix();

    // ----------

    Graphics.endDraw();

    // ----------

    g.image(Graphics, 0, 0, g.width, g.height);

    // ----------
  }

  private void DrawNoise(SunRayEasingWidth sunRayEasingWidth, PGraphics graphics)
  {
    // var v = 0f;
    // var index = 0;

    // graphics.beginDraw();

    // graphics.loadPixels();

    // for(var y = 0; y < graphics.height; y++)
    // {
    //   for(var x = 0; x < graphics.width; x++)
    //   {
    //     v = Utility.NoiseGenerator.PositiveClamp(sunRayEasingWidth.NoiseGenerator.Value(x * sunRayEasingWidth.NoiseInputMultiplier, y * sunRayEasingWidth.NoiseInputMultiplier));

    //     graphics.pixels[index++] = Color.RgbToInt(v);
    //   }
    // }

    // graphics.updatePixels();

    // graphics.endDraw();
  }
 
	@Override
  public void mousePressed() 
  {
    SetupRays();

    StaticRayColor = Background.Gradient.RandomColor().ToInt();
  }    
 
	@Override
  public void keyPressed() 
  {
  }    
 
	@Override
  public void keyReleased(KeyEvent event) 
  {
    if (event.getKey() == PConstants.CODED) 
    {
      switch (event.getKeyCode()) 
      {
        case PConstants.UP:
          break;
        case PConstants.DOWN:
          break;
        case PConstants.LEFT:
         break;
        case PConstants.RIGHT:
         break;
        case PConstants.TAB:
         _visualizeNoise = !_visualizeNoise;
         break;
      default:
          break;
      }
    }
    else 
    {
      switch (event.getKey()) 
      {
        case PConstants.ENTER:
          NoiseGenerator.ReSeed();
          break;
        case 'c':
        case 'C':
          SetupColorsBackgroundAndRadialGradient();
          SetupRayColors();
          break;
        case 's':
        case 'S':
          SaveFrame(Graphics);
          break;
        case 'n':
        case 'N':
          ColorMode = ColorMode.Next();
          System.out.println("ColorMode: " + ColorMode);
          break;
        default:
          break;
        case PConstants.TAB:
          _visualizeNoise = !_visualizeNoise;
          break;
       }
    }
  }  
}