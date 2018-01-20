package ru.rnikonorov.mocking;

public class AuthInterfaceImpl implements AuthInterface {

    AuthInterfaceImpl() {
        //default constructor
    }

    /**
     * {@inheritDoc}
     */
    public boolean doAuth(final String userName, final String password) {
        final InnerInterfaceImpl innerInterface = new InnerInterfaceImpl();
        return innerInterface.authUser(userName, password);
    }

}
