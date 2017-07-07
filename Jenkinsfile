pipeline {
    agent { docker 'maven' }
    stages {
        stage('build') {
            steps {
                sh 'mvn package'
            }
        }
    }
    post {
        always {
            archive 'target/devops-system.war'
        }
    }
}
