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
    public void test_calculateMean_NoInt () {
        MathUtils instance = MathUtils.getInstance();
        int[] t = new int[0];
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> instance.calculateMean(t));
    }

    @Test
    public void test_calculateMean_oneInt () {
        MathUtils instance = MathUtils.getInstance();
        int[] t = new int[]{1};

        double mean = instance.calculateMean(t);

        assertEquals(1, mean);
    }

    @Test
    public void test_calculateMean_threeInt () {
        MathUtils instance = MathUtils.getInstance();
        int[] t = new int[]{1, 2, 4};

        double mean = instance.calculateMean(t);

        assertEquals(2.33333, mean, 0.00001);
    }
}
