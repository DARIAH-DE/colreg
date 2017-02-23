node {
  def mvnHome
  
  stage('Preparation') {
    git 'https://github.com/DARIAH-DE/colreg.git'
    mvnHome = tool 'Maven 3.0.4'
    def urgl = ${env.POM_ARTIFACTID}
    deg argl = ${env.POM_VERSION}
  }

  stage('Build') {
    echo 'urgl'
    echo urgl
    echo ${urgl}
    sh "'${mvnHome}/bin/mvn' -U -Pdariah.deb -Dmaven.test.failure.ignore clean package"
  }

  stage('Publish') {
    sh "aptly repo add snapshots colreg-ui/target/*.deb"
    sh "aptly publish update trusty"
    sh "rm colreg-ui/target/*.deb"
  }
}
