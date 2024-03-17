package co.edu.escuelaing;

import java.net.MalformedURLException;

import junit.framework.TestCase;

public class RemoteLogServiceInvokerTest
        extends TestCase {

    private static final String[] TEST_URL = { "http://test1",
            "http://test2", "http://test3" };

    public void testRoundRobin() throws MalformedURLException {
        RemoteLogServiceInvoker invoker = new RemoteLogServiceInvoker(TEST_URL);

        assertEquals(TEST_URL[1], invoker.RoundRobin().toString());
        assertEquals(TEST_URL[2], invoker.RoundRobin().toString());
        assertEquals(TEST_URL[0], invoker.RoundRobin().toString());
        assertEquals(TEST_URL[1], invoker.RoundRobin().toString());
    }
}
