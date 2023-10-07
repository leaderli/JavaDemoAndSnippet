package io.leaderli.demo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

class CalculatorImpl extends UnicastRemoteObject implements RemoteCalculator {
    public CalculatorImpl() throws RemoteException {
        // Constructor must throw RemoteException
    }

    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
