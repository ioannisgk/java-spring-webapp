pipeline {
  agent any

  stages {
    stage('Checkout Source') {
      steps {
        git 'https://github.com/ioannisgk/java-spring-webapp.git'
        sh 'ls -last'
      }
    }
}
