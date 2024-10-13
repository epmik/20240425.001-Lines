
package Utility.Interfaces;

public interface IRandomGenerator
{
    long Seed();
    long Reset();
    long ReSeed();
    long ReSeed(long seed);
    float Value();
    float Value(float max);
    float Value(float min, float max);
    double Value(double max);
    double Value(double min, double max);
    int Value(int max);
    int Value(int min, int max);
    boolean Boolean();
}
