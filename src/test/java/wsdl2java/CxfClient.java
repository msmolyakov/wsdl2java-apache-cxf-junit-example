package wsdl2java;

import net.webservicex.ConvertTemperatureSoap;
import net.webservicex.LengthUnitSoap;

public class CxfClient {

    public static LengthUnitSoap getLengthClient() {
        return CxfClientBuilder.getProxyFactory(LengthUnitSoap.class,
                "http://www.webservicex.net/length.asmx?wsdl", false);
    }

    public static ConvertTemperatureSoap getTemperatureClient() {
        return CxfClientBuilder.getProxyFactory(ConvertTemperatureSoap.class,
                "http://www.webservicex.net/ConvertTemperature.asmx?wsdl", true);
    }

}
