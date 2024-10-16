import Utility.Color;
import processing.core.PGraphics;

public interface ISunRay extends IColorProvider
{
    ITrajectory Trajectory();

    void Trajectory(ITrajectory trajectory);

    IColorProvider ColorProvider();

    void ColorProvider(IColorProvider colorProvider);

    float Angle();

    void Angle(float angle);

    int Resolution();

    void Resolution(int resolution);

    Color Color();

    void Color(Color color);

    void Setup();

    void Update(float elapsed);
    
    void Draw(PGraphics graphics);

    void ResetDrawState();

    boolean IsAlive();
}
