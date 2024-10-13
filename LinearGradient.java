import java.util.ArrayList;
import Utility.Color;
import Utility.RandomGenerator;

public class LinearGradient
{
    public RandomGenerator RandomGenerator;

    private float _minTime = 0f;
    private float _maxTime = 0f;

    ArrayList<Entry> _entryArrayList = new ArrayList<Entry>();

    private class Entry 
    {
        public float Time;   
        public float R;
        public float G;
        public float B;
        public float A;
        public Color Color = Utility.Color.FromRgb(1f, 1f, 1f, 1f);
        
        public Entry() 
        {
        }
        
        public Entry(float time, float r, float g, float b) 
        {
            this(time, r, g, b, 1f);
        }
        
        public Entry(float time, float r, float g, float b, float a) 
        {
            this(time, (int)(255 * r), (int)(255 * g), (int)(255 * b), (int)(255 * a));
        }
        
        public Entry(float time, int r, int g, int b) 
        {
            this(time, r, g, b, 255);
        }
        
        public Entry(float time, int r, int g, int b, int a) 
        {
            Time = time;
            R = r;
            G = g;
            B = b;
            A = a;
        }
    }

    public LinearGradient() 
    {
        RandomGenerator = new RandomGenerator();
    }
    
    public void Insert(float time, Color color)
    {
        Insert(time, color.R(), color.G(), color.B(), color.A());
    }
    
    public void Insert(float time, int color)
    {
        Insert(time, Utility.Color.RInt(color), Utility.Color.GInt(color), Utility.Color.BInt(color), Utility.Color.AInt(color));
    }
    
    public void Insert(float time, int r, int g, int b)
    {
        Insert(time, (float)(r * 0.0039215686274509803921568627451), (float)(g * 0.0039215686274509803921568627451), (float)(b * 0.0039215686274509803921568627451), 1f);
    }
    
    public void Insert(float time, int r, int g, int b, int a)
    {
        Insert(time, (float)(r * 0.0039215686274509803921568627451), (float)(g * 0.0039215686274509803921568627451), (float)(b * 0.0039215686274509803921568627451), (float)(a * 0.0039215686274509803921568627451));
    }
    
    public void Insert(float time, float r, float g, float b)
    {
        Insert(time, r, g, b, 1f);
    }
    
    public void Insert(float time, float r, float g, float b, float a)
    {
        _entryArrayList.removeIf(o -> o.Time == time);

        _minTime = time < _minTime ? time : _minTime;
        _maxTime = time > _maxTime ? time : _maxTime;

        _entryArrayList.add(new Entry() {
            {
                Time = time;
                R = r;
                G = g;
                B = b;
                A = a;
                Color = Utility.Color.FromRgb(r, g, b, a);
            }
        });

        _entryArrayList.sort((i, j) -> i.Time <= j.Time ? -1 : 0);
    }
    
    public void Clear()
    {
        _entryArrayList.clear();
    }
    
    public Color Color(float time)
    {
        time = Utility.Math.Clamp(time, _minTime, _maxTime);

        int first = 0, last = 0;       

        for (Entry entry : _entryArrayList) 
        {
            if (entry.Time >= time) 
            {
                break;
            }
            last++;
        }

        first = last - 1;

        if (first < 0) 
        {
            first = last;
        }

        var firstEntry = _entryArrayList.get(first);
        var lastEntry = _entryArrayList.get(last);

        var t = (lastEntry.Time - firstEntry.Time);
        t = t > 0 ? ((time - firstEntry.Time) / t) : 0f;

        return Color.Interpolate(firstEntry.Color, lastEntry.Color, t);
    }
    
    public Color RandomColor()
    {
        return RandomColor(0f, 1f);
    }
    
    public Color RandomColor(float max)
    {
        return RandomColor(0f, max);
    }

    public Color RandomColor(float min, float max)
    {
        min = Color.Clamp(min);
        max = Color.Clamp(max);

        return Color((float)RandomGenerator.Value(min, max));
    }
}
