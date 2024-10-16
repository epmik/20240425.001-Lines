import Geometry.Vector2;
import Utility.OpenSimplexNoiseGenerator;
import Utility.OpenSimplexSummedNoiseGenerator;
import Utility.RandomGenerator;
import Utility.Interfaces.INoiseGenerator;
import Utility.Interfaces.IRandomGenerator;

public class TrajectoryCurvy001 extends AbstractTrajectory
{
    public IRandomGenerator RandomGenerator;
    public INoiseGenerator NoiseGenerator;
    public float MinAngleStep = 0.01f;
    public float MaxAngleStep = 0.05f;
    public float AngleNoiseOffset = 10.7641f;
    public float AngleNoiseMultiplier = 0.1f;
    private float _distanceStep;
    public int WalkCount = 5;
    public float WalkDistanceDeviation = 0f;

    private Vector2[] _points;

    public TrajectoryCurvy001()
    {
        this(new RandomGenerator(), new OpenSimplexNoiseGenerator());
    }

    public TrajectoryCurvy001(IRandomGenerator randomGenerator, INoiseGenerator noiseGenerator)
    {
        RandomGenerator = randomGenerator == null ? new RandomGenerator() : randomGenerator;
        NoiseGenerator = noiseGenerator == null ? new OpenSimplexNoiseGenerator() : noiseGenerator;
    }

    @Override
    public void Setup()
    {
        WalkTrajectory();
    }

    @Override
    public void Update(float elapsed)
    {
        
    }
    
    public Vector2 PointAt(float time)
    {
        time = Utility.Math.Clamp(time);

        var step = 1f / (float) Resolution;

        var firstIndex = (int)Math.floor(time / step);
        var lastIndex = firstIndex;

        if(lastIndex == _points.length)
        {
            lastIndex = firstIndex;
        }

        var remainder = time - ((float) firstIndex * step);

        var t = remainder * (1f / step);

        return new Vector2(
            _points[firstIndex].X + (_points[lastIndex].X - _points[firstIndex].X) * t,
            _points[firstIndex].Y + (_points[lastIndex].Y - _points[firstIndex].Y) * t);
    }
    
    public void WalkTrajectory()
    {
        if(_points != null && _points.length == Resolution)
        {
            return;
        }
        
        _points = null;
        _points = new Vector2[Resolution];

        _distanceStep = _length / (float) Resolution;

        float x = 0f, y = 0f;
        float a = 0f;
        int resolutionIndex = 0;
        float defaultWalkDistance = _length / (float) WalkCount;
        float walkDistance = defaultWalkDistance;

        while(resolutionIndex < Resolution)
        {
            float angleStep = (float)(Math.PI * (MinAngleStep + ((MaxAngleStep - MinAngleStep) * NoiseGenerator.Value((AngleNoiseOffset + y) * AngleNoiseMultiplier))));

            float walkDistanceOffset = RandomGenerator.Value(-defaultWalkDistance * WalkDistanceDeviation, defaultWalkDistance * WalkDistanceDeviation);

            walkDistance += walkDistanceOffset;

            var r = WalkCurve(x, y, walkDistance, a, angleStep, resolutionIndex);

            walkDistance = defaultWalkDistance - walkDistanceOffset;

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
            
            x +=  _distanceStep * Math.cos(angle);
            y +=  _distanceStep * Math.sin(angle);

            angle += angleStep;
            distance += _distanceStep;
            resolutionIndex++;
        }

        return new float[] { x, y, distance, angle, resolutionIndex };
    }
}
