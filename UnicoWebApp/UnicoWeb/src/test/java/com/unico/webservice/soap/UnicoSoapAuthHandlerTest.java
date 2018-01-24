package com.unico.webservice.soap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.unico.webservice.auth.Authentication;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UnicoSoapAuthHandlerTest extends TestCase {

	@Mock
	private SOAPMessageContext context;

	@Mock
	private SOAPMessage message;

	@Mock
	private SOAPHeader header;

	@Mock
	private SOAPEnvelope envelope;

	@Mock
	private SOAPPart soapPart;

	@Mock
	private SOAPBody soapBody;

	@Mock
	private Authentication auth;

	@Mock
	private SOAPFault soapFault;

	@InjectMocks
	private UnicoSoapAuthHandler unicoSoapAuthHandler;

	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testHandleMessage() throws SOAPException {
		when(context.getMessage()).thenReturn(message);
		when(message.getSOAPHeader()).thenReturn(header);
		when(message.getSOAPPart()).thenReturn(soapPart);
		when(soapPart.getEnvelope()).thenReturn(envelope);
		when(envelope.getBody()).thenReturn(soapBody);

		HashMap<String, List<String>> map = mock(HashMap.class);
		List<String> list = mock(ArrayList.class);
		when(map.get(HttpHeaders.AUTHORIZATION)).thenReturn(list);
		when(list.get(0)).thenReturn("Basic test");

		when(context.get(MessageContext.HTTP_REQUEST_HEADERS)).thenReturn(map);

		assertTrue(unicoSoapAuthHandler.handleMessage(context));
	}

	@Test(expected = SOAPFaultException.class)
	public void testHandleMessageAuthFailure() throws Exception {
		when(context.getMessage()).thenReturn(message);
		when(message.getSOAPHeader()).thenReturn(header);
		when(message.getSOAPPart()).thenReturn(soapPart);
		when(soapPart.getEnvelope()).thenReturn(envelope);
		when(envelope.getBody()).thenReturn(soapBody);

		HashMap<String, List<String>> map = mock(HashMap.class);
		List<String> list = mock(ArrayList.class);
		when(list.get(0)).thenReturn("Basic test");
		when(map.get(HttpHeaders.AUTHORIZATION)).thenReturn(list);
		when(context.get(MessageContext.HTTP_REQUEST_HEADERS)).thenReturn(map);

		Mockito.doThrow(new Exception("authentication failure"))
		.when(auth)
		.authenticate("Basic test");
		when(soapBody.addFault()).thenReturn(soapFault);

		try {
		unicoSoapAuthHandler.handleMessage(context);
		} catch (SOAPFaultException e) {
			verify(soapFault, times(1)).setFaultString("authentication failure");
			throw e;
		}
	}

	@Test(expected = SOAPFaultException.class)
	public void testHandleMessageMissingHeader() throws Exception {
		when(context.getMessage()).thenReturn(message);
		when(message.getSOAPHeader()).thenReturn(header);
		when(message.getSOAPPart()).thenReturn(soapPart);
		when(soapPart.getEnvelope()).thenReturn(envelope);
		when(envelope.getBody()).thenReturn(soapBody);

		HashMap<String, List<String>> map = mock(HashMap.class);
		when(map.get(HttpHeaders.AUTHORIZATION)).thenReturn(null);
		when(context.get(MessageContext.HTTP_REQUEST_HEADERS)).thenReturn(map);
		when(soapBody.addFault()).thenReturn(soapFault);

		try {
		unicoSoapAuthHandler.handleMessage(context);
		} catch (SOAPFaultException e) {
			verify(soapFault, times(1)).setFaultString("Missing Authorization Header.");
			throw e;
		}
	}
}
