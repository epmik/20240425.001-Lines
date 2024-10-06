import Utility.Color;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class SunBackgroundVerticalGradiant
{
    public int Alpha = 255;
    public LinearGradient Gradient = new LinearGradient();

    public SunBackgroundVerticalGradiant() 
    {
        super();
    }
           
    void Draw(PGraphics graphics) 
    {
        graphics.noFill();

        if (Alpha < 255)
        {
            graphics.blendMode(PConstants.ADD);
        }

        float factor = 0f;
        int color = 0;

        for (int i = 0; i <= graphics.height; i++) 
        {
            factor = PApplet.map(i, 0, graphics.height, 0, 1);

            color = Gradient.Color(factor).ToInt();

            graphics.stroke(color, Alpha);
            graphics.line(0, i, graphics.width, i);
        }

        if (Alpha < 255)
        {
            graphics.blendMode(PConstants.BLEND);
        }
    }
}
