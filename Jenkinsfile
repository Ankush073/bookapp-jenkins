pipeline {
    agent any

    /*
     * Jenkins is running locally on Windows.
     * GitHub cannot directly call localhost, so Jenkins polls GitHub.
     */
    triggers {
        pollSCM('H/2 * * * *')
    }

    environment {
        DOCKER_IMAGE = 'ankush0733/bookapprepo'

        K8S_DEPLOYMENT = 'bookapp-deployment'
        K8S_CONTAINER  = 'bookapp'

        MYSQL_MANIFEST = 'k8s/mysql.yml'
        BOOK_MANIFEST  = 'k8s/book.yml'
    }

    options {
        disableConcurrentBuilds()
        timestamps()
    }

    stages {

        stage('Checkout GitHub') {
            steps {
                checkout scm
            }
        }

        stage('Build Spring Boot') {
            steps {
                bat 'mvnw.cmd clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat '''
                    docker build ^
                      -t %DOCKER_IMAGE%:%BUILD_NUMBER% ^
                      -t %DOCKER_IMAGE%:latest ^
                      .
                '''
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {
                    bat '''
                        echo %DOCKER_PASSWORD% | docker login ^
                          --username %DOCKER_USERNAME% ^
                          --password-stdin

                        docker push %DOCKER_IMAGE%:%BUILD_NUMBER%
                        docker push %DOCKER_IMAGE%:latest
                    '''
                }
            }
        }

        stage('Deploy MySQL') {
            steps {
                bat '''
                    kubectl apply -f %MYSQL_MANIFEST%

                    kubectl rollout status deployment/mysql-deployment ^
                      --timeout=180s
                '''
            }
        }

        stage('Deploy Book Application') {
            steps {
                bat '''
                    kubectl apply -f %BOOK_MANIFEST%

                    kubectl set image ^
                      deployment/%K8S_DEPLOYMENT% ^
                      %K8S_CONTAINER%=%DOCKER_IMAGE%:%BUILD_NUMBER%

                    kubectl rollout status ^
                      deployment/%K8S_DEPLOYMENT% ^
                      --timeout=300s
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                bat '''
                    echo ===== Kubernetes node =====
                    kubectl get nodes

                    echo ===== Deployments =====
                    kubectl get deployments

                    echo ===== Pods =====
                    kubectl get pods

                    echo ===== Services =====
                    kubectl get services
                '''
            }
        }
    }

    post {
        success {
            echo "Build ${BUILD_NUMBER} deployed successfully."
        }

        failure {
            echo "Pipeline failed. Check the failed stage and console output."
        }

        always {
            bat 'docker logout'
        }
    }
}