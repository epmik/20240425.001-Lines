import Utility.Color;

public interface IColorProvider 
{
    Color ColorAt(float time, float x, float y);

    Color ColorAt(float time);

    Color ColorAt(float x, float y);
}
