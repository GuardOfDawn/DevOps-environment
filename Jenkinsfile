pipeline {
    agent any
    stages {
        stage('Test') {
            withSonarQubeEnv {
                sh 'mvn clean sonar:sonar'
            }
        }
        stage('Build') {
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
