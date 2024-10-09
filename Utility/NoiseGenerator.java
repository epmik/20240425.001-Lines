package Utility;

import Utility.Interfaces.INoiseGenerator;

public abstract class NoiseGenerator implements INoiseGenerator
{
	protected long _seed = 0;

	protected static long SeedRoot = new java.util.Date().getTime();	
	  
	public NoiseGenerator() 
	{
		_seed = SeedRoot++;
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

	public static float Positive(float value)
	{
		return (1.0f + value) * 0.5f;
	}

	public static float Clamp(float value)
	{
		return Utility.Math.Clamp(value);
	}

	public static float PositiveClamp(float value)
	{
		return Utility.Math.Clamp((1.0f + value) * 0.5f);
	}

	public abstract long ReSeed(long seed);
	
	public abstract float Value(float x);

	public abstract float Value(float x, float y);

	public abstract float Value(float x, float y, float z);

	public abstract float Value(float x, float y, float z, float w);
}