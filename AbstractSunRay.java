import Utility.Color;
import processing.core.PGraphics;

public abstract class AbstractSunRay implements ISunRay 
{
    protected ITrajectory _trajectory;
    protected IColorProvider _colorProvider;
    protected Color _color;
    protected int _resolution = 1000;
    protected float _angle = 0f;
    protected float _timeStep = 1f / _resolution;
    protected int _timeStepsPerSecond = _resolution / 2;
    protected float _currentTime = 0;
    protected float _targetTime = 0;

    public AbstractSunRay()
    {
        super();
    }

    public ITrajectory Trajectory()
    {
        return _trajectory;
    }

    public void Trajectory(ITrajectory trajectory)
    {
        _trajectory = trajectory;
    }

    public IColorProvider ColorProvider()
    {
        return _colorProvider;
    }

    public void ColorProvider(IColorProvider colorProvider)
    {
        _colorProvider = colorProvider;
    }

    public int Resolution()
    {
        return _resolution;
    }

    public void Resolution(int resolution)
    {
        _resolution = resolution;
        _timeStep = 1f / _resolution;
    }

    public float Angle()
    {
        return _angle;
    }

    public void Angle(float angle)
    {
        _angle = angle;
    }

    public Color Color()
    {
        return _color;
    }

    public void Color(Color color)
    {
        _color = color;
    }

    public void ResetDrawState()
    {
        _currentTime = 0;
        _targetTime = 0;
    }

    public Color ColorAt(float time, float x, float y)
    {
        return _colorProvider == null ? _color : _colorProvider.ColorAt(time, x, y);
    }

    public Color ColorAt(float time)
    {
        return _colorProvider == null ? _color : _colorProvider.ColorAt(time);
    }

    public Color ColorAt(float x, float y)
    {
        return _colorProvider == null ? _color : _colorProvider.ColorAt(x, y);
    }

    public void Setup()
    {
        _trajectory.Setup();
    }

    public boolean IsAlive()
    {
        return _currentTime <= 1f;
    }

    public void Update(float elapsed)
    {
        _targetTime = Utility.Math.Clamp(_currentTime + (elapsed * _timeStep * _timeStepsPerSecond));
    }
    
    public abstract void Draw(PGraphics graphics);
}
