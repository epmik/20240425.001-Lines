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

        // graphics.beginShape(PConstants.LINE_STRIP);

        graphics.strokeWeight(Width);

        for (; _currentTime < _targetTime; _currentTime += _timeStep) 
        {
            Vector2 v = Trajectory().PointAt(_currentTime);
            Vector2 w = Trajectory().PointAt(_currentTime + _timeStep);

            var screenx = graphics.screenX(v.X, v.Y);
            var screeny = graphics.screenY(v.X, v.Y);

            graphics.stroke(ColorAt(_currentTime, screenx, screeny).ToInt());
            graphics.line(v.X, v.Y, w.X, w.Y);
        }

        // graphics.endShape();

        graphics.popMatrix();

        // _currentTime = Utility.Math.Clamp(_currentTime, _targetTime);

    }
}
