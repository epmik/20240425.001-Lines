import java.util.ArrayList;
import Utility.Color;
import Utility.ColorPickerConstraint;
import Utility.OpenSimplexNoiseGenerator;
import Utility.ColorPicker.ColorScheme;
import Utility.Interfaces.INoiseGenerator;
import processing.core.*;
import processing.event.KeyEvent;

public class Sketch002 extends Sketch
{

  private boolean _drawBackground = true;

  private int _sunRayIndex = 0;//128;
  private int _sunRayCount = 2048;

  private int _sunRayDrawCount = 128;

  ArrayList<AbstractSunRay> _sunRayArrayList = null;
  ArrayList<AbstractSunRay> _sunRayDrawArrayList = new ArrayList<>();

  WeightedSunRayCreator _weightedSunRayCreator = new WeightedSunRayCreator();

  public SunBackgroundVerticalGradiant Background;

  public ColorPickerConstraint ColorPickerConstraint;

  public RadialGradient RadialGradient = new RadialGradient();
  private RadialGradient _radialBurstGradient = new RadialGradient();
  private RadialGradient _transparentRadialBurstGradient = new RadialGradient();

  private boolean _visualizeNoise = false;

  private INoiseGenerator _trajectoryNoiseGenerator;
  
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
  
  private RayColorMode ColorMode = RayColorMode.RayColor;
  
  private Color[] PickColors(ColorScheme scheme, int count)
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

    if (_radialBurstGradient == null) 
    {
      _radialBurstGradient = new RadialGradient();
    } 
    else 
    {
      _radialBurstGradient.Clear();
    }

    _radialBurstGradient.Insert(1f, Background.Gradient.RandomColor(0f, 1f));
    _radialBurstGradient.Insert(0f, _radialBurstGradient.ColorAt(1f).Brighter(0.2f, 0.98f));

    if (_transparentRadialBurstGradient == null) 
    {
      _transparentRadialBurstGradient = new RadialGradient();
    } 
    else 
    {
      _transparentRadialBurstGradient.Clear();
    }

    _transparentRadialBurstGradient.Insert(0f, _radialBurstGradient.ColorAt(1f).Brighter(0.2f, 0.98f));
    _transparentRadialBurstGradient.Insert(1f,  _transparentRadialBurstGradient.ColorAt(0f).A(0f));

    // var t = _transparentRadialBurstGradient.ColorAt(0.5f);
  }

  private AbstractSunRay CreateCurvySunRay(ISunRayProviderOptions options)
  {
    var o = (CurvySunRayProviderOptions)options;

    var s = new SunRayStaticWidth();
    var t = new TrajectoryCurvy001(o.TrajectoryRandomGenerator, o.TrajectoryNoiseGenerator);

    s.Trajectory(t);

    t.MinAngleStep = RandomGeneratorNoResetOnUpdate.Value(o.MinMinAngleStep, o.MaxMinAngleStep);
    t.MaxAngleStep = RandomGeneratorNoResetOnUpdate.Value(o.MinMaxAngleStep, o.MaxMaxAngleStep);
    t.Length(RandomGeneratorNoResetOnUpdate.Value(o.MinLength, o.MaxLength));
    t.WalkCount = RandomGeneratorNoResetOnUpdate.Value(o.MinWalkCount, o.MaxWalkCount);
    t.WalkDistanceDeviation = RandomGeneratorNoResetOnUpdate.Value(o.MinWalkDistanceDeviation, o.MaxWalkDistanceDeviation);

    s.Width = (float)RandomGeneratorNoResetOnUpdate.Value(o.MinWidth, o.MaxWidth);
    s.Angle(RandomGeneratorNoResetOnUpdate.Value(o.MinAngle, o.MaxAngle));

    s.ColorProvider(o.ColorProvider);

    if(o.ColorSupplier !=  null)
    {
      s.Color(o.ColorSupplier.get());
    }

    s.Setup();

    return s;
  }

  private AbstractSunRay CreateRadialBurstSunRay(ISunRayProviderOptions options)
  {
    var o = (RadialBurstSunRayProviderOptions)options;

    var s = new SunRayStaticWidth();
    var t = new TrajectoryStraight001();

    s.Trajectory(t);

    t.Length(RandomGeneratorNoResetOnUpdate.Value(o.MinLength, o.MaxLength));

    s.Width = (float)RandomGeneratorNoResetOnUpdate.Value(o.MinWidth, o.MaxWidth);
    s.Angle(RandomGeneratorNoResetOnUpdate.Value(o.MinAngle, o.MaxAngle));

    s.ColorProvider(o.ColorProvider);

    if(o.ColorSupplier !=  null)
    {
      s.Color(o.ColorSupplier.get());
    }
    
    s.Setup();

    return s;  
  }

  private void SetupRays()
  {
    // _weightedSunRayCreator.Add(100, () -> CreateCurvySunRay(new CurvySunRayProviderOptions() { { MaxWidth = 2f; MinLength = 1600f; MaxLength = 3200f; ColorProvider = _radialBurstGradient; ColorSupplier = () -> Background.Gradient.RandomColor(0.00f, 1f); }}));
    // _weightedSunRayCreator.Add(100, () -> CreateCurvySunRay(new CurvySunRayProviderOptions() { { MaxWidth = 2f; MinLength = 1200f; MaxLength = 2400f; ColorProvider = _radialBurstGradient; ColorSupplier = () -> Background.Gradient.RandomColor(0.00f, 1f); }}));
    // _weightedSunRayCreator.Add(100, () -> CreateCurvySunRay(new CurvySunRayProviderOptions() { { MaxWidth = 2f; MinLength = 800f; MaxLength = 1600f; ColorProvider = _radialBurstGradient; ColorSupplier = () -> Background.Gradient.RandomColor(0.00f, 1f); }}));
    // _weightedSunRayCreator.Add(200, () -> CreateCurvySunRay(new CurvySunRayProviderOptions() { { MaxWidth = 2f; MinLength = 600f; MaxLength = 1200f; ColorProvider = _radialBurstGradient; ColorSupplier = () -> Background.Gradient.RandomColor(0.00f, 1f); }}));
    // _weightedSunRayCreator.Add(400, () -> CreateCurvySunRay(new CurvySunRayProviderOptions() { { MaxWidth = 4f; MinLength = 400f; MaxLength = 800f; ColorProvider = _radialBurstGradient; ColorSupplier = () -> Background.Gradient.RandomColor(0.00f, 1f); }}));
    
    // white to transparent
    // _weightedSunRayCreator.Add(400, () -> CreateCurvySunRay(new CurvySunRayProviderOptions() { { MaxWidth = 2f; MinLength = 400f; MaxLength = 1200f; ColorProvider = _transparentRadialBurstGradient; }}));
    // _weightedSunRayCreator.Add(400, () -> CreateRadialBurstSunRay(new RadialBurstSunRayProviderOptions() { { MaxWidth = 2f; MinLength = 400f; MaxLength = 800f; ColorProvider = _transparentRadialBurstGradient; }}));

    _weightedSunRayCreator.Add(400, () -> CreateCurvySunRay(new CurvySunRayProviderOptions() { { MaxWidth = 4f; MinLength = 1600f; MaxLength = 3200f; ColorProvider = _transparentRadialBurstGradient; }}));
    
    // _weightedSunRayCreator.Add(1000, () -> CreateRadialBurstSunRay(new RadialBurstSunRayProviderOptions() { { MinWidth = 4f; MaxWidth = 16f; MinLength = 400f; MaxLength = 1600f; ColorProvider = _transparentRadialBurstGradient; }}));
    // _weightedSunRayCreator.Add(1000, () -> CreateRadialBurstSunRay(new RadialBurstSunRayProviderOptions() { { MaxWidth = 4f; MinLength = 400f; MaxLength = 800f; ColorSupplier = () -> Background.Gradient.RandomColor(0.00f, 1f); }}));
    // _weightedSunRayCreator.Add(1000, () -> CreateRadialBurstSunRay(new RadialBurstSunRayProviderOptions() { { MinLength = 400f; MaxLength = 480f; ColorProvider = _radialBurstGradient; }}));
    // _weightedSunRayCreator.Add(250, () -> CreateRadialBurstSunRay(new RadialBurstSunRayProviderOptions() { { MinLength = 400f; MaxLength = 640f; ColorProvider = _radialBurstGradient; }}));
    // _weightedSunRayCreator.Add(100, () -> CreateRadialBurstSunRay(new RadialBurstSunRayProviderOptions() { { MinLength = 600f; MaxLength = 880f; ColorProvider = _radialBurstGradient; }}));

    if(_sunRayArrayList == null)
    {
      _sunRayArrayList = new ArrayList<>();

      for(var i = 0; i < _sunRayCount; i++)
      {
        _sunRayArrayList.add(_weightedSunRayCreator.Next());
      }
    }

    // SetupRayColors();
  }

  // private void SetupRayColors()
  // {
  //   for (var r : _sunRayArrayList) 
  //   {
  //     r.Color(Background.Gradient.RandomColor(0.00f, 1f));
  //   }
  // }
  
  public void settings() 
  {
      super.settings();
  }
   
	@Override
  public void setup() 
  {
    super.setup();

    _trajectoryNoiseGenerator = new OpenSimplexNoiseGenerator();

    ColorPickerConstraint = new ColorPickerConstraint();
    ColorPickerConstraint.MinSaturation = 0.45f;
    ColorPickerConstraint.MaxSaturation = 0.85f;
    ColorPickerConstraint.MinBrightness = 0.65f;
    ColorPickerConstraint.MaxBrightness = 0.90f;



    SetupColorsBackgroundAndRadialGradient();

    SetupRays();
    
    // SetupRayColors();

    ResetDrawState();
  }

  private void ResetDrawState()
  {
    _drawBackground = true;
    
    _sunRayIndex = 0;
    // _sunRayDrawIndex = 0;

    _sunRayDrawArrayList.clear();

    _sunRayArrayList.forEach((t) -> 
    {
      t.ResetDrawState();
    });  
  }
  
	@Override
  void update(float elapsed)
  {
    super.update(elapsed);

    _sunRayDrawArrayList.removeIf(s -> !s.IsAlive());

    if (_sunRayIndex < _sunRayCount && _sunRayDrawArrayList.size() < _sunRayDrawCount)
    {
      for (var i = _sunRayDrawArrayList.size(); i < _sunRayDrawCount; i++) 
      {
        _sunRayDrawArrayList.add(_sunRayArrayList.get(_sunRayIndex++));
      }
    }
    
    _sunRayDrawArrayList.forEach(s -> s.Update(elapsed));
  }
      
	@Override
  public void draw() 
  {
    long millis = millis();
    var elapsed = (millis - _millis) * 0.001f;
    _millis = millis;

    update(elapsed);

    g.background(255, 255, 255, 0);

    // if(_visualizeNoise)
    // {
    //   DrawNoise((SunRayEasingWidth)_sunRay001ArrayList.get(0), Graphics);

    //   g.image(Graphics, 0, 0, g.width, g.height);

    //   return;
    // }

    Graphics.beginDraw();

    if(_drawBackground)
    {
      Graphics.background(255, 0, 0, 0);

      Background.Draw(Graphics);
  
      _drawBackground = false;
    }  

    Graphics.pushMatrix();

    Graphics.translate(Graphics.width / 2, Graphics.height / 2);

    _sunRayDrawArrayList.forEach(s -> s.Draw(Graphics));

    Graphics.popMatrix();

    // ----------

    // Graphics.pushMatrix();

    // Graphics.translate(Graphics.width / 2, Graphics.height / 2);

    // Graphics.strokeWeight(64);
    // Graphics.stroke(_transparentRadialBurstGradient.ColorAt(0.5f).ToInt());
    // Graphics.line(0, 0, 800, 800);

    // Graphics.strokeWeight(64);
    // Graphics.stroke(_transparentRadialBurstGradient.ColorAt(0.0f).ToInt());
    // Graphics.line(-800, 0, 0, 800);

    // Graphics.popMatrix();

    // ----------

    Graphics.endDraw();

    // ----------

    g.image(Graphics, 0, 0, g.width, g.height);

    // ----------
  }

  // private void DrawNoise(SunRayEasingWidth sunRayEasingWidth, PGraphics graphics)
  // {
  //   // var v = 0f;
  //   // var index = 0;

  //   // graphics.beginDraw();

  //   // graphics.loadPixels();

  //   // for(var y = 0; y < graphics.height; y++)
  //   // {
  //   //   for(var x = 0; x < graphics.width; x++)
  //   //   {
  //   //     v = Utility.NoiseGenerator.PositiveClamp(sunRayEasingWidth.NoiseGenerator.Value(x * sunRayEasingWidth.NoiseInputMultiplier, y * sunRayEasingWidth.NoiseInputMultiplier));

  //   //     graphics.pixels[index++] = Color.RgbToInt(v);
  //   //   }
  //   // }

  //   // graphics.updatePixels();

  //   // graphics.endDraw();
  // }
 
	@Override
  public void mousePressed() 
  {
    SetupRays();

    ResetDrawState();
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
          ResetDrawState();
          break;
        case 'c':
        case 'C':
          SetupColorsBackgroundAndRadialGradient();
          // SetupRayColors();
          ResetDrawState();
          break;
        case 's':
        case 'S':
          SaveFrame(Graphics);
          break;
        case 'n':
        case 'N':
          ColorMode = ColorMode.Next();
          ResetDrawState();
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