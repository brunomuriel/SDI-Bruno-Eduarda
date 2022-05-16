package MYC;

import javax.jws.WebService;

@WebService(endpointInterface = "MYC.WSRecheiosServer")
public class WSRecheiosServerImpl implements WSRecheiosServer {

	private static int NRecheios = 0;

	public String getRecheios(String name) {
		NRecheios++;
		return "Recheio " + name + " efetuado!";
	}

	public int getNRecheios() {
		return NRecheios;
	}

}
