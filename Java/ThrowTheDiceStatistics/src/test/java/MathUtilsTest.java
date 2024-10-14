package test.java;

import main.java.MathUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {

    private static final double[] VALUES =  new double[]{3, 5, 7, 9, 11};
    private static final double MEAN_OF_THE_VALUES = 7;
    private static final double VARIANCE_OF_THE_VALUES = 8;
    private static final double STANDARD_DEVIATION_OF_THE_VALUES = 2.82842;


    // TODO I copy paste this logic from Stack Overflow - I want to review deeper
    @Test
    public void test_constructor_private () throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<MathUtils> constructor = MathUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void test_getInstance_returnNotNull () {
        MathUtils instance = MathUtils.getInstance();
        assertNotNull(instance);
    }

    @Test
    public void test_calculateMean_inputListIsEmpty () {
        MathUtils instance = MathUtils.getInstance();
        double[] t = new double[0];

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> instance.calculateMean(t));
    }

    @Test
    public void test_calculateMean_FiveInt(){
        MathUtils instance = MathUtils.getInstance();

        double mean = instance.calculateMean(VALUES);

        assertEquals(MEAN_OF_THE_VALUES, mean, 0.1);
    }

    @Test
    public void test_calculateVariance_inputListIsEmpty(){
        MathUtils instance = MathUtils.getInstance();
        double[] t = new double[0];

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> instance.calculateVariance(t));
    }

    @Test
    public void test_calculateVariance_fourInt(){
        MathUtils instance = MathUtils.getInstance();

        double variance = instance.calculateVariance(VALUES) ;

        assertEquals(VARIANCE_OF_THE_VALUES, variance, 0.1);
    }

    @Test
    public void test_calculateVariance_withMeanFourInt(){
        MathUtils instance = MathUtils.getInstance();

        double mean = instance.calculateMean(VALUES);
        double variance = instance.calculateVariance(VALUES, mean) ;

        assertEquals(VARIANCE_OF_THE_VALUES, variance, 0.1);
    }

    @Test
    public void test_calculateStandardDeviation_fourInt(){
        MathUtils instance = MathUtils.getInstance();

        double standardDeviation = instance.calculateStandardDeviation(VALUES) ;

        assertEquals(STANDARD_DEVIATION_OF_THE_VALUES, standardDeviation, 0.00001);
    }

    @Test
    public void test_calculateStandardDeviation_fromVariance(){
        MathUtils instance = MathUtils.getInstance();

        double variance = instance.calculateVariance(VALUES) ;
        double standardDeviation = instance.calculateStandardDeviation(variance) ;

        assertEquals(STANDARD_DEVIATION_OF_THE_VALUES, standardDeviation, 0.00001);
    }
}
