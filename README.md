# normalization
Code for various signature normalization schemes.  This project will contain code for two or more normalization schemes in several languages.  

## exponential
Exponential normalization is a rank based normalization scheme.  Genes are ranked and an inverse exponential transformation is applied to each rank value to get a transformed value.  The net effect is that whatever the distribution of the input, the output is guaranteed to have an exponential distribution. 

The java version of the code can be used like this: 

```java
  import bmeg.ExponentialNormalization
  outputValues = ExponentialNormalization.transform(inputValues)
```

To build the jar file 
```
  cd exponential/java
  mvn package
```
  
The required Apache Math3 library will be downloaded by the pom file dependency.  You can include the dependency 
from that pom file in any project that will use this code.  

A groovy test script with test input and reference output is included in exponential/testscript/
