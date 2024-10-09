import Geometry.Vector2;
import Utility.Easing;
import Utility.OpenSimplexSummedNoiseGenerator;
import Utility.RandomGenerator;
import processing.core.PGraphics;

public interface ITrajectory 
{
    float Length();

    void Length(float length);

    // float Scale();

    // void Scale(float scale);

    void Setup();
    
    void Update(float elapsed);
    
    Vector2 PointAt(float time);
}
