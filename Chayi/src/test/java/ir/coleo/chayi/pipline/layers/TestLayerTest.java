package ir.coleo.chayi.pipline.layers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import ir.coleo.chayi.Chayi;
import ir.coleo.chayi.pipline.NetworkData;
import ir.coleo.chayi.pipline.SimpleIdlingResource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestLayerTest {

    public TestLayer testLayer;
    public SimpleIdlingResource resource;

    @Before
    public void before() {
        resource = new SimpleIdlingResource();
        testLayer = new TestLayer(null, resource);
    }

    @Test
    public void testOne() {
        NetworkData data = new NetworkData(null, Chayi.class);
        data = testLayer.before(data);
        assertFalse(resource.isIdleNow());
        data = testLayer.work(data);
        data = testLayer.after(data);
        Assert.assertTrue(resource.isIdleNow());
    }

    @Test
    public void testTwo() {
        NetworkData data = new NetworkData(null, Chayi.class);
        NetworkData dataTwo = new NetworkData(null, Chayi.class);

        data = testLayer.before(data);
        dataTwo = testLayer.before(dataTwo);
        assertFalse(resource.isIdleNow());
        data = testLayer.work(data);
        data = testLayer.after(data);
        assertFalse(resource.isIdleNow());
        dataTwo = testLayer.work(dataTwo);
        dataTwo = testLayer.after(dataTwo);
        Assert.assertTrue(resource.isIdleNow());
    }

    public static @DataPoints
    int[] counts = {1, 2, 5, 20, 40};

    @Theory
    public void testConcurrent(int count) throws InterruptedException {

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> {
                NetworkData data = new NetworkData(null, Chayi.class);
                data = testLayer.before(data);
                assertFalse(resource.isIdleNow());
                data = testLayer.work(data);
                data = testLayer.after(data);
            });
            threads.add(thread);
        }

        for (Thread thread : threads)
            thread.start();

        for (Thread thread : threads)
            thread.join();


        assertTrue(resource.isIdleNow());
    }

}