pipeline {
  environment {
    REGISTRY_REPOSITORY = "ioannisgk/spring-demo"
    GIT_EMAIL = "jenkins@email.com"
    GIT_USERNAME = "jenkins"
    GIT_REPOSITORY = "github.com/ioannisgk/kubernetes-infrastructure.git"
    GIT_CREDS = credentials('github-credentials')
    GIT_CLONE_REPOSITORY = sh(returnStdout: false, script: """
      #!/bin/bash
      mkdir temp && cd temp
      git config --global user.email ${GIT_EMAIL}
      git config --global user.name ${GIT_USERNAME}
      git init
      git clone https://${GIT_CREDS_USR}:${GIT_CREDS_PSW}@${GIT_REPOSITORY}
      git remote add origin https://${GIT_REPOSITORY}
      git branch -M main
      cd kubernetes-infrastructure   
    """)
    GIT_CLONE_REPOSITORY2 = sh(returnStdout: false, script: """
      #!/bin/bash
      echo "test" > a.txt
    """)
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
            - cat
            tty: true
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
            #mvn clean install
          '''
        }
      }
    }
    stage('Kaniko Build & Push Image') {
      steps {
        container('kaniko') {
          sh '''
            ls -last
            #/kaniko/executor --context . --destination ${REGISTRY_REPOSITORY}:v0.0${BUILD_NUMBER}
          '''
        }
      }
    }
    stage('Deploy to Kubernetes') {
      steps {
        container('git') {
          withCredentials([usernamePassword(
            credentialsId: 'github-credentials',
            usernameVariable: 'USERNAME',
            passwordVariable: 'PASSWORD')])
            {
              script {
                //GIT_CLONE_REPOSITORY
                echo "1st script run"
                GIT_CLONE_REPOSITORY2
              }
              sh '''
                ls -last
                
                echo "DONE!!!"
              '''
            }
        }
      }
    }
  }
}
