import processing.core.PGraphics;

public interface IGraphicsPass
{
    void Setup(PGraphics graphics);

    void Update(float elapsed);

    PGraphics Apply(PGraphics source);

    void Apply(PGraphics source, PGraphics destination);
}
