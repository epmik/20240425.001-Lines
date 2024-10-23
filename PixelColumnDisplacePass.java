import Utility.Interfaces.IRandomGenerator;
import processing.core.PGraphics;

public class PixelColumnDisplacePass extends AbstractGraphicsPass
{
    public int MinShiftAmount = 1;
    public int MaxShiftAmount = 16;

    public IRandomGenerator RandomGenerator = new Utility.RandomGenerator();

    public PixelColumnDisplacePass()
    {
        super();
    }

    public PixelColumnDisplacePass(PGraphics graphics)
    {
        super(graphics);
    }

    public void Apply(PGraphics source, PGraphics destination)
    {
        CopyPixelBuffer(source, _intermediateGraphics);

        source.loadPixels();

        _intermediateGraphics.loadPixels();

        for(var w = 0; w < source.pixelWidth; w++)
        {
            var amount = RandomGenerator.Value(MinShiftAmount, MaxShiftAmount);

            for(var h = 0; h < source.pixelHeight; h++)
            {
                CopyPixel(source, _intermediateGraphics, h * source.pixelWidth + w, (h + amount) * source.pixelWidth + w);
            }
        }

        _intermediateGraphics.updatePixels();

        CopyPixelBuffer(_intermediateGraphics, destination);
    }

    private static void CopyPixelBuffer(PGraphics source, PGraphics destination)
    {
        destination.beginDraw();

        // copy the source into the intermediate buffer
        destination.image(source, 0, 0, source.width, source.height);

        destination.flush();

        destination.endDraw();
    }

    private static void CopyPixel(PGraphics source, PGraphics destination, int sourceIndex, int destinationIndex)
    {
        if(sourceIndex < 0 || sourceIndex >= source.pixelWidth * source.pixelHeight || destinationIndex < 0 || destinationIndex >= destination.pixelWidth * destination.pixelHeight)
        {
            return;
        }

        destination.pixels[destinationIndex] = source.pixels[sourceIndex];
    }
}
