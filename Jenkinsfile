pipeline {
  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: maven
            image: maven:3.8.7-eclipse-temurin-11-alpine
            command:
            - cat
            tty: true
        '''
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
