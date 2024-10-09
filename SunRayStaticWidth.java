import Geometry.Vector2;
import processing.core.PGraphics;

public class SunRayStaticWidth extends AbstractSunRay
{
    public float Width = 1f;

    public void Draw(PGraphics graphics)
    {
        var step = 1f / (float)Resolution;

        graphics.pushMatrix();

        graphics.noFill();

        graphics.beginShape();

        graphics.strokeWeight(Width);

        for (var time = 0f; time <= 1f; time += step) 
        {
            Vector2 v = Trajectory().PointAt(time);

            var screenx = graphics.screenX(v.X, v.Y);
            var screeny = graphics.screenY(v.X, v.Y);

            graphics.stroke(Sketch.Instance.RayColor(this, time, screenx, screeny));
            graphics.vertex(v.X, v.Y);
        }

        graphics.endShape();

        graphics.popMatrix();

    }
}
