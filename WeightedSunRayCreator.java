import Utility.WeightedCollection;
import java.util.function.Supplier;

public class WeightedSunRayCreator 
{
  private WeightedCollection<Supplier<AbstractSunRay>> _sunRayCreaterWeightedCollection = new WeightedCollection<Supplier<AbstractSunRay>>();

  public void Add(double weight, Supplier<AbstractSunRay> supplier)
  {
    _sunRayCreaterWeightedCollection.Add(weight, supplier);
  }

  public AbstractSunRay Next()
  {
    return _sunRayCreaterWeightedCollection.Next().get();
  }
}