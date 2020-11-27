# DES

Implementation of Data Encryption Standard algorithm in Java.

## Overview of DES

The Data Encryption Standard is a symmetric-key algorithm for the encryption of digital data. DES is a block cipher--meaning it operates on plaintext blocks of a given size (64-bits) and returns ciphertext blocks of the same size. Thus DES results in a permutation among the 2^64 (read this as: "2 to the 64th power") possible arrangements of 64 bits, each of which may be either 0 or 1. Each block of 64 bits is divided into two blocks of 32 bits each, a left half block L and a right half R. (This division is only used in certain operations.) 

## Reference: 
http://page.math.tu-berlin.de/~kant/teaching/hess/krypto-ws2006/des.htm
