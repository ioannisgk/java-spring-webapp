pipeline {
  agent {
      kubernetes {
          inheritFrom 'default'
      }
  }
  stages {
    stage('Checkout Source') {
      steps {
        echo "Before"
        sh 'ls -last'
        git 'https://github.com/ioannisgk/java-spring-webapp.git'
        echo "After"
        sh 'ls -last'
      }
    }
}
