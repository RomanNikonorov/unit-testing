package ru.rnikonorov.mocking;

public interface AuthInterface {
    /**
     * Interface method to do auth
     * @param user username
     * @param password password
     * @return true if did auth
     */
    boolean doAuth(String user, String password);
}
