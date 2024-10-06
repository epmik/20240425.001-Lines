import Geometry.Vector2;
import Utility.Easing;
import Utility.OpenSimplexSummedNoiseGenerator;
import Utility.RandomGenerator;
import processing.core.PGraphics;

public class Trajectory002 implements ITrajectory
{
    // public RandomGenerator RandomGenerator;
    public OpenSimplexSummedNoiseGenerator NoiseGenerator;
    public float MinAngleStep = 0.01f;
    public float MaxAngleStep = 0.05f;
    public int RayColor = 0;
    public float MaxDeviation = 200f;
    public float NoiseMultiplier = 0.01f;
    public float AngleNoiseOffset = 10.7641f;
    public float AngleNoiseMultiplier = 0.1f;
    private float _distanceStep = 4f;
    public float TotalDistance = 1000;
    public int Resolution = 250;

    private Vector2[] _points;

    // private int DefaultScaleHeight = 1024; 

    // private float _scale = 1f;

    // public float Scale()
    // {
    //     return _scale;
    // }
    
    // public void Scale(float scale)
    // {
    //     _scale = scale;
    // }

    public Trajectory002()
    {
        // RandomGenerator = new RandomGenerator();
        NoiseGenerator = new OpenSimplexSummedNoiseGenerator();

        // Angle = (float) RandomGenerator.Value(Math.PI * 2f);
        
        // Scale((float) Sketch.Instance.Graphics.height / (float) DefaultScaleHeight);
    }

    public void Update(float elapsed)
    {
        
    }
    
    public void Draw(PGraphics graphics)
    {
        WalkTrajectory();

        graphics.pushMatrix();

        for (Vector2 v : _points) 
        {
            var screenx = graphics.screenX(v.X, v.Y);
            var screeny = graphics.screenY(v.X, v.Y);

            graphics.noStroke();
            graphics.fill(Sketch.Instance.RayColor(this, screenx, screeny));
            graphics.circle(v.X, v.Y, 2f);
        }

        graphics.popMatrix();

        PointAt(0.0f);
        PointAt(0.5f);
        PointAt(0.505f);
        PointAt(1.0f);
    }
    
    public Vector2 PointAt(float time)
    {
        time = Utility.Math.Clamp(time);

        var step = 1f / (float) Resolution;

        var index = (int) (time / step);

        var remainder = time - ((float) index * step);
        
        return _points[index];
    }
    
    public void WalkTrajectory()
    {
        if(_points != null && _points.length == Resolution)
        {
            return;
        }
        
        _points = null;
        _points = new Vector2[Resolution];

        _distanceStep = TotalDistance / (float) Resolution;

        float x = 0f, y = 0f;
        float a = 0f;
        int resolutionIndex = 0;

        while(resolutionIndex < Resolution)
        {
            float angleStep = (float)(Math.PI * (MinAngleStep + ((MaxAngleStep - MinAngleStep) * NoiseGenerator.Value((AngleNoiseOffset + y) * AngleNoiseMultiplier))));

            var r = WalkCurve(x, y, 200, a, angleStep, resolutionIndex);

            x = r[0];
            y = r[1];
            a = r[3];
            resolutionIndex = (int)r[4];
        }
    }
       
    private float[] WalkCurve(float x, float y, float walkDistance, float angle, float angleStep, int resolutionIndex)
    {
        float distance = 0f;

        while(distance <= walkDistance && resolutionIndex < Resolution)
        {
            _points[resolutionIndex] = new Vector2(x, y);
            // var screenx = graphics.screenX(x, y);
            // var screeny = graphics.screenY(x, y);

            // graphics.noStroke();
            // graphics.fill(Sketch.Instance.RayColor(this, screenx, screeny));
            // graphics.circle(x, y, 2);

            x +=  _distanceStep * Math.cos(angle);
            y +=  _distanceStep * Math.sin(angle);

            angle += angleStep;
            distance += _distanceStep;
            resolutionIndex++;
        }

        return new float[] { x, y, distance, angle, resolutionIndex };
    }
}
