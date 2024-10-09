import Geometry.Vector2;

public abstract class AbstractTrajectory implements ITrajectory
{
    public int Resolution = 250;
    // protected float _scale = 1f;
    protected float _length = 1000f;

    // public float Scale()
    // {
    //     return _scale;
    // }
    
    // public void Scale(float scale)
    // {
    //     _scale = scale;
    // }

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
