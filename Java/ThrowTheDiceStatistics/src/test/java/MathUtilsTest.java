package test.java;

import main.java.MathUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class MathUtilsTest {

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
    public void test_calculateMean_oneInt () {
        MathUtils instance = MathUtils.getInstance();
        double[] t = new double[]{1};

        double mean = instance.calculateMean(t);

        assertEquals(1, mean);
    }

    @Test
    public void test_calculateMean_threeInt () {
        MathUtils instance = MathUtils.getInstance();
        double[] t = new double[]{1, 2, 4};

        double mean = instance.calculateMean(t);

        assertEquals(2.33333, mean, 0.00001);
    }

    @Test
    public void test_calculateVariance_inputListIsEmpty(){
        MathUtils instance = MathUtils.getInstance();
        double[] t = new double[0];

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> instance.calculateVariance(t));
    }

    @Test
    public void test_calculateMean_fourInt(){
        MathUtils instance = MathUtils.getInstance();
        double[] t = getValues();

        double mean = instance.calculateMean(t);

        assertEquals(7, mean, 0.1);
    }



    @Test
    public void test_calculateVariance_fourInt(){
        MathUtils instance = MathUtils.getInstance();
        double[] t = getValues();

        double variance = instance.calculateVariance(t) ;

        assertEquals(8, variance, 0.1);
    }

    @Test
    public void test_calculateStandardDeviation_fourInt(){
        MathUtils instance = MathUtils.getInstance();
        double[] t = getValues();

        double standardDeviation = instance.calculateStandardDeviation(t) ;

        assertEquals(2.82842, standardDeviation, 0.00001);
    }

    private static double[] getValues() {
        return new double[]{3, 5, 7, 9, 11};
    }
}
