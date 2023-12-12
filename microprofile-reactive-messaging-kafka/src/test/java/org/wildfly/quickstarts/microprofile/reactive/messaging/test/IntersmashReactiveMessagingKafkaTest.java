/**
 * Copyright (C) 2023 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.quickstarts.microprofile.reactive.messaging.test;

import org.jboss.intersmash.tools.annotations.Intersmash;
import org.jboss.intersmash.tools.annotations.Service;
import org.jboss.intersmash.tools.annotations.ServiceUrl;
import org.junit.jupiter.api.Test;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@Intersmash(
  @Service(IntersmashReactiveMessagingKafkaApplication.class)
)
public class IntersmashReactiveMessagingKafkaTest {

 @ServiceUrl(IntersmashReactiveMessagingKafkaApplication.class)
 private String serviceUrl;

 private final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
 @Test
 public void testHTTPEndpointIsAvailable() throws IOException {
  HttpGet httpGet = new HttpGet(serviceUrl);
  CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

  assertEquals("Successful call", 200, httpResponse.getStatusLine().getStatusCode());

  httpResponse.close();

 }
}
