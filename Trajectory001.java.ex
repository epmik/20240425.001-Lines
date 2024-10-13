import Utility.Easing;
import Utility.OpenSimplexSummedNoiseGenerator;
import Utility.RandomGenerator;
import processing.core.PGraphics;

public class Trajectory001 implements ITrajectory
{
    public float Angle;
    public float Time;
    public float NormalizedTime;
    public float TimeStep = 0.1f;
    public float TotalTime = 40f;
    public float Speed = 1f;
    public float MaxDeviation = 200f;
    public float NoiseMultiplier = 0.01f;
    public RandomGenerator RandomGenerator;
    public OpenSimplexSummedNoiseGenerator NoiseGenerator;
    public boolean EaseIn = true;

    public Trajectory001()
    {
        RandomGenerator = new RandomGenerator();
        NoiseGenerator = new OpenSimplexSummedNoiseGenerator();

        Angle = (float)RandomGenerator.Value(Math.PI * 2f);
    }

    public void Update(float elapsed)
    {
        Time += elapsed;
    }
    
    public void Draw(PGraphics graphics)
    {
        graphics.pushMatrix();

        graphics.rotate(Angle);

        float normalizedTime = 0f;
        float t = 0f;
        float y = 0f;
        float offset = (float)((MaxDeviation * 0.5f) + (NoiseGenerator.Value(y * NoiseMultiplier) * MaxDeviation)); 

        while(t <= TotalTime)
        {
            normalizedTime = t / TotalTime;

            float v = (float)NoiseGenerator.Value(y * NoiseMultiplier);

            float x = (EaseIn ? (float)Easing.InCubic(normalizedTime) : 1f) * (offset + (-(MaxDeviation * 0.5f) + (v * MaxDeviation)));

            graphics.noStroke();
            graphics.fill(1f, 0f, 0f);
            graphics.circle(x, y, 2);

            t += TimeStep;
            y += Speed;
        }

        graphics.popMatrix();
    }
    
    void DrawAtTime(PGraphics graphics)
    {
        graphics.beginDraw();

        graphics.pushMatrix();

        float normalizedTime = 0f;
        float t = 0f;
        float y = 0f;
        float offset = (float)((MaxDeviation * 0.5f) + (NoiseGenerator.Value(y * NoiseMultiplier) * MaxDeviation)); 

        normalizedTime = t / TotalTime;

        float v = (float)NoiseGenerator.Value(y * NoiseMultiplier);

        float x = (EaseIn ? (float)Easing.InCubic(normalizedTime) : 1f) * (offset + (-(MaxDeviation * 0.5f) + (v * MaxDeviation)));

        graphics.noStroke();
        graphics.fill(1f, 0f, 0f);
        graphics.circle(x, y, 2);

        y += Speed;

        graphics.popMatrix();

        graphics.endDraw();
    }
}
