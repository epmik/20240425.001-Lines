import Geometry.Vector2;
import processing.core.PGraphics;

public class SunRayNoiseWidth extends AbstractNoiseSunRay
{
    public float MinWidth = 1f;
    public float MaxWidth = 16f;
    public float NoiseInputMultiplier = 0.010f;

    public void Draw(PGraphics graphics)
    {
        graphics.pushMatrix();

        graphics.rotate(Angle());
        
        graphics.noFill();

        // graphics.beginShape();

        for (var time = 0f; time < 1f; time += _timeStep) 
        {
            Vector2 u = Trajectory().PointAt(time);
            Vector2 v = Trajectory().PointAt(time + _timeStep);

            var n = NoiseGenerator.Value(u.X * NoiseInputMultiplier, u.Y * NoiseInputMultiplier);

            var w = (float)(MinWidth + ((MaxWidth - MinWidth) * Utility.NoiseGenerator.PositiveClamp(n)));

            graphics.strokeWeight(w);
            graphics.stroke(((Sketch002)(Sketch.Instance)).RayColor(this, time, graphics.screenX(u.X, u.Y), graphics.screenY(u.X, u.Y)));
            graphics.line(u.X, u.Y, v.X, v.Y);
        }

        // graphics.endShape();

        graphics.popMatrix();

    }
}
