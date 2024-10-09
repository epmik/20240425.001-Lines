import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.text.DateFormat;

import Utility.Color;
import Utility.ColorPickerConstraint;
import Utility.OpenSimplexSummedNoiseGenerator;
import Utility.RandomGenerator;
import Utility.ColorPicker.ColorScheme;
import Utility.Interfaces.INoiseGenerator;
import Utility.Interfaces.IRandomGenerator;
import processing.core.*;
import processing.event.KeyEvent;

public class Sketch extends PApplet implements ISketch {
  
  public int WindowWidth = 800;
  public int WindowHeight = 800;
  public int SketchWidth = WindowWidth * 4;
  public int SketchHeight = WindowHeight * 4;

  private Gui Gui;

  public static Sketch Instance = null;

  long _millis;

  public IRandomGenerator RandomGenerator;
  public INoiseGenerator NoiseGenerator;

  private int _sunRayCount = 1;//256;

  // ArrayList<Trajectory001> _trajectory001ArrayList = new ArrayList<Trajectory001>();
  ArrayList<AbstractSunRay> _sunRayArrayList = new ArrayList<AbstractSunRay>();
  
  public PGraphics Graphics;

  public SunBackgroundVerticalGradiant Background;
  
  protected String Renderer = PConstants.P2D;

  public ColorPickerConstraint ColorPickerConstraint = new ColorPickerConstraint();

  public Gui Gui()
  {
    return Gui;
  }
  
  public enum RayColorMode {
    StaticRayColor,
    RayColor,
    InvertedBackgroundGradient,
  }
  
  public int StaticRayColor = 0;
  public RayColorMode ColorMode = RayColorMode.RayColor;
  
  public int RayColor(ISunRay sunRay, float time, float x, float y)
  {
    switch (ColorMode) 
    {
      case StaticRayColor: 
      {
        return StaticRayColor;
      }
      case InvertedBackgroundGradient: 
      {
        float t = 1f - (y / Graphics.height);

        return Background.Gradient.Color(t).ToInt();
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
  
  private void SetupColorsAndBackground()
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
  }

  private void SetupRays()
  {
    for(var i = 0; i < _sunRayCount; i++)
    {
      var t = new Trajectory002();
      t.Length(2400);

      var s = i < _sunRayArrayList.size() ? (SunRayNoiseWidth)_sunRayArrayList.get(i) : null;

      if(s == null)
      {
        s = new SunRayNoiseWidth();
        _sunRayArrayList.add(s);
      }

      ((SunRayNoiseWidth)s).NoiseInputMultiplier = 0.001f;
      s.Trajectory(t);
      ((SunRayNoiseWidth)s).MaxWidth = 120f;
      s.Setup();

    }

    SetupRayColors();
  }

  private void SetupRayColors()
  {
    for (var r : _sunRayArrayList) 
    {
      r.Color(Background.Gradient.RandomColor(0.00f, 1f).ToInt());
    }
  }
  
  public void settings() 
  {
      super.settings();

      size(WindowWidth, WindowHeight, Renderer);

      smooth(16);
  }
   
	@Override
  public void setup() 
  {
    super.setup();

    RandomGenerator = new RandomGenerator();
    NoiseGenerator = new OpenSimplexSummedNoiseGenerator();

    frameRate(60);

    Graphics = createGraphics(SketchWidth, SketchHeight, Renderer);
    Graphics.smooth(16);

    Gui = new Gui(this);

    ColorPickerConstraint.MinSaturation = 0.35f;
    ColorPickerConstraint.MaxSaturation = 0.75f;
    ColorPickerConstraint.MinBrightness = 0.75f;
    ColorPickerConstraint.MaxBrightness = 1.00f;

    SetupColorsAndBackground();

    SetupRays();
    
    SetupRayColors();

    StaticRayColor = Background.Gradient.RandomColor().ToInt();

    colorMode(PConstants.RGB, 255, 255, 255, 255);

    hint(PConstants.DISABLE_DEPTH_TEST);

    System.out.println("setup done");

    _millis = millis();
  }
  
  void update(float elapsed)
  {
    RandomGenerator.ReSeed(RandomGenerator.Seed());

    // _trajectory001ArrayList.forEach((t) -> { t.Update(elapsed); });
    _sunRayArrayList.forEach((t) -> { t.Update(elapsed); });

    // _trajectory001.Update(elapsed);
    // _trajectory002.Update(elapsed);
    // _trajectory003.Update(elapsed);
  }
      
	@Override
  public void draw() 
  {
    long millis = millis();
    var elapsed = (millis - _millis) * 0.001f;
    _millis = millis;

    update(elapsed);

    background(255, 255, 255, 0);

    Graphics.beginDraw();
  
    Graphics.background(255, 0, 0, 0);

    Background.Draw(Graphics);
  
    Graphics.pushMatrix();

    Graphics.translate(Graphics.width / 2, Graphics.height / 2);

    //_trajectory001ArrayList.forEach((t) -> { t.Draw(_mainLayer); });
    _sunRayArrayList.forEach((t) -> {
      t.Draw(Graphics);
    });

    Graphics.popMatrix();

    Graphics.endDraw();

    g.image(Graphics, 0, 0, g.width, g.height);
  }

  float clampDegrees(float degrees)
  {
    return degrees < 360 ? degrees : degrees - 360f;
  }
 
  public void mousePressed() 
  {
    // System.out.println("mousePressed");

    // _trajectory001ArrayList.forEach((t) -> { 
    //   t.NoiseGenerator.ReSeed(); 
    //   t.Angle = (float)t.RandomGenerator.Value(Math.PI * 2f);
    // });

    // _sunRay002ArrayList.forEach((t) -> { 
    //   t.NoiseGenerator.ReSeed();
    // });

    SetupRays();

    StaticRayColor = Background.Gradient.RandomColor().ToInt();

    // _trajectory001.NoiseGenerator.ReSeed();
    // _trajectory001.Angle = (float)_trajectory001.RandomGenerator.Value(Math.PI * 2f);

    // _trajectory002.NoiseGenerator.ReSeed();
    // _trajectory002.Angle = (float)_trajectory002.RandomGenerator.Value(Math.PI * 2f);

    // _trajectory003.NoiseGenerator.ReSeed();
    // _trajectory003.Angle = (float)_trajectory003.RandomGenerator.Value(Math.PI * 2f);
  }    
 
  public void keyPressed() 
  {
    // System.out.println("keyPressed");
  }    
 
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
            SetupColorsAndBackground();
            SetupRayColors();
            break;
        case 's':
        case 'S':
          SaveFrame(Graphics);
          break;
          default:
          break;
      }
    }
  }
  
  protected String ClassName() 
  {
    return this.getClass().getSimpleName();
  }

  protected String InputDirectory() 
  {
    return "data" + File.separator + "in";
  }

  protected String OutputDirectory() 
  {
    return "data" + File.separator + "out";
  }

  public static String PathCombine(String... paths) 
  {
    File file = new File(paths[0]);

    for (int i = 1; i < paths.length; i++) 
    {
      file = new File(file, paths[i]);
    }

    return file.getPath();
  }

  public static boolean FileExists(String path) 
  {
    var file = new File(path);

    return file.exists() && !file.isDirectory();
  }

  protected void SaveFrame(PImage canvas) 
  {
    DateFormat dateFormat = new java.text.SimpleDateFormat("yyyyMMdd.HHmmss.SSS");

    SaveFrame(canvas,
      PathCombine(
        OutputDirectory(),
            dateFormat.format(java.util.Calendar.getInstance().getTime()) + "." + ClassName() + ".png")
        );
  }

  protected void SaveFrame(PImage canvas, String path) 
  {
    canvas.save(path);
  }
}