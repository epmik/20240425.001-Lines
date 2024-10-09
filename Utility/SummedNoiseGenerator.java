package Utility;

import Utility.Interfaces.INoiseGenerator;
import Utility.Interfaces.ISummedNoiseGenerator;

/*
 * OpenSimplex Noise in Java.
 * by Kurt Spencer
 * 
 * v1.1 (October 5, 2014)
 * - Added 2D and 4D implementations.
 * - Proper gradient sets for all dimensions, from a
 *   dimensionally-generalizable scheme with an actual
 *   rhyme and reason behind it.
 * - Removed default permutation array in favor of
 *   default seed.
 * - Changed seed-based constructor to be independent
 *   of any particular randomization library, so results
 *   will be the same when ported to other languages.
 */
 
public class SummedNoiseGenerator<TNoiseGenerator extends INoiseGenerator> extends NoiseGenerator implements ISummedNoiseGenerator 
{
	protected TNoiseGenerator _noiseGenerator;

	private float _frequency = 1.0f;
	private float _lacunarity = 2.0f;
	private float _persistence = 0.5f;
	private int _octaves = 1;

	// private boolean _clampValues = false;
	// private boolean _allowNegativeValues = true;
	
	// private float _multiplierX = 1.0f;
	// private float _multiplierY = 1.0f;
	// private float _multiplierZ = 1.0f;
	// private float _multiplierW = 1.0f;

	/// <summary>
	/// The frequency of the first octave. 1.0 by default. 
	/// </summary>
	public float Frequency()
	{
		return _frequency;
	}

	/// <summary>
	/// The frequency of the first octave. 1.0 by default. 
	/// </summary>
	public void Frequency(float frequency)
	{
		_frequency = frequency;
	}

	/// <summary>
	/// The density of the generated noise/fractal. By default 2.0. The lower the value the smoother or less dense the noise is.
	/// </summary>
	public float Lacunarity()
	{
		return _lacunarity;
	}

	/// <summary>
	/// The density of the generated noise/fractal. By default 2.0. The lower the value the smoother or less dense the noise is.
	/// </summary>
	public void Lacunarity(float lacunarity)
	{
		_lacunarity = lacunarity;
	}

	// Used to calculate the amplitude for each frequency or octave. By default this is 0.5 so that the amplitude is halved every octave.
	public float Persistence()
	{
		return _persistence;
	}

	// Used to calculate the amplitude for each frequency or octave. By default this is 0.5 so that the amplitude is halved every octave.
	public void Persistence(float persistence)
	{
		_persistence = persistence;
	}

	/// <summary>
	/// The number of octaves used the generate the random value. By default this is 1.
	/// </summary>
	public int Octaves()
	{
		return _octaves;
	}

	/// <summary>
	/// The number of octaves used the generate the random value. By default this is 1.
	/// </summary>
	public void Octaves(int octaves)
	{
		_octaves = octaves;
	}	
	
	public SummedNoiseGenerator(TNoiseGenerator noiseGenerator)
	{
		_noiseGenerator = noiseGenerator;
	}

	public long Seed() 
	{
		return _noiseGenerator.Seed();
	}

	public long Reset() 
	{
		return _noiseGenerator.Reset();
	}

	public long ReSeed() 
	{
		return _noiseGenerator.ReSeed();
	}

	public long ReSeed(long seed) 
	{
		return _noiseGenerator.ReSeed(seed);
	}
	
	public float Value(float x) 
	{
		return Value(x, x);
	}

	public float Value(float x, float y) 
	{
		// x *= _multiplierX;
		// y *= _multiplierY;

		var value = 0.0f;
		var amplitude = 1.0f;
		var frequency = Frequency();

		for (var c = 0; c < Octaves(); c++)
		{
			value += _noiseGenerator.Value(x * frequency, y * frequency) * amplitude;

			frequency *= Lacunarity();
			amplitude *= Persistence();
		}

		// return ConditionalClampAndRange(value);
		return value;
	}

	public float Value(float x, float y, float z) 
	{
		// x *= _multiplierX;
		// y *= _multiplierY;
		// z *= _multiplierZ;

		var value = 0.0f;
		var amplitude = 1.0f;
		var frequency = Frequency();

		for (var c = 0; c < Octaves(); c++)
		{
			value += _noiseGenerator.Value(x * frequency, y * frequency, z * frequency) * amplitude;

			frequency *= Lacunarity();
			amplitude *= Persistence();
		}
		
		// return ConditionalClampAndRange(value);
		return value;
	}

	public float Value(float x, float y, float z, float w) 
	{
		// x *= _multiplierX;
		// y *= _multiplierY;
		// z *= _multiplierZ;
		// w *= _multiplierW;

		var value = 0.0f;
		var amplitude = 1.0f;
		var frequency = Frequency();

		for (var c = 0; c < Octaves(); c++) {
			value += _noiseGenerator.Value(x * frequency, y * frequency, z * frequency, w * frequency) * amplitude;

			frequency *= Lacunarity();
			amplitude *= Persistence();
		}

		// return ConditionalClampAndRange(value);
		return value;
	}	

	// private double ConditionalClampAndRange(double value) 
	// {
	// 	// value are by default in the range -1 to 1
	// 	if (_clampValues) {
	// 		if (value < -1) {
	// 			value = -1;
	// 		} else if (value > 1) {
	// 			value = 1;
	// 		}
	// 	}

	// 	if (!_allowNegativeValues) {
	// 		value = (value + 1) * 0.5;
	// 	}

	// 	return value;
	// }
		
	// // @Override
	// public boolean ClampValues() 
	// {
	//   return _clampValues;
	// }
  
	// // @Override
	// public void ClampValues(boolean state) 
	// {
	//   _clampValues = state;
	// }
  
	// // @Override
	// public boolean AllowNegativeValues() 
	// {
	//   return _allowNegativeValues;
	// }
  
	// // @Override
	// public void AllowNegativeValues(boolean state) 
	// {
	//   _allowNegativeValues = state;
	// }
  
	// // @Override
	// public double InputMultiplierX() 
	// {
	//   return _multiplierX;
	// }
  
	// // @Override
	// public double InputMultiplierY() 
	// {
	//   return _multiplierY;
	// }
  
	// // @Override
	// public double InputMultiplierZ() 
	// {
	//   return _multiplierZ;
	// }
  
	// // @Override
	// public double InputMultiplierW() 
	// {
	//   return _multiplierW;
	// }
  
	// // @Override
	// public void InputMultipliers(double multiplier) 
	// {
	//   _multiplierX = _multiplierY = _multiplierZ = _multiplierW = multiplier;
	// }
  
	// // @Override
	// public void InputMultiplier(double multiplierX) 
	// {
	//   _multiplierX = multiplierX;
	// }
  
	// // @Override
	//   public void InputMultiplier(double multiplierX, double multiplierY) 
	// {
	//   _multiplierX = multiplierX;
	//   _multiplierY = multiplierY;
	// }
  
	// // @Override
	// public void InputMultiplier(double multiplierX, double multiplierY, double multiplierZ) 
	// {
	//   _multiplierX = multiplierX;
	//   _multiplierY = multiplierY;
	//   _multiplierZ = multiplierZ;
	// }
  
	// // @Override
	// public void InputMultiplier(double multiplierX, double multiplierY, double multiplierZ, double multiplierW) 
	// {
	// 	_multiplierX = multiplierX;
	// 	_multiplierY = multiplierY;
	// 	_multiplierZ = multiplierZ;
	// 	_multiplierW = multiplierW;
	// }
}