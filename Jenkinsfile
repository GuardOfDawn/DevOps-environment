pipeline {
    agent any
    stages {
        stage('Test') {
            withSonarQubeEnv('http://218.94.159.101:9000') {
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
