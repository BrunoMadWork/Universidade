/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.rmi.*;
public interface Rmiinterface extends Remote {
        public String say() throws RemoteException;
        public void ConnectDatabase(String textArea1,String textField1) throws RemoteException;
        public String Button1Read() throws RemoteException;
        public String Button2Read() throws RemoteException;
        public String Button3Read() throws RemoteException;
        public Boolean Login(String user, String pass) throws RemoteException;
        public void ConnectDatabaseOrderInfo(String textArea1, String textField1) throws RemoteException ;
        public void newOrder(String Field1,String Field3,String Field4,String Field5,String Field6,String Area1,String Area2,String Area3,String Area4) throws RemoteException;
        public String getLogins() throws RemoteException;
        public String getLogs() throws RemoteException;
        public String getShip() throws RemoteException;




                }
           