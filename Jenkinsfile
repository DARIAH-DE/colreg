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
    def pom = readMavenPom file: 'colreg-ui/pom.xml'
    def uiVersion = pom.parent.version
    def snapshot = uiVersion.contains("SNAPSHOT")

    if (!snapshot) {
        echo "publishing deb package colreg-ui for non SNAPSHOT version ${uiVersion}"

        sh "aptly repo add snapshots colreg-ui/target/*.deb"
        sh "aptly publish update trusty"
        sh "rm colreg-ui/target/*.deb"
    }
    else {
        echo "deb package colreg-ui for SNAPSHOT version ${uiVersion} will not be published"
    }
  }
}
