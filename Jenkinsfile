pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn clean sonar:sonar'
                }
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
