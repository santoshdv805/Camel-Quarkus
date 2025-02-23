package com.santosh.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class CamelRouteTest {
	@Inject
	ProducerTemplate template;
	@Inject
	CamelContext camelContext;;

	@Test
	public void myRouteTest() throws InterruptedException {
		 MockEndpoint mockResult = camelContext.getEndpoint("mock:result", MockEndpoint.class);
	        assertNotNull(mockResult, "MockEndpoint should not be null");

	        mockResult.expectedMessageCount(1);
		template.sendBody("direct:camel-quarkus-test", "{Test}");
		mockResult.assertIsSatisfied();
		String body=mockResult.getExchanges().get(0).getIn().getBody(String.class );
		System.out.println("body--->"+body);
		assertNotNull(body);
		assertEquals("{Test}", body);
	}
}
