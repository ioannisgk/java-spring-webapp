pipeline {
  environment {
    REGISTRY_REPOSITORY = "ioannisgk/hello-kaniko"
  }
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
          - name: kaniko
            image: gcr.io/kaniko-project/executor:debug
            imagePullPolicy: Always
            command:
            - sleep
            args:
            - 9999999
            volumeMounts:
              - name: jenkins-docker-cfg
                mountPath: /kaniko/.docker
          - name: git
            image: alpine/git:2.36.3
            command:
            - cat
            tty: true
          volumes:
          - name: jenkins-docker-cfg
            projected:
              sources:
              - secret:
                  name: docker-credentials
                  items:
                    - key: .dockerconfigjson
                      path: config.json
        '''
    }
  }
  stages {
    stage('Build Application') {
      steps {
        container('maven') {
          sh '''
            mvn -v
            mvn clean install
          '''
        }
      }
    }
    stage('Kaniko Build & Push Image') {
      steps {
        container('kaniko') {
          sh '''
            ls -last
            /kaniko/executor --context . --destination ${REGISTRY_REPOSITORY}:v0.0${BUILD_NUMBER}
          '''
        }
      }
    }
    stage('Deploy to Kubernetes') {
      steps {
        container('git') {
          withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh '''
              git clone https://$USERNAME:$PASSWORD@github.com/ioannisgk/kubernetes-infrastructure.git
            '''
          }
        }
      }
    }
  }
}
