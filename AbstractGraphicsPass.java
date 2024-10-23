import processing.core.PGraphics;

public abstract class AbstractGraphicsPass implements IGraphicsPass
{
    public PGraphics _intermediateGraphics = null;

    public AbstractGraphicsPass()
    {
    }

    public AbstractGraphicsPass(PGraphics graphics)
    {
        Setup(graphics);
    }

    public void Setup(PGraphics graphics)
    {
        if(graphics != null && (_intermediateGraphics == null || _intermediateGraphics.width != graphics.width || _intermediateGraphics.height != graphics.height))
        {
            _intermediateGraphics = null;

            _intermediateGraphics = AbstractSketch.Instance.CreateGraphics(graphics);
        }
    }

    public void Update(float elapsed)
    {

    }

    public PGraphics Apply(PGraphics source)
    {
        Setup(source);        

        Apply(source, _intermediateGraphics);

        return _intermediateGraphics;
    }

    public abstract void Apply(PGraphics source, PGraphics destination);
}
