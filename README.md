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

## quantile

Quantile normalization maps one empirical distribtuion onto another. It is used on batches of samples to make all of the samples have the same distribution.  I can also be used to transform test samples into the distribution of training samples.  Many implementations of this for expression data make the simplifying assumption that all of the samples being normalized have the same number of genes.   Most also assume that all the data is available at the same time.   For signature normalization neither of these assumptions typically hold.   We may train the classifiers on historical data with only 9000 genes, or on recent data with 25,000 genes.  Similarly, we may want to apply the signatures to data with an unknown number of genes.  More importantly, unless we want all of the training data to travel with the models, we will not have all of the training data in hand when we wish to apply normalization to a new test sample.  We will just have some kind of summary description of the original distribution. 

Approaches:

1. We could save the entire training distribution.  This is exceedingly space inefficient, making the entire
	training dataset travel around with the models. 
2. We could artificially force samples to have the same number of genes by filling in missing genes with NaN or if the size mismatch goes the other way, subsampling genes and just save one value per gene.  This is very ad hoc.  
3. We can represent the distribution in a compact but robust way with reasonable error bounds. 

Option 3 is easy to do with existing libraries.  For example, [The COLT QuantileBin1D](https://dst.lbl.gov/ACSSoftware/colt/api/hep/aida/bin/QuantileBin1D.html) class efficiently stores a compressed version of a distribution guaranteed to meet certain error bounds.   For a system purely implemented in Java I would recommend this approach.   

#### QuantileNormalizationReference

I have written a reference version of quantile normalization that uses the QuantileBin1D class.  Using this code has two phases.  First creating a compressed version of the distribution.  This is done like:

```java
  import bmeg.QuantileNormalizationReference;
  QuantileNormalizationReference qn = new QuantileNormalizationReference();
  qn.saveDistribution(trainingValues);
  qn.save(fileName); // saves a serialized version of the class
```
Then, when applying this trained model to new samples you can read in the saved version of the distribution
and apply it to new samples like:

```java
  import bmeg.QuantileNormalizationReference;
  QuantileNormalizationReference qn = QuantileNormalizationReference.read(fileName);
  transformedValues = qn.transform(inputValues);
```

To build the jar file 
```
  cd quantile
  mvn package
```
  


