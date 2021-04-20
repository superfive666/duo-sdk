# DUO-sdk
DUO SDK Springboot Integration

## Version
Springboot version 2.4.5

Java version: 11

Logging: spring-boot-starter-log4j2

## Motive
This project provides a pluggable development kit for Java Springboot application which wishes
to use DUO 2FA as part of the security measures. However, there are no plausible Java kit available
open source for seamless integration. Hope this project helps with the ease of integration.

Having included the dependency in the `pom.xml`, there are two modes of using 
* By autowiring the `DuoSecuirty` bean directly into any of the existing service code layer.
* By annotating a public method with `@DuoSecured` and one of the argument should be `Auth` to 
enable DUO 2FA before the actual method calls.
  
## Setup
Include the following dependency in your `pom.xml` file
```xml
<dependency>
    <groupId>io.github.superfive666</groupId>
    <artifactId>duo-sdk</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
In your springboot application properties, configure the required parameters:
```yaml
duo:
  secret:
    host: api-XXXXXXXX.duosecurity.com
    ikey: DIWJ8X6AEYOR5OMC6TQ1
    skey: Zh5eGmUq9zpfQnyUIu5OL9iWoMMv5ZNmk3zLJ4Ep
```
For more information on where to find the relevant information of your own DUO account,
log into your [DUO admin panel](https://admin.duosecurity.com/login), and you will find
all relevant information to configure this.

### Method 1 - Autowiring
In your springboot application, enable the DUO 2FA by annotating the class (or any of 
your configuration class) with `@EnableDuoSecurity`. Simplified example is given below:

```java
package com.example;

import io.github.superfive666.duosdk.annotation.EnableDuoSecurity;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDuoSecurity
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

And in one of the service class, autowire the `DuoSecuirty` class to use the API:
[API Specification](https://duo.com/docs/authapi#/auth)
```java
package com.example.service;

import io.github.superfive666.duosdk.params.request.Auth;
import io.github.superfive666.duosdk.auth.DuoSecurity;
import io.github.superfive666.duosdk.params.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    
    @Autowired
    private DuoSecurity duoSecurity;

    public void testDuo2FaAuthentication() {
        Auth auth = new Auth();
        // set your required 2FA authentication parameters

        // async or synchronously use 2FA DUO authentication below
        AuthResponse response = duoSecurity.auth(auth);
        // check your response here
        
        // other application logic below...
    }
    
}
```

### Method 2 - Annotation Security
In your springboot application, enable annotation security by using `@EnableAnnotatedDuoSecurity`
```java
package com.example;

import io.github.superfive666.duosdk.annotation.EnableAnnotatedDuoSecurity;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAnnotatedDuoSecurity
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
And in your service layer class, do the following:

```java
package com.example.service;

import io.github.superfive666.duosdk.annotation.DuoSecured;
import io.github.superfive666.duosdk.params.DuoAuthenticationMode;
import io.github.superfive666.duosdk.params.request.Auth;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @DuoSecured
    public void do2Fa(Auth auth, Object arg) {
        // your business logic will only be executed if 2FA authentication success
        // do something with the function argument
    }

    @DuoSecured(type = "Push Screen Title 1", mode = DuoAuthenticationMode.PUSH)
    public void do2FaPush(Auth auth, Object arg) {
        // your business logic will only be executed if 2FA authentication success
        // do something with the function argument
    }

}
```

## Additional Configuration Available
You can choose to go via a configured proxy server to use DUO API.
You can refer to the sample configuration below (by default, proxy is not
enabled if not specifically turned on):
```yaml
duo:
  proxy:
    proxy-enabled: true
    proxy-url: 101.23.23.122
    proxy-port: 3128
    proxy-username: admin
    proxy-password: p@ssw0rd
```

For a springboot demo application, please find the code in [Springboot DUO sdk-demo](https://github.com/superfive666/duo-sdk-demo)