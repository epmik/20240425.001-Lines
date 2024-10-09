import Geometry.Vector2;
import processing.core.PGraphics;

public interface ISunRay 
{
    ITrajectory Trajectory();

    void Trajectory(ITrajectory trajectory);

    int Color();

    void Color(int color);

    int ColorAt(float time, float x, float y);

    void Setup();

    void Update(float elapsed);
    
    void Draw(PGraphics graphics);
}
