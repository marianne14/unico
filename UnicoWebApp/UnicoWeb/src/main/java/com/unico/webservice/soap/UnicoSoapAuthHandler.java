package com.unico.webservice.soap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.unico.webservice.auth.Authentication;

public class UnicoSoapAuthHandler implements SOAPHandler<SOAPMessageContext> {

	@Inject
	private Authentication auth;

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		try {

			SOAPMessage message = context.getMessage();
			SOAPHeader header = message.getSOAPHeader();
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			SOAPBody soapBody = message.getSOAPPart().getEnvelope().getBody();

			if (header == null) {
				header = envelope.addHeader();
			}

			Map<String, List<String>> headers = (Map<String, List<String>>) context.get(MessageContext.HTTP_REQUEST_HEADERS);
			List<String> authentication = headers.get(HttpHeaders.AUTHORIZATION);

			try {
				if(authentication == null) {
					throw new Exception("Missing Authorization Header.");
				}

				auth.authenticate(authentication.get(0));
			} catch (Exception e) {
				SOAPFault soapFault = soapBody.addFault();
				soapFault.setFaultString(e.getMessage());
				throw new SOAPFaultException(soapFault);
			}
		} catch (SOAPException e) {
			System.err.println(e);
		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}
}
