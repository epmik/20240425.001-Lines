import java.util.function.Supplier;

import Utility.Color;
import Utility.ColorPicker;

public class RadialBurstSunRayProviderOptions implements ISunRayProviderOptions
{
    public float MinLength = 1f;
    public float MaxLength = 1024f;

    public float MinWidth = 1f;
    public float MaxWidth = 1f;

    public float MinAngle = 0f;
    public float MaxAngle = (float)Math.PI * 2f;

    public IColorProvider ColorProvider = null;

    public Supplier<Color> ColorSupplier = null;
}
