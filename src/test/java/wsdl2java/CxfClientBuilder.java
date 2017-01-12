package wsdl2java;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

import java.util.HashMap;
import java.util.Map;

public class CxfClientBuilder {

    @SuppressWarnings("unchecked")
    public static <T> T getProxyFactory(Class<T> clazz, String url, boolean soap12) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(clazz);
        factory.setAddress(url);

        if (soap12) {
            factory.setBindingId(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING);
        }

        return (T) factory.create();
    }

    public static <T> T getProxyFactoryWithWSS(Class<T> clazz, String url, boolean soap12, String user) {
        T port = getProxyFactory(clazz, url, soap12);
        Client client = ClientProxy.getClient(port);

        initSecurityHeaders(client, user);
        return port;
    }


    private static void initSecurityHeaders(Client client, String user) {
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setContentType("application/xop+xml; charset=UTF-8; type=\"application/soap+xml\"");
        http.setClient(httpClientPolicy);
        client.setThreadLocalRequestContext(true);

        Map<String, Object> outProps = new HashMap<>();
        outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_NONE);
        outProps.put(WSHandlerConstants.MUST_UNDERSTAND, "false");
        outProps.put(WSHandlerConstants.USER, user);

        WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
        client.getEndpoint().getOutInterceptors().add(wssOut);
    }

}
