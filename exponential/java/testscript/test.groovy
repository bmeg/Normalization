#!/usr/bin/env groovy 

import bmeg.ExponentialNormalization


// Normally distributed (microarray) sample
inputValues = new File("reference_input.tab").collect{it as double}
inputValues = inputValues as double[] // ArrayList is result of collect

// Output transformed by reference pipeline..
referenceOutput = new File("reference_output.tab").collect{it as double}

outputValues = ExponentialNormalization.transform(inputValues)

outputValues.eachWithIndex{v,i->
	ref = referenceOutput[i]
	delta = Math.abs(v-ref)		
	if (delta > 0.01) System.err.println "val: $v\tref: $ref\tdelta: $delta"
}

