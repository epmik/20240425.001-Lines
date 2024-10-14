import Geometry.Vector2;
import processing.core.PGraphics;

public interface ISunRay 
{
    ITrajectory Trajectory();

    void Trajectory(ITrajectory trajectory);

    float Angle();

    void Angle(float angle);

    int Resolution();

    void Resolution(int resolution);

    int Color();

    void Color(int color);

    int ColorAtTime(float time, float x, float y);

    void Setup();

    void Update(float elapsed);
    
    void Draw(PGraphics graphics);

    void ResetDrawState();

    boolean IsAlive();
}
