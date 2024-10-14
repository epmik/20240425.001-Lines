import Geometry.Vector2;
import processing.core.PGraphics;

public class SunRay002 extends AbstractSunRay
{
    public void Draw(PGraphics graphics)
    {
        graphics.pushMatrix();

        for (var time = 0f; time <= 1f; time += _timeStep) 
        {
            Vector2 v = Trajectory().PointAt(time);

            var screenx = graphics.screenX(v.X, v.Y);
            var screeny = graphics.screenY(v.X, v.Y);

            graphics.noStroke();
            graphics.fill(SketchTrajectoryCurvy001.Instance.RayColor(this, time, screenx, screeny));
            graphics.circle(v.X, v.Y, 8f);
        }

        graphics.popMatrix();

    }
}
