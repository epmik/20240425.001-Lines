package Utility;

import Utility.Interfaces.IRandomGenerator;
import processing.core.PApplet;

public class Color
{
  private static final double ByteToDoubleFactor = 1.0 / 255.0;
  private static final double DegreesToDoubleFactor = 1.0 / 360.0;
  private static final double PercentageToDoubleFactor = 1.0 / 100.0;
  
  // private static IRandomGenerator RandomGenerator = new RandomGenerator();
  // private static ColorPicker DefaultColorPicker = new ColorPicker();

  private float _r = 1f;
  private float _g = 1f;
  private float _b = 1f;
  private float _a = 1f;
  private float _hue;
  private float _saturation;
  private float _brightness;

  public Color() 
  {
    super();
  }

  public Color(float r, float g, float b) 
  {
    super();

    this.Rgb(r, g, b);
  }

  public Color(float r, float g, float b, float a) 
  {
    super();

    this.Rgb(r, g, b, a);
  }

  public static int RInt(int color)
  {
    return color >> 16 & 0xff;
  }

  public static int GInt(int color)
  {
    return color >> 8 & 0xff;
  }

  public static int BInt(int color)
  {
    return color & 0xff;
  }

  public static int AInt(int color)
  {
    return color >> 24 & 0xff;
  }

  public static Color FromInt(int color) 
  {
    return FromRgb(color >> 16 & 0xff, color >> 8 & 0xff, color & 0xff, color >> 24 & 0xff);
  }

  public static Color FromRgb(int r, int g, int b) 
  {
    return FromRgb(r, g, b, 255);
  }

  public static Color FromRgb(int r, int g, int b, int a) 
  {
    return FromRgb((float)(r * ByteToDoubleFactor), (float)(g * ByteToDoubleFactor), (float)(b * ByteToDoubleFactor), (float)(a * ByteToDoubleFactor));
  }

  public static Color FromRgb(float r, float g, float b) 
  {
    return FromRgb(r, g, b, 1f);
  }

  public static Color FromRgb(float r, float g, float b, float a) 
  {
    return new Color().Rgb(r, g, b, a);
  }

  /**
   * @param     h   the hue component of the color, between 0 and 360
   * @param     s   the saturation component of the color, between 0 and 100
   * @param     b   the brightness component of the color, between 0 and 100
   * @return    a Color class object
   */
  public static Color FromHsb(int h, int s, int b) 
  {
    return FromHsb(h, s, b, 100);
  }

  /**
   * @param     h   the hue component of the color, between 0 and 360
   * @param     s   the saturation component of the color, between 0 and 100
   * @param     b   the brightness component of the color, between 0 and 100
   * @param     a   the alpha component of the color, between 0 and 100
   * @return    a Color class object
   */
  public static Color FromHsb(int h, int s, int b, int a) 
  {
    return FromHsb(
      (float) (h * DegreesToDoubleFactor), 
      (float) (s * PercentageToDoubleFactor), 
      (float) (b * PercentageToDoubleFactor),
      (float) (a * PercentageToDoubleFactor));
  }
  
  /**
   * @param     h   the hue component of the color, between 0 and 1f
   * @param     s   the saturation component of the color, between 0 and 1f
   * @param     b   the brightness component of the color, between 0 and 1f
   * @return    a Color class object
   */
  public static Color FromHsb(float h, float s, float b) 
  {
    return FromHsb(h, s, b, 1f);
  }

  /**
   * @param     h   the hue component of the color, between 0 and 1f
   * @param     s   the saturation component of the color, between 0 and 1f
   * @param     b   the brightness component of the color, between 0 and 1f
   * @param     a   the alpha component of the color, between 0f and 1f
   * @return    a Color class object
   */
  public static Color FromHsb(float h, float s, float b, float a) 
  {
    return new Color().Hsb(h, s, b, a);
  }

  public float R()
  {
    return _r;
  }

  public float G()
  {
    return _g;
  }

  public float B()
  {
    return _b;
  }

  public float A()
  {
    return _a;
  }

  public Color R(float r)
  {
    _r = Utility.Math.Clamp(r);
    return CalculateHsb(); 
  }

  public Color G(float g)
  {
    _g = Utility.Math.Clamp(g);
    return CalculateHsb(); 
  }

  public Color B(float b)
  {
    _b = Utility.Math.Clamp(b);
    return CalculateHsb(); 
  }

  public Color A(float a)
  {
    _a = Utility.Math.Clamp(a);
    return CalculateHsb(); 
  }

  public Color Rgb(int r, int g, int b)
  {
    return Rgb(r, g, b, 255); 
  }

  public Color Rgb(int r, int g, int b, int a)
  {
    return Rgb((float)(r * ByteToDoubleFactor), (float)(g * ByteToDoubleFactor), (float)(b * ByteToDoubleFactor), (float)(a * ByteToDoubleFactor));
  }

  public Color Rgb(float r, float g, float b)
  {
    return Rgb(r, g, b, 1f); 
  }

  public Color Rgb(float r, float g, float b, float a)
  {
    _r = Utility.Math.Clamp(r);
    _g = Utility.Math.Clamp(g);
    _b = Utility.Math.Clamp(b);
    _a = Utility.Math.Clamp(a);
    return CalculateHsb(); 
  }

  public float Hue()
  {
    return _hue;
  }

  public int HueInt()
  {
    return (int) (_hue * 360f);
  }

  public float Saturation()
  {
    return _saturation;
  }

  public int SaturationInt()
  {
    return (int) (_saturation * 100f);
  }

  public float Brightness()
  {
    return _brightness;
  }

  public int BrightnessInt()
  {
    return (int) (_brightness * 100f);
  }

  public Color Hue(float h)
  {
    _hue = Wrap(h);
    return CalculateRgb();
  }

  public Color Saturation(float s)
  {
    _saturation = Utility.Math.Clamp(s);
    return CalculateRgb();
  }

  public Color Brightness(float b)
  {
    _brightness = Utility.Math.Clamp(b);
    return CalculateRgb();
  }

  public Color Hsb(float h, float s, float b)
  {
    return Hsb(h, s, b, 1f);
  }
  
  public Color Hsb(float h, float s, float b, float a)
  {
    _hue = Wrap(h);
    _saturation = Utility.Math.Clamp(s);
    _brightness = Utility.Math.Clamp(b);
    _a = Utility.Math.Clamp(a);
    return CalculateRgb();
  }
  
  public int ToInt()
  {
    return (AInt() << 24 | RInt() << 16 | GInt() << 8 | BInt());
  }

  public int RInt()
  {
    return (int) (_r * 255f);
  }

  public int GInt()
  {
    return (int) (_g * 255f);
  }

  public int BInt()
  {
    return (int) (_b * 255f);
  }

  public int AInt()
  {
    return (int) (_a * 255f);
  }

  static public Color Interpolate(Color c1, Color c2, float time) 
  {
    time = Utility.Math.Clamp(time);

    float a1 = (float) c1.AInt();// ((c1 >> 24) & 0xff);
    float r1 = (float) c1.RInt();// (c1 >> 16) & 0xff;
    float g1 = (float) c1.GInt();// (c1 >> 8) & 0xff;
    float b1 = (float) c1.BInt();// c1 & 0xff;
    float a2 = (float) c2.AInt();// (c2 >> 24) & 0xff;
    float r2 = (float) c2.RInt();// (c2 >> 16) & 0xff;
    float g2 = (float) c2.GInt();// (c2 >> 8) & 0xff;
    float b2 = (float) c2.BInt();// c2 & 0xff;

    return new Color().Rgb(
        (int) (java.lang.Math.round(r1 + (r2 - r1) * time)),
        (int) (java.lang.Math.round(g1 + (g2 - g1) * time)),
        (int) (java.lang.Math.round(b1 + (b2 - b1) * time)),
        (int) (java.lang.Math.round(a1 + (a2 - a1) * time)));

    // return ((Math.round(a1 + (a2-a1)*time) << 24) |
    // (Math.round(r1 + (r2-r1)*time) << 16) |
    // (Math.round(g1 + (g2-g1)*time) << 8) |
    // (Math.round(b1 + (b2-b1)*time)));
  }
  
  static public int Interpolate(int c1, int c2, float time) 
  {
    time = Utility.Math.Clamp(time);

    float a1 = ((c1 >> 24) & 0xff);
    float r1 = (c1 >> 16) & 0xff;
    float g1 = (c1 >> 8) & 0xff;
    float b1 = c1 & 0xff;
    float a2 = (c2 >> 24) & 0xff;
    float r2 = (c2 >> 16) & 0xff;
    float g2 = (c2 >> 8) & 0xff;
    float b2 = c2 & 0xff;

    return ((PApplet.round(a1 + (a2-a1)*time) << 24) |
            (PApplet.round(r1 + (r2-r1)*time) << 16) |
            (PApplet.round(g1 + (g2-g1)*time) << 8) |
            (PApplet.round(b1 + (b2-b1)*time)));
  }  
 
  private Color CalculateHsb() 
  {
    // copied from
    // https://hg.openjdk.org/jdk8/jdk8/jdk/file/687fd7c7986d/src/share/classes/java/awt/Color.java#l907

    int r = RInt();
    int g = GInt();
    int b = BInt();

    int cmax = (r > g) ? r : g;
    if (b > cmax)
      cmax = b;
    int cmin = (r < g) ? r : g;
    if (b < cmin)
      cmin = b;

    _brightness = cmax / 255.0f;
    if (cmax != 0)
      _saturation = (cmax - cmin) / cmax;
    else
    _saturation = 0;
    if (_saturation == 0)
      _hue = 0;
    else {
      float redc = ((cmax - r)) / ((cmax - cmin));
      float greenc = ((cmax - g)) / ((cmax - cmin));
      float bluec = ((cmax - b)) / ((cmax - cmin));
      if (r == cmax)
        _hue = (bluec - greenc);
      else if (g == cmax)
        _hue = (2.0f + redc - bluec);
      else
        _hue = (4.0f + greenc - redc);
      _hue = _hue / 6.0f;
      if (_hue < 0)
        _hue = _hue + 1.0f;
    }
    return this;
  }
 
  private Color CalculateRgb() 
  {
    // copied from
    // https://hg.openjdk.org/jdk8/jdk8/jdk/file/687fd7c7986d/src/share/classes/java/awt/Color.java#l839

    int r = 0, g = 0, b = 0;
    
    if (_saturation == 0) 
    {
      r = g = b = (int) (_brightness * 255.0f + 0.5f);
    } 
    else 
    {
      float h = (_hue - (float) java.lang.Math.floor(_hue)) * 6.0f;
      float f = h - (float) java.lang.Math.floor(h);
      float p = _brightness * (1.0f - _saturation);
      float q = _brightness * (1.0f - _saturation * f);
      float t = _brightness * (1.0f - (_saturation * (1.0f - f)));
      switch ((int) h) {
        case 0:
          r = (int) (_brightness * 255.0f + 0.5f);
          g = (int) (t * 255.0f + 0.5f);
          b = (int) (p * 255.0f + 0.5f);
          break;
        case 1:
          r = (int) (q * 255.0f + 0.5f);
          g = (int) (_brightness * 255.0f + 0.5f);
          b = (int) (p * 255.0f + 0.5f);
          break;
        case 2:
          r = (int) (p * 255.0f + 0.5f);
          g = (int) (_brightness * 255.0f + 0.5f);
          b = (int) (t * 255.0f + 0.5f);
          break;
        case 3:
          r = (int) (p * 255.0f + 0.5f);
          g = (int) (q * 255.0f + 0.5f);
          b = (int) (_brightness * 255.0f + 0.5f);
          break;
        case 4:
          r = (int) (t * 255.0f + 0.5f);
          g = (int) (p * 255.0f + 0.5f);
          b = (int) (_brightness * 255.0f + 0.5f);
          break;
        case 5:
          r = (int) (_brightness * 255.0f + 0.5f);
          g = (int) (p * 255.0f + 0.5f);
          b = (int) (q * 255.0f + 0.5f);
          break;
      }
    }

    _r = (float)Clamp(r * ByteToDoubleFactor);
    _g = (float)Clamp(g * ByteToDoubleFactor);
    _b = (float)Clamp(b * ByteToDoubleFactor);
    
    return this;
  }
    
  // public static float Clamp(float v)
  // {
  //   return Math.Min(Math.Max(0, v), 1);
  // }
 
  public static float Wrap(float v)
  {
    if (v < 0f) {
      return Wrap(v + 1f);
    }
    if (v > 1f) {
      return Wrap(v - 1f);
    }
    return v;
  }
  













  

  // public static double Red(int color) 
  // {
  //   return (color >> 16 & 0xff) / 255.0;
  // }

  // public static double Green(int color) 
  // {
  //   return (color >> 8 & 0xff) / 255.0;
  // }

  // public static double Blue(int color) 
  // {
  //   return (color & 0xff) / 255.0;
  // }
 
  // public static double Hue(int color) 
  // {
  //   var hsb = IntToHsb(color);

  //   return hsb[0];
  // }
  
  // public static double Saturation(int color) 
  // {
  //   var hsb = IntToHsb(color);

  //   return hsb[1];
  // }
  
  // public static double Brightness(int color) 
  // {
  //   var hsb = IntToHsb(color);

  //   return hsb[2];
  // }
  
  // public static double Alpha(int color) 
  // {
  //   return (color >> 24 & 0xff) / 255.0;
  // }
  
  // public static double[] IntToHsb(int color) 
  // {
  //   return RgbToHsb(IntToRgb(color));
  // }
  
    // /**
    //  * Converts the components of a color, as specified by the HSB
    //  * model, to an equivalent set of values for the default RGB model.
    //  * <p>
    //  * The <code>saturation</code> and <code>brightness</code> components
    //  * should be floating-point values between zero and one
    //  * (numbers in the range 0.0-1.0).  The <code>hue</code> component
    //  * can be any floating-point number.  The floor of this number is
    //  * subtracted from it to create a fraction between 0 and 1.  This
    //  * fractional number is then multiplied by 360 to produce the hue
    //  * angle in the HSB color model.
    //  * <p>
    //  * The integer that is returned by <code>HSBtoRGB</code> encodes the
    //  * value of a color in bits 0-23 of an integer value that is the same
    //  * format used by the method {@link #getRGB() getRGB}.
    //  * This integer can be supplied as an argument to the
    //  * <code>Color</code> constructor that takes a single integer argument.
    //  * @param     hue   the hue component of the color
    //  * @param     saturation   the saturation of the color
    //  * @param     brightness   the brightness of the color
    //  * @return    the RGB value of the color with the indicated hue,
    //  *                            saturation, and brightness.
    //  */
    // public static int HsbToInt(double hue, double saturation, double brightness) 
    // {
    //   return HsbToInt(hue, saturation, brightness, 1.0);
    // }

    /**
     * Converts the components of a color, as specified by the HSB
     * model, to an equivalent set of values for the default RGB model.
     * <p>
     * The <code>saturation</code> and <code>brightness</code> components
     * should be floating-point values between zero and one
     * (numbers in the range 0.0-1.0).  The <code>hue</code> component
     * can be any floating-point number.  The floor of this number is
     * subtracted from it to create a fraction between 0 and 1.  This
     * fractional number is then multiplied by 360 to produce the hue
     * angle in the HSB color model.
     * <p>
     * The integer that is returned by <code>HSBtoRGB</code> encodes the
     * value of a color in bits 0-23 of an integer value that is the same
     * format used by the method {@link #getRGB() getRGB}.
     * This integer can be supplied as an argument to the
     * <code>Color</code> constructor that takes a single integer argument.
     * @param     hue   the hue component of the color
     * @param     saturation   the saturation of the color
     * @param     brightness   the brightness of the color
     * @param     alpha   the alpha value of the color
     * @return    the RGB value of the color with the indicated hue,
     *                            saturation, and brightness.
     * @see       java.awt.Color#getRGB()
     * @see       java.awt.Color#Color(int)
     * @see       java.awt.image.ColorModel#getRGBdefault()
     * @since     JDK1.0
     */
    public static int HsbToInt(double hue, double saturation, double brightness, double alpha) 
    {
      // mostly copied from
      // http://hg.openjdk.java.net/jdk8/jdk8/jdk/file/687fd7c7986d/src/share/classes/java/awt/Color.java

      int r = 0, g = 0, b = 0, a = (int) (alpha * 255);

      if (saturation == 0) {
        r = g = b = (int) (brightness * 255.0 + 0.5);
      } else {
        double h = (hue - java.lang.Math.floor(hue)) * 6.0;
        double f = h - java.lang.Math.floor(h);
        double p = brightness * (1.0f - saturation);
        double q = brightness * (1.0f - saturation * f);
        double t = brightness * (1.0f - (saturation * (1.0f - f)));
        switch ((int) h) {
          case 0:
            r = (int) (brightness * 255.0f + 0.5f);
            g = (int) (t * 255.0f + 0.5f);
            b = (int) (p * 255.0f + 0.5f);
            break;
          case 1:
            r = (int) (q * 255.0f + 0.5f);
            g = (int) (brightness * 255.0f + 0.5f);
            b = (int) (p * 255.0f + 0.5f);
            break;
          case 2:
            r = (int) (p * 255.0f + 0.5f);
            g = (int) (brightness * 255.0f + 0.5f);
            b = (int) (t * 255.0f + 0.5f);
            break;
          case 3:
            r = (int) (p * 255.0f + 0.5f);
            g = (int) (q * 255.0f + 0.5f);
            b = (int) (brightness * 255.0f + 0.5f);
            break;
          case 4:
            r = (int) (t * 255.0f + 0.5f);
            g = (int) (p * 255.0f + 0.5f);
            b = (int) (brightness * 255.0f + 0.5f);
            break;
          case 5:
            r = (int) (brightness * 255.0f + 0.5f);
            g = (int) (p * 255.0f + 0.5f);
            b = (int) (q * 255.0f + 0.5f);
            break;
        }
      }
      return RgbToInt(r, g, b, a);
    }
    
    public static double[] RgbToHsb(int [] rgba) 
    {
      return RgbToHsb(rgba[0], rgba[1], rgba[2], rgba[3]);
    }
    
    public static double[] RgbToHsb(float [] rgba) 
    {
      return RgbToHsb(rgba[0], rgba[1], rgba[2], rgba[3]);
    }
    
    public static double[] RgbToHsb(double [] rgba) 
    {
      return RgbToHsb(rgba[0], rgba[1], rgba[2], rgba[3]);
    }
  
    public static double[] RgbToHsb(int r, int g, int b)
    {
      return RgbToHsb(r, g, b, 255);
    }

    public static double[] RgbToHsb(float r, float g, float b)
    {
      return RgbToHsb(r, g, b, 1.0f);
    }

    public static double[] RgbToHsb(double r, double g, double b)
    {
      return RgbToHsb(r, g, b, 1.0);
    }

    public static double[] RgbToHsb(float r, float g, float b, float a)
    {
      return RgbToHsb((int) (r * 255.0f), (int) (g * 255.0f), (int) (b * 255.0f), (int) (a * 255.0f));
    }

    public static double[] RgbToHsb(double r, double g, double b, double a)
    {
      return RgbToHsb((int) (r * 255.0), (int) (g * 255.0), (int) (b * 255.0), (int) (a * 255.0));
    }
  
  public static double[] RgbToHsb(int r, int g, int b, int a) 
  {
    // mostly copied from
    // http://hg.openjdk.java.net/jdk8/jdk8/jdk/file/687fd7c7986d/src/share/classes/java/awt/Color.java

    double hue, saturation, brightness, alpha = (double) a / 255.0;
    var hsbvals = new double[4];

    int cmax = (r > g) ? r : g;
    if (b > cmax)
      cmax = b;
    int cmin = (r < g) ? r : g;
    if (b < cmin)
      cmin = b;

    brightness = ((double) cmax) / 255.0f;
    if (cmax != 0)
      saturation = ((double) (cmax - cmin)) / ((double) cmax);
    else
      saturation = 0;
    if (saturation == 0)
      hue = 0;
    else {
      double redc = ((double) (cmax - r)) / ((double) (cmax - cmin));
      double greenc = ((double) (cmax - g)) / ((double) (cmax - cmin));
      double bluec = ((double) (cmax - b)) / ((double) (cmax - cmin));
      if (r == cmax)
        hue = bluec - greenc;
      else if (g == cmax)
        hue = 2.0f + redc - bluec;
      else
        hue = 4.0f + greenc - redc;
      hue = hue / 6.0f;
      if (hue < 0)
        hue = hue + 1.0f;
    }
    hsbvals[0] = hue;
    hsbvals[1] = saturation;
    hsbvals[2] = brightness;
    hsbvals[3] = alpha;

    return hsbvals;
  }




  // public static double[] RgbaToDouble(int r, int g, int b, int a) 
  // {
  //   return RgbaToDouble(RgbToInt(r, g, b, a));
  // }

  // public static double[] RgbaToDouble(int color) 
  // {
  //   return new double[] {
  //     ByteToFloatFactor * (color >> 16 & 0xff),   // r
  //     ByteToFloatFactor * (color >> 8 & 0xff),    // g
  //     ByteToFloatFactor * (color & 0xff),         // b
  //     ByteToFloatFactor * (color >> 24 & 0xff),   // a
  //   };
  // }

  // public static int[] IntToRgba(int color) 
  // {
  //   return new int[] {
  //     (color >> 16 & 0xff),   // r
  //     (color >> 8 & 0xff),    // g
  //     (color & 0xff),         // b
  //     (color >> 24 & 0xff),   // a
  //   };
  // }

  public static double[] IntToRgb(int color) 
  {
    return new double[] {
      (color >> 16 & 0xff) * ByteToDoubleFactor,   // r
      (color >> 8 & 0xff) * ByteToDoubleFactor,    // g
      (color & 0xff) * ByteToDoubleFactor,         // b
      (color >> 24 & 0xff) * ByteToDoubleFactor,   // a
    };
  }

  public static int RgbToInt(int r, int g, int b, int a) 
  {
    return (int)(a << 24 | r << 16 | g << 8 | b);
  }

  public static int RgbToInt(float r, float g, float b, float a) 
  {
    return RgbToInt((int)(r * 255), (int)(g * 255), (int)(b * 255), (int)(a * 255));
  }

  public static int RgbToInt(double r, double g, double b, double a) 
  {
    return RgbToInt((int)(r * 255), (int)(g * 255), (int)(b * 255), (int)(a * 255));
  }

  public static int RgbToInt(int r, int g, int b) 
  {
    return RgbToInt(r, g, b, 255);
  }

  public static int RgbToInt(int grey) 
  {
    return RgbToInt(grey, grey, grey, 255);
  }

  public static int RgbToInt(float grey) 
  {
    return RgbToInt(grey, grey, grey, 1.0f);
  }

  public static int RgbToInt(double grey) 
  {
    return RgbToInt(grey, grey, grey, 1.0);
  }

  public static int RgbToInt(float r, float g, float b) 
  {
    return RgbToInt(r, g, b, 1.0F);
  }

  public static int RgbToInt(double r, double g, double b) 
  {
    return RgbToInt(r, g, b, 1.0);
  }

  // public static int LinearTweenHsb(double[] hsba, double[] hsbb, double factor)
  // {
  //   return LinearTweenHsb(hsba[0], hsba[1], hsba[2], hsbb[0], hsbb[1], hsbb[2], factor);
  // }
  
  // public static int LinearTweenHsb(double ha, double sa, double ba, double hb, double sb, double bb, double factor)
  // {
  //   return HsbToInt(LinearTweenHue(ha, hb, factor), LinearTweenSaturation(sa, sb, factor), LinearTweenBrightness(ba, bb, factor));
  // }

  // public static int LinearTweenRgb(int rgba, int rgbb, double factor)
  // {
  //   return LinearTweenRgb(Color.IntToRgb(rgba), Color.IntToRgb(rgbb), factor);
  // }

  // public static int LinearTweenRgb(double[] rgba, double[] rgbb, double factor)
  // {
  //   return LinearTweenRgb(rgba[0], rgba[1], rgba[2], rgbb[0], rgbb[1], rgbb[2], factor);
  // }
  
  // public static int LinearTweenRgb(double ra, double ga, double ba, double rb, double gb, double bb, double factor)
  // {
  //   return RgbToInt(Math.Clamp(ra + (rb - ra) * factor), Math.Clamp(ga + (gb - ga) * factor), Math.Clamp(ba + (bb - ba) * factor));
  // }

  // public static double LinearTweenHue(double ha, double hb, double factor)
  // {
  //   if (ha + 1.0 - hb < hb - ha)
  //   {
  //     ha += 1.0;
  //   }

  //   return WrapHue(ha + (hb - ha) * factor);
  // }

  // public static double LinearTweenSaturation(double sa, double sb, double factor)
  // {
  //   return ClampSaturation(sa + (sb - sa) * factor);
  // }

  // public static double LinearTweenBrightness(double ba, double bb, double factor)
  // {
  //   return ClampBrightness(ba + (bb - ba) * factor);
  // }

  // public static double HueAdjust(double hue, int degrees) 
  // {
  //   return HueAdjust(hue, DegreesToDouble(degrees));
  // }

  // public static double HueAdjust(double hue, int mindegrees, int maxdegrees) 
  // {
  //   return HueAdjust(hue, DegreesToDouble(RandomGenerator.Value(mindegrees, maxdegrees)));
  // }

  // public static double HueAdjust(double hue, double factor) 
  // {
  //   return WrapHue(hue + factor);
  // }

  // public static double HueAdjust(double hue, double minfactor, double maxfactor) 
  // {
  //   return WrapHue(hue + RandomGenerator.Value(minfactor, maxfactor));
  // }

  // public static double BrightnessAdjust(double brightness, int percentage) 
  // {
  //   return BrightnessAdjust(brightness, (double)percentage * 0.01);
  // }

  // public static double BrightnessAdjust(double brightness, double percentage) 
  // {
  //   return ClampBrightness(brightness + percentage);
  // }

  // public static double BrightnessAdjust(double brightness, double minpercentage, double maxpercentage) 
  // {
  //   return ClampBrightness(brightness + RandomGenerator.Value(minpercentage, maxpercentage));
  // }

  // public static double SaturationAdjust(double saturation, int percentage) 
  // {
  //   return SaturationAdjust(saturation, (double)percentage * 0.01);
  // }

  // public static double SaturationAdjust(double saturation, double percentage) 
  // {
  //   return ClampSaturation(saturation + percentage);
  // }

  // public static double SaturationAdjust(double saturation, double minpercentage, double maxpercentage) 
  // {
  //   return ClampSaturation(saturation + RandomGenerator.Value(minpercentage, maxpercentage));
  // }

  public static double DegreesToDouble(int degrees) 
  {
    return (double)degrees * DegreesToDoubleFactor;
  }
  
  // public static double WrapHue(int degrees) 
  // {
  //   if (degrees < 0) {
  //     return WrapHue(degrees + 360);
  //   }
  //   if (degrees > 359) {
  //     return WrapHue(degrees - 360);
  //   }
  //   return degrees;
  // }
  
  // public static double WrapHue(double value) 
  // {
  //   if (value < 0.0) {
  //     return WrapHue(value + 1.0);
  //   }
  //   if (value >= 1.0) {
  //     return WrapHue(value - 1.0);
  //   }
  //   return value;
  // }
  
  // public static double ClampSaturation(int percentage) 
  // {
  //   if (percentage < 0) {
  //     return 0;
  //   }
  //   if (percentage > 100) {
  //     return 100;
  //   }
  //   return percentage;
  // }
  
  // public static double ClampSaturation(double value) 
  // {
  //   return Clamp(value);
  // }
  
  // public static double ClampBrightness(int percentage) 
  // {
  //   if (percentage < 0) {
  //     return 0;
  //   }
  //   if (percentage > 100) {
  //     return 100;
  //   }
  //   return percentage;
  // }
  
  // public static double ClampBrightness(double value) 
  // {
  //   return Clamp(value);
  // }

  private static double Clamp(double value) 
  {
    if (value < 0.0) {
      return 0.0;
    }
    if (value > 1.0) {
      return 1.0;
    }
    return value;
  }

  public Color Brighter(float fraction) 
  {
    return Brighter(fraction, 1f);
  }

  public Color Brighter(float fraction, float max) 
  {
    return Brighter(fraction, 0f, 1f);
  }

  public Color Brighter(float fraction, float min, float max) 
  {
    _brightness = Utility.Math.Clamp(_brightness + fraction, min, max);
    return CalculateRgb();
  }

  // public static int RandomColor()
  // {
  //   return DefaultColorPicker.RandomColor();
  // }

  // public static int RandomColor(int hue)
  // {
  //   return RandomColor(hue, RandomGenerator);
  // }

  // public static int RandomColor(int hue, IRandomGenerator randomGenerator)
  // {
  //   return RandomColor(Color.DegreesToDouble(hue), randomGenerator);
  // }

  // public static int RandomColor(double hue)
  // {
  //   return RandomColor(hue, RandomGenerator);
  // }

  // public static int RandomColor(double hue, IRandomGenerator randomGenerator)
  // {
  //   return HsbToInt(hue, RandomSaturation(randomGenerator), RandomBrightness(randomGenerator), 1.0);
  // }

  // public static int RandomColor(int saturation, int brightness)
  // {
  //   return RandomColor(saturation, brightness, RandomGenerator);
  // }

  // public static int RandomColor(int saturation, int brightness, IRandomGenerator randomGenerator)
  // {
  //   return RandomColor(saturation * 0.01, brightness * 0.01, randomGenerator);
  // }

  // public static int RandomColor(double saturation, double brightness)
  // {
  //   return RandomColor(saturation, brightness, RandomGenerator);
  // }

  // public static int RandomColor(double saturation, double brightness, IRandomGenerator randomGenerator)
  // {
  //   return HsbToInt(RandomHue(randomGenerator), saturation, brightness, 1.0);
  // }

  // public static double RandomHue(IRandomGenerator randomGenerator) {
  //   return randomGenerator.Value();
  // }

  // public static double RandomSaturation(IRandomGenerator randomGenerator) {
  //   return randomGenerator.Value();
  // }

  // public static double RandomBrightness(IRandomGenerator randomGenerator) {
  //   return randomGenerator.Value();
  // }

  // public static double RandomAlpha(IRandomGenerator randomGenerator) {
  //   return randomGenerator.Value();
  // }

  // public double RandomHue() {
  //   return RandomHue(RandomGenerator);
  // }

  // public double RandomSaturation() {
  //   return RandomSaturation(RandomGenerator);
  // }

  // public double RandomBrightness() {
  //   return RandomBrightness(RandomGenerator);
  // }

  // public double RandomAlpha() {
  //   return RandomAlpha(RandomGenerator);
  // }


  // public static int RandomColorFromSb(double s, double b)
  // {
  //   return ColorPicker.RandomColorFromSb(s, b);
  // }

  // public static int RandomColorFromSb(double s, double b, IRandomGenerator randomGenerator)
  // {
  //   return ColorPicker.RandomColorFromSb(s, b, randomGenerator);
  // }

  // public static int RandomFromH(int hue, int variation)
  // {
  //   return ColorPicker.RandomFromH(hue, variation);
  // }

  // public static int RandomFromH(int hue, int variation, IRandomGenerator randomGenerator)
  // {
  //   return ColorPicker.RandomFromH(hue, variation, randomGenerator);
  // }

  // public static int RandomFromH(double hue, double variation)
  // {
  //   return ColorPicker.RandomFromH(hue, variation);
  // }

  // public static int RandomFromH(double hue, double variation, IRandomGenerator randomGenerator)
  // {
  //   return ColorPicker.RandomFromH(hue, variation, randomGenerator);
  // }
}