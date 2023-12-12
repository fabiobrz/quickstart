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
package org.wildfly.quickstarts.microprofile.lra;

import org.jboss.intersmash.tools.IntersmashConfig;
import org.jboss.intersmash.tools.annotations.Intersmash;
import org.jboss.intersmash.tools.annotations.Service;
import org.jboss.intersmash.tools.annotations.ServiceUrl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

@Intersmash(
  @Service(IntersmashLRAApplication.class)
)
public class IntersmashLRATest {

 @ServiceUrl(IntersmashLRAApplication.class)
 private String lraServiceUrl;

 @BeforeAll
 public static void setup() {
  // initialize the application service descriptor
  final IntersmashLRAApplication application = new IntersmashLRAApplication();
  application
    .addSetOverride("build.uri", IntersmashConfig.deploymentsRepositoryUrl())
    .addSetOverride("build.ref", IntersmashConfig.deploymentsRepositoryRef())
    .addSetOverride("deploy.builderImage", application.getBuilderImage())
    .addSetOverride("deploy.runtimeImage", application.getRuntimeImage());
 }

 @Test
 public void testHTTPEndpointIsAvailable() throws IOException, InterruptedException, URISyntaxException {
  String serverHost = lraServiceUrl + "/participant1/work";
  final HttpRequest request = HttpRequest.newBuilder()
    .uri(new URI(serverHost))
    .GET()
    .build();
  final HttpClient client = HttpClient.newBuilder()
    .followRedirects(HttpClient.Redirect.ALWAYS)
    .connectTimeout(Duration.ofMinutes(1))
    .build();
  final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
  assertEquals(200, response.statusCode());

 }
}
