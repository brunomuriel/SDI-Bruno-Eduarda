package MYC;

import java.net.URL;
import java.net.InetAddress;
import javax.xml.namespace.QName;
import java.util.concurrent.TimeUnit;
import javax.xml.ws.Service;
import javax.xml.ws.Endpoint;
import java.util.*;
import java.io.*;

// import java.xml.bind;
// import javax.xml.bind.JAXBException;


public class ServerPublisher {

	static void printReport(WSRecepcaoServer srecepcao) {
		try { 
			System.out.println("##  Servidor  ##");
			System.out.println("Status: finalizado");
			System.out.println("Nro_clientes_atendidos: " +  srecepcao.getNroClient());
			System.out.println("WS-Pandelo (ens1): 4");
			System.out.println("WS-Cobertura (ens1): 2");
			System.out.println("WS-Recheio (ens1): 8");
			System.out.println("WS-Cortes (ens1): 4");
			System.out.println("WS-Recepcao (ens1): 18");
			System.out.println("###########");
	  	}catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void readSetup (String host, WSRecepcaoServer srecepcao) {

		try {
			Scanner sc = new Scanner(System.in);
			while(true){
				if (!sc.hasNextLine()) {
					break;
				}
				String newline = sc.nextLine();
				BufferedReader inFromUser
				= new BufferedReader(new InputStreamReader(System.in));
				String[] word = newline.split(" ");
				
				switch (word[0]) {
					case "NClientes":
					      srecepcao.setServer(Integer.parseInt(word[2]));
						  System.out.println("Número de clientes: " +  srecepcao.getNroClient());
					      break;
					// continuar código da leitura das configurações
					

					default:
						// System.out.println("Ignorado: ("+word[0]+")");
				}
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		String host = args[0];

		System.out.print("oi\n");


		System.out.println("* SERVER: Beginning to publish WS Servers ("+host+") * \n");

		// WS Recepcao
		Endpoint ep = Endpoint.create(new WSRecepcaoServerImpl());
		ep.publish("http://"+host+":9815/WSRecepcao");

		// WS Pandelo
		Endpoint ep_pan = Endpoint.create(new WSPandeloServerImpl());
		ep_pan.publish("http://"+host+":9816/WSPandelo");

		// WS Cortes
		Endpoint ep_cor = Endpoint.create(new WSCortesServerImpl());
		ep_cor.publish("http://"+host+":9817/WSCortes");

		// Continuar a declaração dos demais serviços ...

		// ...

		System.out.println("* All done publishing. *");

		try {
			// ##### WS Recepcao  #####
			URL url1 = new URL("http://"+host+":9815/WSRecepcao?wsdl");
			QName qname1 = new QName("http://MYC/",
			"WSRecepcaoServerImplService");

			Service recepcao = Service.create(url1, qname1);
			WSRecepcaoServer srecepcao = recepcao.getPort(WSRecepcaoServer.class);
			InetAddress addr = InetAddress.getLocalHost();
			String hostname = addr.getHostName();
			readSetup(host, srecepcao);
			Boolean flag = true;
			while (flag) { // Pooling aguardando clientes

        TimeUnit.SECONDS.sleep(1);
				if (srecepcao.getNroClient() <= 0) {
					System.out.println("* Server End *");
					flag = false;
					
					printReport(srecepcao);
					
					ep.stop();
					ep_pan.stop();
					ep_cor.stop();
					// Continuar ...
					// ...

					// ...
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
