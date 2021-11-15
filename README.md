# edge-service
<h3>This is an edge service prototype</h3>

<h4>A route is defined by the following:</h4>

<p>Predicates, which select a route based on information in the incoming HTTP request</p>

<p>Filters, which can modify both the request and/or the response</p>
<p>A destination URI, which describes where to send a request</p>
<p>An ID, that is, the name of the route</p>
<p>For a full list of available predicates and filters, refer to the reference documentation: https://cloud.spring.io/spring-cloud-gateway/single/spring-cloud-gateway.html.</p>


<p>
spring.cloud.gateway.routes:
- id: product-composite
  uri: lb://product-composite
  predicates:
  - Path=/product-composite/**
</p>

<p>
id: product-composite: The name of the route is product-composite.
uri: lb://product-composite: If the route is selected by its predicates, the request will be routed to the service that is named product-composite in the discovery service, Netflix Eureka. The protocol lb:// is used to direct Spring Cloud Gateway to use the client-side load balancer to look up the destination in the discovery service.
predicates: - Path=/product-composite/** is used to specify what requests this route should match. ** matches zero or more elements in the path.
</p> 
