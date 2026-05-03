pipeline {
    agent any

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    parameters {
        booleanParam(name: 'RUN_BUILD', defaultValue: true, description: 'Run build (package) before running tests')
    }

    stages {
        stage('Clone/Pull Repository') {
            steps {
                // use the repository configured for the job (checkout scm)
                checkout scm
            }
        }

        stage('Build (optional)') {
            when {
                expression { return params.RUN_BUILD }
            }
            steps {
                script {
                    // Use the Maven wrapper if present, otherwise fall back to mvn
                    if (fileExists('mvnw')) {
                        if (isUnix()) {
                            sh './mvnw -B -DskipTests package'
                        } else {
                            bat 'mvnw.cmd -B -DskipTests package'
                        }
                    } else {
                        if (isUnix()) {
                            sh 'mvn -B -DskipTests package'
                        } else {
                            bat 'mvn -B -DskipTests package'
                        }
                    }
                }
            }
        }

        stage('Run Unit Tests') {
            steps {
                script {
                    if (fileExists('mvnw')) {
                        if (isUnix()) {
                            sh './mvnw -B test'
                        } else {
                            bat 'mvnw.cmd -B test'
                        }
                    } else {
                        if (isUnix()) {
                            sh 'mvn -B test'
                        } else {
                            bat 'mvn -B test'
                        }
                    }
                }
            }
            post {
                always {
                    // Publish JUnit XML reports produced by Surefire/Failsafe
                    junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: false
                    // Archive produced artifacts (optional)
                    archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished. Check 'Test Result' and 'Console Output' for details."
        }
    }
}