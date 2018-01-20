package ru.rnikonorov.mocking;

public class InnerInterfaceImpl {

    private static final String USER_NAME = "user";
    private static final String USER_PASSWORD = "password";

    InnerInterfaceImpl() {
        //default constructor
    }

    /**
     * do auth user
     * @param userName user's name
     * @param password user's password
     * @return true if auth is successful
     */
    public boolean authUser(final String userName, final String password) {
        return userName != null && password != null && userName.equals(USER_NAME) && password.equals(USER_PASSWORD);
    }
}
