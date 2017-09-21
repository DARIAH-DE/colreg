# colreg

The DARIAH-DE Collection Registry (CR) is a web application that serves as central component in the DARIAH-DE Data Federation Architecture (DFA) software stack. Its primary purpose consists in providing means to register, store and access descriptions of research data collections.

Further information on the concept of the Collection Registry can be accessed following https://de.dariah.eu/collection-registry.

## Prerequisites

The installation of a CR instance requires the setup of some required components:
* An installed Java, minimum version JavaSE-1.7
* A Java web-application server such as Tomcat or Jetty
* MongoDB 3 as storage backend
* A web-accessible installation of the DARIAH-DE Data Modeling Environment (DME); you can use a beta installation (testdata) at https://minfba.de.dariah.eu/dme/ or *currently not yet installed* the productive installation at https://dme.de.dariah.eu/ in case no other instance of the DME is needed

## Configuration

Configuration steps can be performed before an actual installation of the CR container/package to prevent initial startup failures.

### Configuration file

Configuration parameters for the CR are set in a central YAML configuration file, which should remain outside of the application container to remain untouched when updating the CR. A good place for storing the configuration could be at `/etc/dfa/colreg/colreg.yml`

A simple initial colreg.yml configuration:

```yaml
paths:
  main: /etc/dfa/colreg

api:
  dme:
    # A recent beta intallation 
    baseUrl: https://minfba.de.dariah.eu/dme/
    # The future production installation
    #baseUrl: https://dme.de.dariah.eu/
    modelLink: ${api.dme.baseUrl}model/editor/%s/
    models: ${api.dme.baseUrl}api/models

mongo:
  host: 127.0.0.1
  port: 27017
  database: colreg
  
auth:
  local: 
    forceHttps: true
    users:  
      - username: 'admin'
        passhash: '$2a$10$nbXRnAx5wKurTrbaUkT/MOLXKAJgpT8R71/jujzPwgXXrG.OqlBKW'
        roles: ["ROLE_ADMINISTRATOR"]
  saml:
    keystore:
      path: ${paths.main}/key/dfa-de-dariah-eu.jks
      # Uncomment if keystore is protected by password
      #pass: 'somepass'
      alias: dfa.de.dariah.eu
      aliaspass: ''
```

### Environment variables

The CR needs to be aware of its configuration file location. Currently, this is done by handing an environment parameter to the web application server. In the case of a Tomcat-based installation, simply create or modify the setenv.sh (or the appenv.sh file) in the ./bin subdirectory of the Tomcat installation:

```
\#!/bin/sh
\# Application specific environment variables

export CATALINA_OPTS="$CATALINA_OPTS -Dcolreg.yml=/path/to/colreg.yml"
export CATALINA_OPTS="$CATALINA_OPTS -Dsaml=false"
```

### Security (part 1)

For security, the CR relies on a Java/Spring-based implementation of the SAML protocol. While this installation guide contains only the necessary steps to install the CR, further information on the security component can be found here: https://github.com/tgradl/dariahsp

The CR needs a Java keystore for start up - even when saml processing is turned off and local logins are used. This is the case in order to be able to generate SAML SP metadata once the CR is running. As access to the CR should be protected by SSL, the required certificate pair and chain should be available.

Based on a X.509 keypair and certificate chains, a keystore can easily be consolidated with openssl and the keytool (comes with Java installation) following the guide here: https://github.com/tgradl/dariahsp#java-keystore

### Database

In addition to file based parameters, the CR comes with predefined vocabularies, which need to be imported into the MongoDB database when installing a new instance of the CR. Using the shell, you can import the initial database content with mongoimport (modify the path and database name if required):

```
for I in ./colreg-ui/WEB-INF/classes/initialization_data/*; do mongoimport --db colreg --jsonArray --quiet $I; done && 
```

## Installation and startup

The WAR container files for a custom installation of the CR can be found at:
* Production releases: https://minfba.de.dariah.eu/artifactory/webapp/#/artifacts/browse/tree/General/dariah-minfba-releases/eu/dariah/de/colreg-ui
* Snapshot releases: https://minfba.de.dariah.eu/artifactory/webapp/#/artifacts/browse/tree/General/dariah-minfba-snapshots/eu/dariah/de/colreg-ui

In its developer portal, DARIAH-DE automatically generates and provides Debian packages. The repository (both for snapshots and releases) can be found at: https://ci.de.dariah.eu/packages/

## Security (part 2)

Once the CR has successfully started up (check the logs of the respective web application server), metadata for registering the CR instance as SAML Service Provider can be generated. In order to generate such metadata, navigate to the /saml/web/metadata path (e. g. http://localhost:8080/saml/web/metadata) and click on *Generate new SP*.   

![Generate new SP](https://github.com/DARIAH-DE/colreg/raw/master/img/installation_saml_metatata.png "Generate new SP")

