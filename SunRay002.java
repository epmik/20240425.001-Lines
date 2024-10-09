import Geometry.Vector2;
import processing.core.PGraphics;

public class SunRay002 extends AbstractSunRay
{
    public void Draw(PGraphics graphics)
    {
        var step = 1f / (float)Resolution;

        graphics.pushMatrix();

        for (var time = 0f; time <= 1f; time += step) 
        {
            Vector2 v = Trajectory().PointAt(time);

            var screenx = graphics.screenX(v.X, v.Y);
            var screeny = graphics.screenY(v.X, v.Y);

            graphics.noStroke();
            graphics.fill(Sketch.Instance.RayColor(this, time, screenx, screeny));
            graphics.circle(v.X, v.Y, 8f);
        }

        graphics.popMatrix();

    }
}
