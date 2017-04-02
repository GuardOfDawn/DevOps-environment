node {
    stage('SCM') {
        git 'https://github.com/GuardOfDawn/DevOps-environment.git'
    }
    stage('QA') {
        sh 'sonar-scanner'
    }
}
