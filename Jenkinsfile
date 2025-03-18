def mainDir="."
def ecrLoginHelper="docker-credential-ecr-login"
def region="ap-northeast-2"
def ecrUrl="207567790847.dkr.ecr.ap-northeast-2.amazonaws.com"
def repository="watchild-backend-dev"
def ecsCluster="watchild-backend-dev-cluster"
def ecsService="watchild-backend-dev-service"

pipeline {
    agent any

    stages {
        stage('Pull Codes from Github'){
            steps{
                checkout scm
            }
        }
        stage('Build Codes by Gradle') {
            steps {
                sh """
                cd ${mainDir}
                chmod +x ./gradlew
                ./gradlew clean
                ./gradlew build
                """
            }
        }

         stage('Build Docker Image by Jib & Push to AWS ECR Repository') {
                    steps {
                        withAWS(region:"${region}", credentials:"aws_key") {
                            ecrLogin()
                            sh """
                                curl -O https://amazon-ecr-credential-helper-releases.s3.us-east-2.amazonaws.com/0.8.0/linux-amd64/${ecrLoginHelper}
                                chmod +x ${ecrLoginHelper}
                                mv ${ecrLoginHelper} /usr/local/bin/
                                cd ${mainDir}
                                ./gradlew jib -Djib.to.image=${ecrUrl}/${repository}:latest -Djib.console='plain'
                            """
                        }
                    }
                }

        stage('Deploy to AWS ECS VM'){
             steps {
                script{
                    try {
                        withAWS(region:"${region}", credentials:"aws_key") {
                            sh"""
                                aws ecs update-service --region ${region} --cluster ${ecsCluster} --service ${ecsService} --force-new-deployment
                            """
                        }
                    } catch (error) {
                        print(error)
                        echo 'Remove Deploy Files'
                        sh "rm -rf /var/lib/jenkins/workspace/${env.JOB_NAME}/*"
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
            post {
                success {
                    echo "The deploy stage successfully."
                }
                failure {
                    echo "The deploy stage failed."
                }
            }
        }
    }
}
