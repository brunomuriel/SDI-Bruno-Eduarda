package MYC;

import javax.jws.WebService;

@WebService(endpointInterface = "MYC.WSCoberturaServer")
public class WSCoberturaServerImpl implements WSCoberturaServer {

	private static int NCoberturas = 0;

	public String getCobertura(String name) {
		NCoberturas++;
		return "Cobertura " + name + " efetuado!";
	}

	public int getNCoberturas() {
		return NCoberturas;
	}

}
