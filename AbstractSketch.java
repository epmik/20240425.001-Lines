import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.text.DateFormat;

import Utility.Color;
import Utility.ColorPickerConstraint;
import Utility.OpenSimplexNoiseGenerator;
import Utility.OpenSimplexSummedNoiseGenerator;
import Utility.RandomGenerator;
import Utility.ColorPicker.ColorScheme;
import Utility.Interfaces.INoiseGenerator;
import Utility.Interfaces.IRandomGenerator;
import processing.core.*;
import processing.event.KeyEvent;

public abstract class AbstractSketch extends PApplet implements ISketch {
  
  public int WindowWidth = 800;
  public int WindowHeight = 800;
  public int SketchWidth = WindowWidth * 4;
  public int SketchHeight = WindowHeight * 4;
  public long FrameCount = 0;

  private Gui Gui;

  public static AbstractSketch Instance = null;

  long _millis;

  public IRandomGenerator RandomGenerator;
  public IRandomGenerator RandomGeneratorNoResetOnUpdate;
  public INoiseGenerator NoiseGenerator;

  public IBackground Background;

  public PGraphics Graphics;
 
  protected String Renderer = PConstants.P2D;

  public Gui Gui()
  {
    return Gui;
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
    RandomGeneratorNoResetOnUpdate = new RandomGenerator();
    NoiseGenerator = new OpenSimplexNoiseGenerator();

    frameRate(60);

    Graphics = CreateGraphics(SketchWidth, SketchHeight, Renderer);

    Gui = new Gui(this);

    if(Background == null)
    {
      Background = new SolidColorBackground();
    }

    colorMode(PConstants.RGB, 255, 255, 255, 255);

    hint(PConstants.DISABLE_DEPTH_TEST);

    System.out.println("setup done");

    _millis = millis();
  }
  
  void update(float elapsed)
  {
    FrameCount++;

    RandomGenerator.Reset();

    Background.Update(elapsed);
  }
      
	@Override
  public void draw() 
  {
    long millis = millis();
    var elapsed = (millis - _millis) * 0.001f;
    _millis = millis;

    update(elapsed);

    g.background(255, 255, 255, 0);

    g.image(Graphics, 0, 0, g.width, g.height);
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
        case PConstants.TAB:
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

  public PGraphics CreateGraphics(int width, int height)
  {
    return CreateGraphics(width, height, "processing.awt.PGraphicsJava2D");
  }

  public PGraphics CreateGraphics(int width, int height, String renderer)
  {
    var graphics = this.createGraphics(width, height, renderer);
    
    graphics.smooth(16);

    graphics.colorMode(PConstants.RGB, 255, 255, 255, 255);  
    
    return graphics;
  }

  public PGraphics CreateGraphics(PGraphics graphics)
  {
    return CreateGraphics(graphics.width, graphics.height, graphics.getClass().getName());
  }
}