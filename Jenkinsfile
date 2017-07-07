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
        stage('Quality Gate') {
            timeout(time:1,unit:'HOURS') {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
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
