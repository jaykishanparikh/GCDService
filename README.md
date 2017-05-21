## Synopsis

This is an enterprise Java application that implements RESTful and SOAP web services.

The RESTful service will expose two methods: 
  1. public String push(int i1, int i2);    
        which returns the status of the request to the caller as a String. The two parameters will be added to a JMS queue. 
  2. public List<Integer> list();    
        which returns a list of all the elements ever added to the queue from a database in the order added as a JSON structure.
     
The SOAP service will expose the following method as operations: 
  1. public int gcd();    
        which returns the greatest common divisor* of the two integers at the head of the queue. These two elements will subsequently be discarded from the queue and the head replaced by the next two in line. 
  2. public List<Integer> gcdList();    
        which returns a list of all the computed greatest common divisors from a database. 
  3. public int gcdSum();    
        which returns the sum of all computed greatest common divisors from a database. 
		
## Assumptions
  1. It is assumed that only positive Integer numbers are pushed.
  2. Application is deployed on Weblogic 10.3 application server.
  3. JMS queue is already configured on application server as a pre-requests of this application.  
  4. Gradle 3.5 is instlaled on the build machine. 
	
## Technologies
  1. Application server - Oracle weblogic server 10.3 
  2. JMS Queue – Weblogic Server
  3. Build tool – Gradle 3.5
  4. Java 1.6 for source code compilation
  5. Jersery implementation for Restful API 
  6. SOAP 2.0 web service implementation
  7. In memory (java collection) database.
  8. SOAP UI for testing
 
## Installation

  1. Clone the repository or download it on the local machine.
  2. Execute gradle build opeation by folloing command.
```
D:\Jaykishan\professional\workspace\GCDService>**gradle build**
```

output
```
D:\Jaykishan\workspace\GCDService>gradle build                                     
case MRGPicked up _JAVA_OPTIONS: -Xmx512M                                                  
case MRGGCDService project build starts...                                                 
case MRGenv var D:\Oracle\Middleware                                                       
case MRGGCDService project build ends...                                                   
case MRG:compileJava                                                                       
case MRGwarning: [options] bootstrap class path not set in conjunction with -source 1.6    
case MRG1 warning                                                                             
case MRG:processResources NO-SOURCE
case MRG:classes
case MRG:war
case MRG:assemble
case MRG:compileTestJava NO-SOURCE
case MRG:processTestResources NO-SOURCE
case MRG:testClasses UP-TO-DATE
case MRG:test NO-SOURCE
case MRG:check UP-TO-DATE
case MRG:build
case MRG
case MRGBUILD SUCCESSFUL
case MRG
case MRGTotal time: 2.098 secs
```
  3. Once build is completed successfully, see the .war file is generated.
  4. Login to weblogic application server adminstration console.
  5. Deploy the war file. No server restart is required.
  6. Once deployed successfully, the services are up and running.

## API Reference
* SOAP API wsdl - http://10.9.132.124:7001/GCDService/GcdSoapServiceService?wsdl
* Restful API - http://10.9.132.124:7001/GCDService/GcdRestService/

## Unit Testing

The application can be tested by following ways.
  1. Restful web service
  * push - To push two integer numbers to message queue.
      * url - http://localhost:7001/GCDService/GcdRestService/<<firstNumber>>/<<secondNumber>>
	  * example - to push numbers 12 and 22, call the url - **localhost:7001/GCDService/GcdRestService/12/22**
  
  * list - To retrieve numbers ever pushed to message queue.
      * url - http://localhost:7001/GCDService/GcdRestService/list
	  * example result - [12,22,3,9,45,55,36,54,3,5]
  2. SOAP web service
  * gcd - to get the gcd of first two number from queue head.    

Service request
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://soap.service.gcd.uni.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:gcd/>
   </soapenv:Body>
</soapenv:Envelope>
```  
Service response
```  
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <ns2:gcdResponse xmlns:ns2="http://soap.service.gcd.uni.com/">
         <return>2</return>
      </ns2:gcdResponse>
   </S:Body>
</S:Envelope>
```  
  * gcdList - to get the list of all calculated gcds

Service request  
```  
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://soap.service.gcd.uni.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:gcdList/>
   </soapenv:Body>
</soapenv:Envelope>	
```  
Service response
```  
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <ns2:gcdListResponse xmlns:ns2="http://soap.service.gcd.uni.com/">
         <return>2</return>
         <return>3</return>
		 <return>8</return>
		 <return>6</return>
		 <return>7</return>
      </ns2:gcdListResponse>
   </S:Body>
</S:Envelope>	
```    
  * gcdSum - sum of all calculated gcds.
  
Service request
```    
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://soap.service.gcd.uni.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:gcdSum/>
   </soapenv:Body>
</soapenv:Envelope>	     
```    
Service response
```    
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <ns2:gcdListResponse xmlns:ns2="http://soap.service.gcd.uni.com/">
         <return>26</return>
      </ns2:gcdListResponse>
   </S:Body>
</S:Envelope>	
```    
