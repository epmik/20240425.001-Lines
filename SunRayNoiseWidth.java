import Geometry.Vector2;
import processing.core.PGraphics;

public class SunRayNoiseWidth extends AbstractNoiseSunRay
{
    public float MinWidth = 1f;
    public float MaxWidth = 16f;
    public float NoiseInputMultiplier = 0.25f;

    public void Draw(PGraphics graphics)
    {
        var step = 1f / (float)Resolution;

        graphics.pushMatrix();

        graphics.noFill();

        graphics.beginShape();

        for (var time = 0f; time <= 1f; time += step) 
        {
            Vector2 v = Trajectory().PointAt(time);

            var screenx = graphics.screenX(v.X, v.Y);
            var screeny = graphics.screenY(v.X, v.Y);

            var w = (float)(MinWidth + (MaxWidth - MinWidth) * Utility.NoiseGenerator.PositiveClamp(NoiseGenerator.Value(v.X * NoiseInputMultiplier, v.Y * NoiseInputMultiplier)));

            graphics.strokeWeight(w);
            graphics.stroke(Sketch.Instance.RayColor(this, time, screenx, screeny));
            graphics.vertex(v.X, v.Y);
        }

        graphics.endShape();

        graphics.popMatrix();

    }
}
