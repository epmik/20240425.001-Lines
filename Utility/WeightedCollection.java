package Utility;

import java.util.NavigableMap;
import java.util.TreeMap;

import Utility.Interfaces.IRandomGenerator;

public class WeightedCollection<TObject> 
{
    private final NavigableMap<Double, TObject> _map = new TreeMap<Double, TObject>();
    private final IRandomGenerator _randomGenerator;
    private double _total = 0;

    public WeightedCollection() 
    {
        this(new RandomGenerator());
    }

    public WeightedCollection(long seed) 
    {
        this(new RandomGenerator(seed));
    }

    public WeightedCollection(RandomGenerator randomGenerator) 
    {
        _randomGenerator = randomGenerator;
    }

    public WeightedCollection<TObject> Add(double weight, TObject result) 
    {
        if (weight <= 0)
            return this;
        
            _total += weight;
        
        _map.put(_total, result);
            
        return this;
    }

    public TObject Next() 
    {
        double value = _randomGenerator.Value() * _total;
        return _map.higherEntry(value).getValue();
    }

    public void Clear() 
    {
        _map.clear();
    }
}
