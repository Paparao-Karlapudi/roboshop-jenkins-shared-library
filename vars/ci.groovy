def call() {
    try {
        node('workstation') {

            stage('Checkout') {
                cleanWs()
                git branch: 'main', url: 'https://github.com/Paparao-Karlapudi/cart'
            }

            stage('Compile/Build') {
                common.compile()
            }

            stage('Unit Tests') {
                common.unittests()
            }

            stage('Quality control') {
                SONAR_PASS = sh(script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                SONAR_USER = sh(script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.user --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()

                wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", VAR: 'SECRET']]]) {
                    sh "sonar-scanner -Dsonar.host.url=http://172.31.88.23:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=cart"

                }
            }
            stage('Upload ARTIFACTS to centralized repo') {
                echo 'upload'
            }

        }
    } catch(Exception e) {
        common.email("Failed")
    }
}