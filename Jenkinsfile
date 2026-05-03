pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/Salmanahmed22/junit-mocks-assignment'
            }
        }

        stage('Build Project') {
            steps {
                sh './mvnw clean compile'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh './mvnw test'
            }
        }
    }
}