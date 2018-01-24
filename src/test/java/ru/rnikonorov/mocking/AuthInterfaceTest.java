package ru.rnikonorov.mocking;

import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createMock;

@PrepareForTest({AuthInterfaceImpl.class, InnerInterfaceImpl.class})
public class AuthInterfaceTest extends PowerMockTestCase {

    private static final String USER = "user";
    private static final String PASSWORD = "password";

    private static final String WRONG_USER = "some_user";
    private static final String WRONG_PASSWORD = "some_password";


    /**
     * Strict test, username and password are correct
     */
    @Test
    public void doAuthCorrectTest() {
        final AuthInterface authInterface = new AuthInterfaceImpl();
        final boolean isAuth = authInterface.doAuth(USER, PASSWORD);
        //username and password are correct, we expect true
        Assert.assertEquals(true, isAuth);
    }

    /**
     * Strict test, username and password are incorrect
     */
    @Test
    public void doAuthIncorrectTest() {
        final AuthInterface authInterface = new AuthInterfaceImpl();
        final boolean isAuth = authInterface.doAuth(WRONG_USER, WRONG_PASSWORD);
        //username and password are incorrect, we expect false
        Assert.assertEquals(false, isAuth);
    }

    /**
     * Strict test, username and password are nulls
     */
    @Test
    public void doAuthNullsTest() {
        final AuthInterface authInterface = new AuthInterfaceImpl();
        final boolean isAuth = authInterface.doAuth(null, null);
        //username and password are nulls, we expect false
        Assert.assertEquals(false, isAuth);
    }

    /**
     * Mocking test, passing wrong values, but expecting true
     *
     * @throws Exception
     */
    @Test
    public void doAuthMockAbsolutlyWrongTest() throws Exception {

        final InnerInterfaceImpl innerInterfaceMock = createMock(InnerInterfaceImpl.class);
        PowerMock.expectNew(InnerInterfaceImpl.class).andReturn(innerInterfaceMock).anyTimes();
        expect(innerInterfaceMock.authUser(WRONG_USER, WRONG_PASSWORD)).andReturn(true).anyTimes();

        final AuthInterface authInterface = new AuthInterfaceImpl();
        PowerMock.replay(innerInterfaceMock, InnerInterfaceImpl.class);
        final boolean isAuth = authInterface.doAuth(WRONG_USER, WRONG_PASSWORD);
        PowerMock.verify(innerInterfaceMock);

        Assert.assertEquals(true, isAuth);
    }

    /**
     * Mocking test:
     * <ul>
     *     <li>passing correct values, expecting true</li>
     *     <li>passing wrong values, expecting false</li>
     *     <li>passing nulls, expecting false</li>
     * </ul>
     *
     * @throws Exception
     */
    @Test
    public void doAuthMockTest() throws Exception {

        final InnerInterfaceImpl innerInterfaceMock = PowerMock.createMock(InnerInterfaceImpl.class);
        PowerMock.expectNew(InnerInterfaceImpl.class).andReturn(innerInterfaceMock).anyTimes();
        expect(innerInterfaceMock.authUser(USER, PASSWORD)).andReturn(true).anyTimes();
        expect(innerInterfaceMock.authUser(WRONG_USER, WRONG_PASSWORD)).andReturn(false).anyTimes();
        expect(innerInterfaceMock.authUser(null, null)).andReturn(false).anyTimes();

        final AuthInterface authInterface = new AuthInterfaceImpl();
        PowerMock.replay(innerInterfaceMock, InnerInterfaceImpl.class);
        final boolean isAuthCorrect = authInterface.doAuth(USER, PASSWORD);
        final boolean isAuthIncorrect = authInterface.doAuth(WRONG_USER, WRONG_PASSWORD);
        final boolean isAuthNulls = authInterface.doAuth(null, null);
        PowerMock.verify(innerInterfaceMock);

        Assert.assertEquals(true, isAuthCorrect);
        Assert.assertEquals(false, isAuthIncorrect);
        Assert.assertEquals(false, isAuthNulls);
    }
}
