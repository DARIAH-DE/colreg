# colreg

The DARIAH-DE Collection Registry (CR) is a web application that serves as central component in the DARIAH-DE Data Federation Architecture (DFA) software stack. Its primary purpose consists in providing means to register, store and access descriptions of research data collections.

Further information on the concept of the Collection Registry can be accessed following https://de.dariah.eu/collection-registry.

Bugs are tracked at: https://minfba.de.dariah.eu/mantisbt/set_project.php?project_id=21

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
#!/bin/sh
# Application specific environment variables

export CATALINA_OPTS="$CATALINA_OPTS -Dcolreg.yml=/path/to/colreg.yml"
export CATALINA_OPTS="$CATALINA_OPTS -Dsaml=false"
```

### Security (part 1: setup)

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

Once installed and successfully started as (within a Java web application server) an empty CR dashboard is presented - with default ports and installed on a local machine as http://localhost:8080

![Empty CR dashboard](https://github.com/DARIAH-DE/colreg/raw/master/img/empty_cr_dashboard.png "Empty CR dashboard")

You can login as *admin*, username *password* and are ready to fill your collections and agents. In case local user accounts should be used, see https://github.com/tgradl/dariahsp#local-user-accounts on how to create such local accounts.

If SAML authentication as e.g. with the DFN-AAI should be used instead, continue with the following section.

## Security (part 2: SAML)

Once the CR has successfully started up (check the logs of the respective web application server), metadata for registering the CR instance as SAML Service Provider can be generated. In order to generate such metadata, navigate to the /saml/web/metadata path (e. g. http://localhost:8080/saml/web/metadata) and click on *Generate new SP*.   

Some basic SAML protocol related parameters need to be set in this dialog. See https://github.com/tgradl/dariahsp#metadata-generation on how these parameters are set in the DARIAH-DE context or see the section auth.saml.sp in the DARIAH-DE configuration sample below.

Make sure to set the *Store for current session* to *Yes*. This way, when entering metadata in the DFN AAI administration interface, you can provide a link to https://yourserver/colreg/saml/metadata in order to autofill the input elements.

![Generate new SP](https://github.com/DARIAH-DE/colreg/raw/master/img/installation_saml_metatata.png "Generate new SP")

Once generated, copy the text highlighted below as *XML* into a file on your server's file system. In the sample configuration below, this file would be expected at `/etc/dfa/colreg/colreg_sp_metadata.xml`. The *YML* highlighted section denotes configuration parameters for the CR configuration file.

Again, see the sample configuration below or the DARIAHSP project for reference.

![SP metadata](https://github.com/DARIAH-DE/colreg/raw/master/img/result_saml_metatata.png "Resulting SP metadata")

### DARIAH Sample configuration

In case a DARIAH-DE service provider needs to be configured, only a small set of parameters need to be changed in comparison to the sample configuration below. Please note that especially the lengthy configuration of the required attributes under ´auth.saml.sp.attributeQuery´ can remain unchanged on all DARIAH-DE based installations. 

In conclusion: typically only the sections *paths*, *api*, *mongo*, *auth.saml.keystore*, *auth.saml.metadata* and *auth.saml.sp* need to be adapted. 

```yaml
paths:
  main: /etc/dfa/colreg
  
api:
  dme:
    baseUrl: https://minfba.de.dariah.eu/dme/
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
    metadata:
      url: https://www.aai.dfn.de/fileadmin/metadata/dfn-aai-test-metadata.xml
      #url: https://www.aai.dfn.de/fileadmin/metadata/dfn-aai-basic-metadata.xml
    sp:
      externalMetadata: ${paths.main}/colreg_sp_metadata.xml
      alias: colreg
      baseUrl: https://colreg.de.dariah.eu/
      entityId: colreg.de.dariah.eu
      securityProfile: metaiop
      sslSecurityProfile: pkix
      sslHostnameVerification: default
      signMetadata: true
      signingAlgorithm: "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"
      signingKey: dfa.de.dariah.eu
      encryptionKey: dfa.de.dariah.eu
      tlsKey: dfa.de.dariah.eu
      requireArtifactResolveSigned: true
      requireAttributeQuerySigned: false
      requireLogoutRequestSigned: true
      requireLogoutResponseSigned: false
      discovery:
        enabled: true
        url: https://wayf.aai.dfn.de/DFN-AAI-Test/wayf
        #url: https://auth.dariah.eu/CDS/WAYF
        return: https://colreg.de.dariah.eu/saml/login/alias/schereg?disco:true
      allowedNameIds : EMAIL, TRANSIENT, PERSISTENT, UNSPECIFIED, X509_SUBJECT
    
      # Attribute querying
      attributeQuery:
        enabled: true
        excludedEndpoints: 
          urls: ["https://ldap-dariah-clone.esc.rzg.mpg.de/idp/shibboleth", "https://idp.de.dariah.eu/idp/shibboleth"]
          assumeAttributesComplete: true
        queryIdp: https://ldap-dariah-clone.esc.rzg.mpg.de/idp/shibboleth
        #queryIdp: https://idp.de.dariah.eu/idp/shibboleth
        queryByNameID: false
        queryAttribute:
          friendlyName: eduPersonPrincipalName
          name: urn:oid:1.3.6.1.4.1.5923.1.1.1.6
          nameFormat: urn:oasis:names:tc:SAML:2.0:attrname-format:uri
        # For now without parameters bc DARIAH Self Service is broken 
        incompleteAttributesRedirect: "https://dariah.daasi.de/Shibboleth.sso/Login?target=/cgi-bin/selfservice/ldapportal.pl"
        #incompleteAttributesRedirect: "https://dariah.daasi.de/Shibboleth.sso/Login?target=/cgi-bin/selfservice/ldapportal.pl%3Fmode%3Dauthenticate%3Bshibboleth%3D1%3Bnextpage%3Dregistration%3Breturnurl%3D{returnUrl}&entityID={entityId}"
        #incompleteAttributesRedirect: "https://auth.dariah.eu/Shibboleth.sso/Login?target=/cgi-bin/selfservice/ldapportal.pl%3Fmode%3Dauthenticate%3Bshibboleth%3D1%3Bnextpage%3Dregistration%3Breturnurl%3D{returnUrl}&entityID={entityId}"
      requiredAttributes:
        - stage: ATTRIBUTES
          required: true
          attributeGroup:
            - check: AND
              attributes:
                - friendlyName: mail
                  name: urn:oid:0.9.2342.19200300.100.1.3
                  nameFormat: urn:oasis:names:tc:SAML:2.0:attrname-format:uri
        - stage: ATTRIBUTES
          required: true
          attributeGroup:
            - check: OR
              attributes:
                - friendlyName: dariahTermsOfUse
                  name: urn:oid:1.3.6.1.4.1.10126.1.52.4.15
                  nameFormat: urn:oasis:names:tc:SAML:2.0:attrname-format:uri
                  value: Terms_of_Use_v5.pdf
                - friendlyName: dariahTermsOfUse
                  name: urn:oid:1.3.6.1.4.1.10126.1.52.4.15
                  nameFormat: urn:oasis:names:tc:SAML:2.0:attrname-format:uri
                  value: foobar-service-agreement_version1.pdf     
        - stage: AUTHENTICATION
          required: true
          attributeGroup:
            - check: AND
              attributes:
                - friendlyName: eduPersonPrincipalName
                  name: urn:oid:1.3.6.1.4.1.5923.1.1.1.6
                  nameFormat: urn:oasis:names:tc:SAML:2.0:attrname-format:uri
        - stage: AUTHENTICATION
          required: false
          attributeGroup:
            - check: OPTIONAL
              attributes:
                - friendlyName: mail
                  name: urn:oid:0.9.2342.19200300.100.1.3
                  nameFormat: urn:oasis:names:tc:SAML:2.0:attrname-format:uri
                - friendlyName: displayName
                  name: urn:oid:2.16.840.1.113730.3.1.241
                  nameFormat: urn:oasis:names:tc:SAML:2.0:attrname-format:uri
```
