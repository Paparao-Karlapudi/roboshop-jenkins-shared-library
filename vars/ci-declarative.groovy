def call() {
    try {
        pipeline {

            agent {
                label 'workstation'
            }

            stages {

                stage('Compile/Build') {
                    steps {
                        script {
                            common.compile()
                        }
                    }
                }

                stage('Unit Tests') {
                    steps {
                        script {
                            common.unittests()
                        }
                    }
                }

                stage('Quality control') {
                    environment {
                        SONAR_USER = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.user --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
                        //SONAR_PASS = '$(aws ssm get-prameters --region us-eat-1 --names sonarqube.pass --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
                    }
                    steps {
                        script {
                            SONAR_PASS = sh(script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                            wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", VAR: 'SECRET']]]) {
                                sh "sonar-scanner -Dsonar.host.url=http://172.31.88.23:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=cart"
                            }
                        }
                    }
                }
                stage('Upload code to centralized repo') {
                    steps {
                        echo 'upload'
                    }
                }
            }
        }
    } catch(Exception e) {
        common.email("Failed")
    }
}