import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public interface IBackground
{
    void Setup();

    void Update(float elapsed);

    void Draw(PGraphics graphics);
}
