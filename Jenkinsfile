pipeline {
  environment {
    REGISTRY_REPOSITORY = "ioannisgk/spring-demo"
    GIT_EMAIL = "jenkins@email.com"
    GIT_USERNAME = "jenkins"
    GIT_REPOSITORY = "github.com/ioannisgk/kubernetes-infrastructure.git"
    NEW_IMAGE_TAG = "v0.0${BUILD_NUMBER}"
    DEPLOYMENT_FILE_PATH = "development/spring-app-demo/spring-app-deployment.yaml"
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
            /kaniko/executor --context . --destination ${REGISTRY_REPOSITORY}:${NEW_IMAGE_TAG}
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
              sh '''
                mkdir temp && cd temp
                git config --global user.email ${GIT_EMAIL}
                git config --global user.name ${GIT_USERNAME}
                git init
                git clone https://$USERNAME:$PASSWORD@${GIT_REPOSITORY}
                git remote add origin https://${GIT_REPOSITORY}
                git branch -M main
                cd kubernetes-infrastructure
                
                OLD_IMAGE_TAG=$(grep -E ${REGISTRY_REPOSITORY} ${DEPLOYMENT_FILE_PATH} | cut -d : -f 3)
                sed -ie "s/${OLD_IMAGE_TAG}/${NEW_IMAGE_TAG}/" ${DEPLOYMENT_FILE_PATH}
                
                git add .
                git commit -m "Update Spring demo app version to ${NEW_IMAGE_TAG}"
                git push -uf origin main 
              '''
            }
        }
      }
    }
  }
}
