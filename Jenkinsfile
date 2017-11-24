node {
  def mvnHome
  
  stage('Preparation') {
    git 'https://github.com/DARIAH-DE/colreg.git'
    mvnHome = tool 'Maven 3.0.4'
  }

  stage('Build') {
    sh "'${mvnHome}/bin/mvn' -U -Pdariah.deb -Dmaven.test.failure.ignore clean package"
  }

  stage('Publish') { 
    def pom = readMavenPom file: 'pom.xml'
    def uiVersion = pom.project.properties.eu.dariah.de.colreg.colreg-model
    def release = uiVersion.contains("RELEASE")
    def snapshot = uiVersion.contains("SNAPSHOT")

    if (snapshot || release) {
        echo "publishing deb package colreg-ui for " + (snapshot ? "SNAPSHOT" : "RELEASE") + " version ${uiVersion}"
		
        sh "PLOC=\$(ls colreg-ui/target/*.deb); curl -X POST -F file=@\${PLOC} http://localhost:8008/api/files/colreg-ui-${uiVersion}"
        sh "curl -X POST http://localhost:8008/api/repos/" + (snapshot ? "snapshots" : "releases") + "/file/colreg-ui-${uiVersion}"
        sh "curl -X PUT -H 'Content-Type: application/json' --data '{}' http://localhost:8008/api/publish/:./trusty"
        sh "rm colreg-ui/target/*.deb"
        sh "rm colreg-ui/target/*.changes"

    }
    else {
        echo "deb package colreg-ui for version ${uiVersion} will not be published"
    }
  }
}
