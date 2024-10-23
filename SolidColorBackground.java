import processing.core.PGraphics;
import Utility.Color;

public class SolidColorBackground implements IBackground
{
    private Color _color = new Color(255, 255, 255, 255);
    private int _colorInt = _color.ToInt();

    public SolidColorBackground() 
    {
    }

    public SolidColorBackground(Color color) 
    {
        Color(color);
    }

    public void Color(Color color)
    {
        _color = color;
        _colorInt = _color.ToInt();
    }

    public Color Color()
    {
        return _color;
    }

    public void Setup()
    {

    }

    public void Update(float elapsed)
    {

    }

    public void Draw(PGraphics graphics)
    {
        graphics.background(_colorInt);
    }
}
