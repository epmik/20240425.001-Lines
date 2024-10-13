import Geometry.Vector2;
import Utility.RandomGenerator;
import processing.core.PGraphics;

public abstract class AbstractSunRay implements ISunRay 
{
    private ITrajectory _trajectory;
    private int _color;
    public int Resolution = 1000;
    public float Angle = 0f;

    public AbstractSunRay()
    {
        super();

        // Angle = RandomGenerator.Default.Value(0, Math.PI * 2f);
    }

    public ITrajectory Trajectory()
    {
        return _trajectory;
    }

    public void Trajectory(ITrajectory trajectory)
    {
        _trajectory = trajectory;
    }

    public int Color()
    {
        return _color;
    }

    public void Color(int color)
    {
        _color = color;
    }

    public int ColorAt(float time, float x, float y)
    {
        return _color;
    }

    public void Setup()
    {
        _trajectory.Setup();
    }

    public void Update(float elapsed)
    {

    }
    
    public abstract void Draw(PGraphics graphics);
}
