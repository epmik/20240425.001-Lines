import Geometry.Vector2;

public class TrajectoryStraight001 extends AbstractTrajectory
{
    public TrajectoryStraight001() 
    {
        super();

        Resolution = 1;
    }
    
    public Vector2 PointAt(float time)
    {
        time = Utility.Math.Clamp(time);

        return new Vector2(0f, _length * time);
    }
}
