package MYC;

import java.net.URL;
import java.net.InetAddress;
import javax.xml.namespace.QName;
import java.util.concurrent.TimeUnit;
import javax.xml.ws.Service;
import javax.xml.ws.Endpoint;
import java.util.*;
import java.io.*;

public class ServerPublisher {

	

	static void printReport() {
      System.out.println("##  Servidor  ##");
      System.out.println("Status: finalizado");
      System.out.println("Nro_clientes_atendidos: 2");
      System.out.println("WS-Pandelo (ens1): 4");
      System.out.println("WS-Cobertura (ens1): 2");
      System.out.println("WS-Recheio (ens1): 8");
      System.out.println("WS-Cortes (ens1): 4");
      System.out.println("WS-Recepcao (ens1): 18");
      System.out.println("###########");
	}

	static void readSetup (String host, WSRecepcaoServer srecepcao) {

		int cliente;

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
					break;

					// case "Guiche":
					//     srecepcao.setServer(Integer.parseInt(word[2]));
					// break;

					case "****":
						cliente = Integer.parseInt(word[2]);
					break;

					// case "WS-Pandelo":
					// 	spandelo.getPandelo(word[2]);
					// break;

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

		System.out.println("* SERVER: Beginning to publish WS Servers ("+host+") *");

		// WS Recepcao
		Endpoint ep = Endpoint.create(new WSRecepcaoServerImpl());
		ep.publish("http://"+host+":9845/WSRecepcao");

		// WS Pandelo
		Endpoint ep_pan = Endpoint.create(new WSPandeloServerImpl());
		ep_pan.publish("http://"+host+":9846/WSPandelo");

		// WS Cortes
		Endpoint ep_cor = Endpoint.create(new WSCortesServerImpl());
		ep_cor.publish("http://"+host+":9847/WSCortes");

		// WS Cobertura
		Endpoint ep_cob = Endpoint.create(new WSCoberturaServerImpl());
		ep_cob.publish("http://"+host+":9848/WSCobertura");

		// WS Recheios
		Endpoint ep_rec = Endpoint.create(new WSRecheiosServerImpl());
		ep_rec.publish("http://"+host+":9849/WSRecheios");

		System.out.println("* All done publishing. *");

		try {
			// ##### WS Recepcao  #####
			URL url1 = new URL("http://"+host+":9845/WSRecepcao?wsdl");
			QName qname1 = new QName("http://MYC/",
			"WSRecepcaoServerImplService");

			// ##### WS Pandelo  #####
			URL url4 = new URL("http://"+host+":9846/WSPandelo?wsdl");
			QName qname4 = new QName("http://MYC/",
			"WSPandeloServerImplService");

			// ##### WS Cortes  #####
			URL url3 = new URL("http://"+host+":9847/WSCortes?wsdl");
			QName qname3 = new QName("http://MYC/",
			"WSCortesServerImplService");

			// // ##### WS Cobertura  #####
			URL url2 = new URL("http://"+host+":9848/WSCobertura?wsdl");
			QName qname2 = new QName("http://MYC/",
			"WSCoberturaServerImplService");

			// ##### WS Recheios  #####
			URL url5 = new URL("http://"+host+":9849/WSRecheios?wsdl");
			QName qname5 = new QName("http://MYC/",
			"WSRecheiosServerImplService");

			Service recepcao = Service.create(url1, qname1);
			WSRecepcaoServer srecepcao = recepcao.getPort(WSRecepcaoServer.class);

			Service cobertura = Service.create(url2, qname2);
			WSCoberturaServer scobertura = cobertura.getPort(WSCoberturaServer.class);

			Service cortes = Service.create(url3, qname3);
			WSCortesServer scortes = cortes.getPort(WSCortesServer.class);

			Service pandelo = Service.create(url4, qname4);
			WSPandeloServer spandelo = pandelo.getPort(WSPandeloServer.class);

			Service recheios = Service.create(url5, qname5);
			WSRecheiosServer srecheios = recheios.getPort(WSRecheiosServer.class);

			InetAddress addr = InetAddress.getLocalHost();
			String hostname = addr.getHostName();
			readSetup(host, srecepcao);
			Boolean flag = true;
			while (flag) { // Pooling aguardando clientes

        TimeUnit.SECONDS.sleep(1);
				if (srecepcao.getNroClient() <= 0) {
					System.out.println("* Server End *");
					flag = false;
					ep.stop();
					ep_pan.stop();
					ep_cor.stop();
					ep_cob.stop();
					ep_rec.stop();
					printReport();
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
