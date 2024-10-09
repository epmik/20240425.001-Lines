
package Utility.Interfaces;

public interface INoiseGenerator
{
    long Seed();
    long Reset();
    long ReSeed();
    long ReSeed(long seed);
    float Value(float x);
    float Value(float x, float y);
    float Value(float x, float y, float z);
    float Value(float x, float y, float z, float w);
}
