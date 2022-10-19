# Torus Key Generation

## Introduction

This package is used to reconstruct private key using idToken when mfa is not enabled. 

## Steps to reconstruct Torus Key
- Pass verifier, verifierId, verifierParamsHashMap, idToken parameters to getTorusKey() function.
- getTorusKey() function returns Torus key and throws error when mfa is not enabled.

## Getting started
```groovy
repositories {
        maven { url "https://jitpack.io" }
   }
```   

## Dependencies used
- [Torus Fetch Node Details](https://github.com/torusresearch/fetch-node-details-java)
- [Torus Torus Utils Java](https://github.com/torusresearch/torus-utils-java)
- [web3j Android](https://github.com/web3j/web3j)
- [Google gson](https://github.com/google/gson)

## Requirements
- Java 8 / 1.8

