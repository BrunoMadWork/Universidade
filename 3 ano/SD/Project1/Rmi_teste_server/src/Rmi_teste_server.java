import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class Rmi_teste_server {
	public static void main(String[] args) throws RemoteException {
		
		try {
			System.getProperties().put("java.security.policy","java.policy");
			System.setSecurityManager(new RMISecurityManager());
	
			Bd_implementation imp=new Bd_implementation();
			LocateRegistry.createRegistry(7000);
			Naming.rebind("rmi://169.254.10.151:7000/Bd", imp); 

			System.out.println("Hello Server ready.");
		} catch (RemoteException re) {
			System.out.println("Exception in HelloImpl.main: " + re);
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException in HelloImpl.main: " + e);		
			e.printStackTrace();
		}
	}
}
