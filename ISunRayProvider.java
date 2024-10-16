import Utility.Color;
import processing.core.PGraphics;

public interface ISunRayProvider
{
    AbstractSunRay Create(ISunRayProviderOptions options);
}
