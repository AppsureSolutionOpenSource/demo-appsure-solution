# demo-appsure-solution
Simple demo app, showcasing various Android method calls.
## Table of contents
* [TECHNOLOGIES](#techologies)
* [PROJECT STRUCTURE](#structure)
* [OBFUSCATION](#obfuscation)
* [TESTING](#testig)
* [LIBRARY METHODS](#library-methods)

## TECHOLOGIES
This project was developed using Android Studio.

## PROJECT STRUCTURE
The application consist of two modules, *app* and *util*.
Module *app* is the Android Application, and *util* is the referenced library.
Module *util* exposes calls through the LibraryFacade class, wich is located in the package *com.appsuresolutions.utils.entrypoint*.

## OBFUSCATION
The library was obfuscated, in order to make code harder to reverse engineer. All classes have been collapsed into a single package called "筆理清車著治意然只得界見業", excepting those located in the package *com.appsuresolutions.utils.entrypoint*.

## TESTING
The library was tested using both JUNIT (the Aes encryption functions) and AndroidUnit (web service calls, file operations).

## LIBRARY METHODS
### [REST/GET]
* [REST/POST](#rest-post)
* [LIST FILES](#list-file)
* [SAVE STRING TO FILE](#save-string-to-file)
* [LOAD STRINGS FROM FILE](#load-string-from-file)
* [BASE64 ENCODE](#base64-encode)
* [BASE64 DECODE](#base64-decode)
* [AES ENCRYPRT](#aes-encrypt-hex-encode)
* [AES DECRYPRT](#aes-encrypt-hex-decode)

