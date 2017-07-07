pipeline {
    agent any
    def mvn = tool 'Maven'
    stages {
        stage('Test') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '${mvn}/bin/mvn clean sonar:sonar'
                }
            }
        }
        stage('Build') {
            steps {
                sh '${mvn}/bin/mvn package'
            }
        }
    }
    post {
        always {
            archive 'target/devops-system.war'
        }
    }
}
