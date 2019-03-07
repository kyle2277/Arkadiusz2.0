<img src=https://github.com/kyle2277/Arkadiusz2.0/blob/master/Arkadiusz2.0_logo.png width="275" height="183"></img>
___
# Arkadiusz2.0
## Overview
Welcome to project Arkadiusz2.0, a password protection program designed for quick and secure encyrption of usernames and passwords. This program internally organizes accounts and their respective credientials, making retrieving account information easy and efficient. Credentials are stored locally solely in the form of encrypted matrices.
## Documentation
### How it works
#### Master Key
In short, Arkadiusz2.0 uses __linear algebra and matrix operations__ to achieve string encryption. At the start of the program the user provides a master password. This password generates a 4x4 invertible matrix which does the encryption and decryption, via a change of basis, for all credentials stored under this master password. The process of generating the encryption/decryption matrix is repeatable; A given master password will create the same matrix every time it is entered, and different master passwords will create unique matrices. This also allows different credentials to be stored under different master passwords.
#### Encryption
When a string is recieved for encryption, it is first transformed into an encoded matrix, a unique numerical representation of itself. The encryption/decryption matrix (described above) is then used to apply a change of basis to each column vector in the encoded matrix, resulting in an encrypted matrix. The __only__ way to get back to the original matrix is with a reverse change of basis using the encryption/decryption matrix. Without this exact matrix, the encrypted credentials are simply collections of random numbers.
#### Decryption
The process of decryption is simply the opposite of encryption. The encryption/decryption matrix is inverted, and then used to apply a (reverse) change of basis on the column vectors of an encrypted matrix. This results in a decrypted matrix which can be easily turned back into a string.
#### The Vault
The encryption matrix of each respective credential is stored locally in the program directory, in a file called 'vault.txt'. The only non-encrypted information in the vault is the names of the accounts that correspond to the encrypted credentials. Credentials are __stored in no other form or place__ than in this file. When the program is running, it generates a list of all current accounts from the vault. This list stores all the passwords in their encrypted matrix form, meaning all decryption is done on the fly when prompted by the user.
### \*\*Important\*\*
This program is an __implementation of concept__. It has not been tested for, and offers no true gaurantee of, security. Proceed at your own risk and always store your sensitive credentials in the most secure manner available.
### Dependencies
It is necessary to add the required .jar files from each of these libraries to your Java classpath before running:
* EJML (Efficient Java Matrix Library)
  * ejml-simple
  * ejml-core
  * ejml-ddense
* StringUtils (Apache Commons Lang3)
  * commons-lang3
If running in the terminal, add the path to all dependencies to the CLASSPATH variable in the makefile.
