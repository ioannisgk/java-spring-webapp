pipeline {
  agent {
      kubernetes {
          inheritFrom 'maven'
      }
  }
  stages {
    stage('Application Build') {
      steps {
        container('maven') {
          sh '''
            mvn -v
            mvn clean install
            ls -last
          '''
        }
      }
    }
  }
}
