import Geometry.Vector2;
import processing.core.PConstants;
import processing.core.PGraphics;

public class SunRayStaticWidth extends AbstractSunRay
{
    public float Width = 1f;

    public void Draw(PGraphics graphics)
    {
        if(!IsAlive())
        {
            return;
        }

        graphics.pushMatrix();

        graphics.rotate(Angle());
        
        graphics.noFill();

        graphics.beginShape(PConstants.LINE_STRIP);

        graphics.strokeWeight(Width);

        for (; _currentTime <= _targetTime; _currentTime += _timeStep) 
        {
            Vector2 v = Trajectory().PointAt(_currentTime);

            var screenx = graphics.screenX(v.X, v.Y);
            var screeny = graphics.screenY(v.X, v.Y);

            graphics.stroke(((Sketch002)(Sketch.Instance)).RayColor(this, _currentTime, screenx, screeny));
            graphics.vertex(v.X, v.Y);
        }

        graphics.endShape();

        graphics.popMatrix();

    }
}
