/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import java.rmi.registry.LocateRegistry;

    public class ControllerOrder {
        public static void main(String[] args)  throws RemoteException {
                try {
                       System.getProperties().put("java.security.policy", "java.policy");
                     System.setSecurityManager(new RMISecurityManager());    
                       Rmi imp=new Rmi();
                       LocateRegistry.createRegistry(7028);

                     
                    Naming.rebind("rmi://127.0.0.1:7028/Bd", imp); 

            
                    

			
                        System.out.println("Running...");


                } catch (Exception e) {
                        System.out.println("Server not connected: " + e);
                }
        }
}

