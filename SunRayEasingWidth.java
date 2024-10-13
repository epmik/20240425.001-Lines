import Geometry.Vector2;
import Utility.Easing;
import processing.core.PGraphics;

public class SunRayEasingWidth extends AbstractSunRay
{
    public float MinWidth = 1f;
    public float MaxWidth = 16f;
    public Easing.IEasingFunction EasingFunction = Easing::Linear;

    public void Draw(PGraphics graphics)
    {
        var step = 1f / (float)Resolution;

        graphics.pushMatrix();

        graphics.rotate(Angle);

        graphics.noFill();

        // graphics.beginShape();

        for (var time = 0f; time < 1f; time += step) 
        {
            Vector2 u = Trajectory().PointAt(time);
            Vector2 v = Trajectory().PointAt(time + step);

            var w = MinWidth + ((MaxWidth - MinWidth) * EasingFunction.Ease(1f - time));

            graphics.strokeWeight(w);
            graphics.stroke(((SketchTrajectoryStraight001)(Sketch.Instance)).RayColor(this, time, graphics.screenX(u.X, u.Y), graphics.screenY(u.X, u.Y)));
            graphics.line(u.X, u.Y, v.X, v.Y);
        }

        // graphics.endShape();

        graphics.popMatrix();

    }
}
