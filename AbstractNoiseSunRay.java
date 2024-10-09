import Utility.OpenSimplexSummedNoiseGenerator;

public abstract class AbstractNoiseSunRay extends AbstractSunRay 
{
    public OpenSimplexSummedNoiseGenerator NoiseGenerator;

    public AbstractNoiseSunRay()
    {
        NoiseGenerator = new OpenSimplexSummedNoiseGenerator();
    }
}
