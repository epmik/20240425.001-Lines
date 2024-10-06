import Utility.Easing;
import Utility.OpenSimplexSummedNoiseGenerator;
import Utility.RandomGenerator;
import processing.core.PGraphics;

public interface ITrajectory 
{
    void Update(float elapsed);
    
    void Draw(PGraphics graphics);
}
