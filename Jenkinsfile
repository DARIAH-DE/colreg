node {
   def mvnHome
   stage('Preparation') {
      git 'https://github.com/DARIAH-DE/colreg.git'
      mvnHome = tool 'Maven 3.0.4'
   }
   stage('Build') {
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' -U -Dmaven.test.failure.ignore clean package"
      } else {
         bat(/"${mvnHome}\bin\mvn" -U -Dmaven.test.failure.ignore clean package/)
      }
   }
}
