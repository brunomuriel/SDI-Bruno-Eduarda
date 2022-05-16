package MYC;

import javax.jws.WebService;

@WebService(endpointInterface = "MYC.WSCortesServer")
public class WSCortesServerImpl implements WSCortesServer {

	private static int NCortes = 0;

	public String getCortes(String name) {
		NCortes++;
		return "Corte " + name + " efetuado!";
	}

	public int getNCortes() {
		return NCortes;
	}

}
