# demo-appsure-solution
Simple demo app, showcasing various Android method calls.
## Table of contents
* [TECHNOLOGIES](#techologies)
* [PROJECT STRUCTURE](#structure)
* [OBFUSCATION](#obfuscation)
* [TESTING](#testig)
* [LIBRARY METHODS](#library-methods)
* [FUTURE ENHANCEMENTS](#future-enhancements)

## TECHOLOGIES
This project was developed using Android Studio and obfuscated using ProGuard.

## PROJECT STRUCTURE
The application consist of two modules, *app* and *util*.
Module *app* is the Android Application, and *util* is the referenced library.
Module *util* exposes calls through the LibraryFacade class, wich is located in the package *com.appsuresolutions.utils.entrypoint*.

## OBFUSCATION
The library was obfuscated, in order to make code harder to reverse engineer. All classes have been collapsed into a single package called "筆理清車著治意然只得界見業", excepting those located in the package *com.appsuresolutions.utils.entrypoint*.

## TESTING
The library was tested using both JUNIT (the Aes encryption functions) and AndroidUnit (web service calls, file operations).
Almost each library function comes in two flavors, one synchronous and one asynchronous. Unit tests call only the synchronous functions, and the application calls mostly the asynchronous calls.

## LIBRARY METHODS

### [REST/GET]
Consumes data from https://dummy.restapiexample.com/employees . 
### [REST/POST]
Creates new entries using https://dummy.restapiexample.com/create .
### [LIST FILES]
List files from a hardcoded directory.
### [SAVE STRING TO FILE]
Save a string to a file in a predefined location.
### [LOAD STRINGS FROM FILE]
Load a string from a file in a predefined location.
### [BASE64 ENCODE]
Encodes a user-input string to a base64 string.
(App also copies the result into Clipboard, in order to facilitate decoding operations).
### [BASE64 DECODE]
Decodes a user-input string to a regular string.
### [AES ENCRYPRT]
Encrypts a user-input string to a hex encoded string. Encryption uses custom pin derivation in order to obtain key and iv. Aes mode is CBC, padding is PKCS5. 
(App also copies the result into Clipboard, in order to facilitate decryption operations).
### [AES DECRYPRT]
Decrypts a user-input hex string to a regular string. Decryption uses custom pin derivation in order to obtain key and iv. Aes mode is CBC, padding is PKCS5. 
### [BIOMETRIC ENCRYPTION]
Validates user presence, exposing callbacks in order to make things simpler.
### [CHANGE BUTTON COLOR]
Changes button color at runtime.

## FUTURE ENHANCEMENTS
* ToDo: Add more validations.
* ToDo: Test on multiple devices.
* ToDo: Better docs structure.
* ToDo: Better code structure
