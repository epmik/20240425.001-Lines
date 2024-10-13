package Utility;

import java.util.Date;

import Utility.Interfaces.IRandomGenerator;
  
public class RandomGenerator implements IRandomGenerator
{
    private long _seed;
    private java.util.Random _random;
 
    private static long SeedRoot = new java.util.Date().getTime();

    public RandomGenerator()
    {
      this(SeedRoot++);
    }
  
    public RandomGenerator(long seed)
    {
      _seed = seed;
      _random = new java.util.Random(_seed);
    }
    
    public long Seed()  
    {
      return _seed;
    }
    
    public long Reset()
    {
      return ReSeed(_seed);
    }
    
    public long ReSeed()
    {
      return ReSeed(SeedRoot++);
    }
    
   public long ReSeed(long seed)
   {
    if(seed != _seed)
    {
      _seed = seed;
      _random.setSeed(_seed);
     }     
     return _seed;
   }
    
    public float Value()
    {
        return (float)_random.nextDouble();
    }
    
    public float Value(float max)
    {
        return Value(0, max);
    }
    
    public float Value(float min, float max)
    {
        return min + (float)_random.nextDouble() * (max - min);
    }
    
    public double Value(double max)
    {
        return Value(0, max);
    }
    
    public double Value(double min, double max)
    {
        return min + _random.nextDouble() * (max - min);
    }
    
    public int Value(int max)
    {
        return Value(0, max);
    }
    
    public int Value(int min, int max)
    {
        return (int)(min + _random.nextDouble() * (max - min));
    }
    
    public boolean Boolean()
    {
        return Value(0, 2) == 0;
    }
}
