pipeline {
    agent any
    tools {
        maven 'Maven'
    }
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
                archive 'target/devops-system.war'
            }
        }
    }
}
