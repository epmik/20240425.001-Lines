import java.util.function.Supplier;

public class SunBurst<TTrajectory extends AbstractTrajectory>
{
    private Supplier<TTrajectory> _trajectorySupplier;

    public SunBurst(Supplier<TTrajectory> trajectorySupplier)
    {
        _trajectorySupplier = trajectorySupplier;
    }

    public void Add(Supplier<TTrajectory> trajectorySupplier)
    {
        _trajectorySupplier = trajectorySupplier;
    }
}
