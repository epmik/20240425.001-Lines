import Geometry.Vector2;

public abstract class AbstractTrajectory implements ITrajectory
{
    public int Resolution = 250;
    protected float _length = 1000f;

    public float Length()
    {
        return _length;
    }
    
    public void Length(float length)
    {
        _length = length;
    }

    public void Setup()
    {
        
    }

    public void Update(float elapsed)
    {
        
    }
    
    public abstract Vector2 PointAt(float time);
}
