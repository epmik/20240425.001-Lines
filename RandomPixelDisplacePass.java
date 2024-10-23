import Utility.Interfaces.IRandomGenerator;
import processing.core.PGraphics;

public class RandomPixelDisplacePass extends AbstractGraphicsPass
{
    public int MinHorizontalDisplaceAmount = 1;
    public int MaxHorizontalDisplaceAmount = 16;

    public int MinVerticalDisplaceAmount = 1;
    public int MaxVerticalDisplaceAmount = 16;

    public IRandomGenerator RandomGenerator = new Utility.RandomGenerator();

    public RandomPixelDisplacePass()
    {
        super();
    }

    public RandomPixelDisplacePass(PGraphics graphics)
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
            for(var h = 0; h < source.pixelHeight; h++)
            {
                var horizontalAmount = RandomGenerator.Value(MinHorizontalDisplaceAmount, MaxHorizontalDisplaceAmount);
                var verticalAmount = RandomGenerator.Value(MinVerticalDisplaceAmount, MaxVerticalDisplaceAmount);

                CopyPixel(source, _intermediateGraphics, h * source.pixelWidth + w, (h + verticalAmount) * source.pixelWidth + w + horizontalAmount);
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
