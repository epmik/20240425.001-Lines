import Utility.Color;
import processing.core.PApplet;
import processing.core.PGraphics;

public class LinearGradientBackground implements IBackground
{
    public LinearGradient Gradient = new LinearGradient();

    public LinearGradientBackground() 
    {
        this(new Color(255f, 255f, 255f), new Color(0f, 0f, 0f));
    }
 
    public LinearGradientBackground(Color from, Color to) 
    {
        Gradient.Insert(0f, from);
        Gradient.Insert(1f, to);
    }
 
    public void Setup()
    {

    }

    public void Update(float elapsed)
    {

    }

    public void Draw(PGraphics graphics)
    {
        graphics.noFill();

        float factor = 0f;

        for (int i = 0; i <= graphics.height; i++) 
        {
            factor = PApplet.map(i, 0, graphics.height, 0, 1);

            graphics.stroke(Gradient.ColorAt(factor).ToInt());
            graphics.line(0, i, graphics.width, i);
        }
    }
}
