package Utility;

import java.util.Date;

public class OpenSimplexSummedNoiseGenerator extends SummedNoiseGenerator<OpenSimplexNoiseGenerator> 
{
    public OpenSimplexSummedNoiseGenerator()
    {
        this(SeedRoot++);
    }
    
	public OpenSimplexSummedNoiseGenerator(long seed)
    {
        super(new OpenSimplexNoiseGenerator(seed));
    }
}