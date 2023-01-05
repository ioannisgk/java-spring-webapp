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
        
      }
    }
  }
}
