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
            mvn clean install
            ls -last
          '''
        }
      }
    }
  }
}
