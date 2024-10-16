import java.util.function.Supplier;

import Utility.Color;
import Utility.OpenSimplexNoiseGenerator;
import Utility.RandomGenerator;
import Utility.Interfaces.INoiseGenerator;
import Utility.Interfaces.IRandomGenerator;

public class CurvySunRayProviderOptions implements ISunRayProviderOptions
{
    public float MinLength = 1f;
    public float MaxLength = 1024f;

    public float MinWidth = 1f;
    public float MaxWidth = 1f;

    public float MinAngle = 0f;
    public float MaxAngle = (float)Math.PI * 2f;

    public int MinWalkCount = 1;
    public int MaxWalkCount = 6;

    public float MinMinAngleStep = 0.001f;
    public float MaxMinAngleStep = 0.01f;
    public float MinMaxAngleStep = 0.01f;
    public float MaxMaxAngleStep = 0.025f;

    public float MinWalkDistanceDeviation = 0f;
    public float MaxWalkDistanceDeviation = 0.25f;

    public IColorProvider ColorProvider = null;

    public Supplier<Color> ColorSupplier = null;

    public IRandomGenerator TrajectoryRandomGenerator = null;

    public INoiseGenerator TrajectoryNoiseGenerator = null;

}
