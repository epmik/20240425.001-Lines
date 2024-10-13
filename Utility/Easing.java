package Utility;

public class Easing 
{
    public interface IEasingFunction {
        float Ease(float time);
    }
    
    private static Easing.IEasingFunction[] _allFunctions = new IEasingFunction[] { 
        Easing::Linear,
        
        Easing::InQuadratic,
        Easing::InCubic,
        Easing::InQuartic,
        Easing::InQuintic,
        Easing::InExpo,
        Easing::InSine,
        Easing::InCircular,
        Easing::InBack,
        Easing::InElastic,
        Easing::InBounce,
        
        Easing::OutQuadratic,
        Easing::OutCubic,
        Easing::OutQuartic,
        Easing::OutQuintic,
        Easing::OutExpo,
        Easing::OutSine,
        Easing::OutCircular,
        Easing::OutBack,
        Easing::OutElastic,
        Easing::OutBounce,
        
        Easing::InOutQuadratic,
        Easing::InOutCubic,
        Easing::InOutQuartic,
        Easing::InOutQuintic,
        Easing::InOutExpo,
        Easing::InOutSine,
        Easing::InOutCircular,
        Easing::InOutBack,
        Easing::InOutElastic,
        Easing::InOutBounce,
    };

    private static Easing.IEasingFunction[] _inFunctions = new IEasingFunction[] { 
        Easing::InQuadratic,
        Easing::InCubic,
        Easing::InQuartic,
        Easing::InQuintic,
        Easing::InExpo,
        Easing::InSine,
        Easing::InCircular,
        Easing::InBack,
        Easing::InElastic,
        Easing::InBounce,
    };

    private static Easing.IEasingFunction[] _outFunctions = new IEasingFunction[] { 
        Easing::OutQuadratic,
        Easing::OutCubic,
        Easing::OutQuartic,
        Easing::OutQuintic,
        Easing::OutExpo,
        Easing::OutSine,
        Easing::OutCircular,
        Easing::OutBack,
        Easing::OutElastic,
        Easing::OutBounce,
    };

    private static Easing.IEasingFunction[] _inOutFunctions = new IEasingFunction[] {
        Easing::InOutQuadratic,
        Easing::InOutCubic,
        Easing::InOutQuartic,
        Easing::InOutQuintic,
        Easing::InOutExpo,
        Easing::InOutSine,
        Easing::InOutCircular,
        Easing::InOutBack,
        Easing::InOutElastic,
        Easing::InOutBounce,
    };

    public static Easing.IEasingFunction[] Functions()
    {
        return _allFunctions;
    }

    public static Easing.IEasingFunction[] InFunctions()
    {
        return _inFunctions;
    }

    public static Easing.IEasingFunction[] OutFunctions()
    {
        return _outFunctions;
    }

    public static Easing.IEasingFunction[] InOutFunctions()
    {
        return _inOutFunctions;
    }

    // static Easing()
    // {

    // }

    // http://gizma.com/easing/
    // https://github.com/jesusgollonet/processing-penner-easing/blob/master/src/Linear.java
    // http://vitiy.info/easing-functions-for-your-animations/
    // https://joshondesign.com/2013/03/01/improvedEasingEquations
    // https://gist.github.com/gre/1650294

    // https://easings.net/ !!!!
    
    public static float Linear(float time){
        return time;
    }
    
    public static float Linear(float time, float from, float to){
        return from + (to - from) * time;
    }




    // quadratic easing in - accelerating from zero velocity
    public static float InQuadratic (float time) {
        return time * time;
    };		

    // quadratic easing out - decelerating to zero velocity
    public static float OutQuadratic (float time) {
        return 1f - InQuadratic(1f-time);
    };        

    // quadratic easing in/out - acceleration until halfway, then deceleration
    public static float InOutQuadratic (float time) {
        if(time < 0.5f) 
        {
            return InQuadratic(time*2.0f)/2.0f;
        }
        return 1-InQuadratic((1f-time)*2f)/2f;            
    };




    // cubic easing in - accelerating from zero velocity
    public static float InCubic (float time) {
        return (float)java.lang.Math.pow(time, 3);
    };        

    // cubic easing out - decelerating to zero velocity
    public static float OutCubic (float time) {
        return 1f - InCubic(1f-time);
    };

    // cubic easing in/out - acceleration until halfway, then deceleration
    public static float InOutCubic (float time) {
        if(time < 0.5f) 
        {
            return InCubic(time*2.0f)/2.0f;
        }
        return 1-InCubic((1f-time)*2f)/2f;            
    };
    
    


    // quartic easing in - accelerating from zero velocity
    public static float InQuartic (float time) {
        return (float)java.lang.Math.pow(time, 4);
    };

    // quartic easing out - decelerating to zero velocity
    public static float OutQuartic (float time) {
        return 1f - InQuartic(1f-time);
    };		

    // quartic easing in/out - acceleration until halfway, then deceleration
    public static float InOutQuartic (float time) {
        if(time < 0.5) 
        {
            return InQuartic(time*2.0f)/2.0f;
        }
        return 1-InQuartic((1f-time)*2f)/2f;            
    };




    // quintic easing in - accelerating from zero velocity
    public static float InQuintic (float time) {
        return (float)java.lang.Math.pow(time, 5);
    };        

    // quintic easing out - decelerating to zero velocity
    public static float OutQuintic (float time) {
        return 1f - InQuintic(1-time);
    };            

    // quintic easing in/out - acceleration until halfway, then deceleration
    public static float InOutQuintic (float time) {
        if(time < 0.5f) 
        {
            return InQuintic(time*2.0f)/2.0f;
        }
        return 1-InQuintic((1f-time)*2f)/2f;            
    };
		



    public static float InExpo (float time) {
        return time == 0f ? 0f : (float)java.lang.Math.pow(2, 10f * time - 10f);
    };

    public static float OutExpo (float time) {
        return 1f - InExpo(1f-time);
    };

    public static float InOutExpo (float time) {
        if(time < 0.5f) 
        {
            return InExpo(time*2.0f)/2.0f;
        }
        return 1f-InExpo((1f-time)*2f)/2f;            
    };



    // sinusoidal easing in - accelerating from zero velocity
    public static float InSine (float time) {
        return -(float)java.lang.Math.cos(time/1f * (Math.Pi/2f)) + 1f;
    };

    // sinusoidal easing out - decelerating to zero velocity
    public static float OutSine (float time) {
        return (float)java.lang.Math.sin(time/1f * (Math.Pi/2f));
    };

    // sinusoidal easing out - decelerating to zero velocity
    public static float InOutSine (float time) {
        return -(float)((java.lang.Math.cos(Math.Pi * time) - 1f) / 2f);
    };




    public static float InCircular (float time) {
        return 1f - (float)java.lang.Math.sqrt(1f - java.lang.Math.pow(time, 2));
    };

    public static float OutCircular (float time) {
        return 1f - InCircular(1f-time);
    };

    public static float InOutCircular (float time) {
        if(time < 0.5f) 
        {
            return InCircular(time*2.0f)/2.0f;
        }
        return 1f-InCircular((1f-time)*2f)/2f;            
    };




    public static float InBack (float time) {
        final float c1 = 1.70158f;
        final float c3 = c1 + 1f;
        
        return c3 * time * time * time - c1 * time * time;
    };

    public static float OutBack (float time) {
        return 1f - InBack(1f-time);
    };

    public static float InOutBack (float time) {
        if(time < 0.5f) 
        {
            return InBack(time*2.0f)/2.0f;
        }
        return 1f-InBack((1f-time)*2f)/2f;            
    };




    public static float InElastic (float time) {
        final float c4 = (float)((2f * Math.Pi) / 3f);

        return time == 0f
            ? 0f
            : time == 1f
            ? 1f
            : -(float)(java.lang.Math.pow(2, 10f * time - 10f) * java.lang.Math.sin((time * 10f - 10.75f) * c4));
    };

    public static float OutElastic (float time) {
        return 1f - InElastic(1-time);
    };

    public static float InOutElastic (float time) {
        if(time < 0.5f) 
        {
            return InElastic(time*2.0f)/2.0f;
        }
        return 1f-InElastic((1f-time)*2f)/2f;            
    };




    public static float InBounce (float time) {
        return 1f - OutBounce(1f - time);
    };

    public static float OutBounce (float time) {
        final float n1 = 7.5625f;
        final float d1 = 2.75f;

        if (time < 1f / d1) {
            return n1 * time * time;
        } else if (time < 2f / d1) {
            return n1 * (time -= 1.5f / d1) * time + 0.75f;
        } else if (time < 2.5f / d1) {
            return n1 * (time -= 2.25f / d1) * time + 0.9375f;
        } else {
            return n1 * (time -= 2.625f / d1) * time + 0.984375f;
        }
    };

    public static float InOutBounce (float time) {
        return time < 0.5f
            ? (1f - OutBounce(1f - 2f * time)) / 2f
            : (1f + OutBounce(2f * time - 1f)) / 2f;   
    };



    // public static float EaseInOutBounce (float time) {
    //     if(time < 0.5) 
    //     {
    //         return EaseOutBounce(time*2.0)/2.0;
    //     }
    //     return 1-EaseOutBounce((1-time)*2)/2;            
    // };

    // public static float PikeSine (float time) {
    //     if(time < 0.5) 
    //     {
    //         return EaseInSine(time*2.0);
    //     }
    //     return EaseInSine((1-time)*2);            
    // };

    // public static float PikeCosine (float time) {
    //     if(time < 0.5) 
    //     {
    //         return EaseOutSine(time*2.0);
    //     }
    //     return EaseOutSine((1-time)*2);            
    // };

            

    // // exponential easing in - accelerating from zero velocity
    // public static float EaseInExpo (float time, float min, float length, float duration) {
    //     return c * Math.pow( 2, 10 * (t/d - 1) ) + b;
    // };


    // // exponential easing out - decelerating to zero velocity
    // public static float EaseOutExpo (float time, float min, float length, float duration) {
    //     return c * ( -Math.pow( 2, -10 * t/d ) + 1 ) + b;
    // };
            

    // // exponential easing in/out - accelerating until halfway, then decelerating
    // public static float EaseInOutExpo (float time, float min, float length, float duration) {
    //     t /= d/2;
    //     if (t < 1) return c/2 * Math.pow( 2, 10 * (t - 1) ) + b;
    //     t--;
    //     return c/2 * ( -Math.pow( 2, -10 * t) + 2 ) + b;
    // };
            

    // // circular easing in - accelerating from zero velocity
    // public static float EaseInCirc (float time, float min, float length, float duration) {
    //     t /= d;
    //     return -c * (Math.sqrt(1 - t*t) - 1) + b;
    // };
            

    // // circular easing out - decelerating to zero velocity
    // public static float EaseOutCirc (float time, float min, float length, float duration) {
    //     t /= d;
    //     t--;
    //     return c * Math.sqrt(1 - t*t) + b;
    // };
            

    // // circular easing in/out - acceleration until halfway, then deceleration
    // public static float EaseInOutCirc (float time, float min, float length, float duration) {
    //     t /= d/2;
    //     if (t < 1) return -c/2 * (Math.sqrt(1 - t*t) - 1) + b;
    //     t -= 2;
    //     return c/2 * (Math.sqrt(1 - t*t) + 1) + b;
    // };

}