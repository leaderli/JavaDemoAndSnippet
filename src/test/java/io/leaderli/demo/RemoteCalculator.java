package io.leaderli.demo;

import java.rmi.Remote;
import java.rmi.RemoteException;

interface RemoteCalculator extends Remote {
    int add(int a, int b) throws RemoteException;
}
