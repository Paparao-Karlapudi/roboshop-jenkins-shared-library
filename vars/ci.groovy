def call() {
    pipeline {

        agent {
            label 'workstation'
        }

        stages {

            stage('Compile/Build') {
                steps {
                    echo 'compile'
                }
            }

            stage('Unit Tests') {
                steps {
                    echo 'unit tests'
                }
            }

            stage('Quality control') {
                steps {
                    echo 'Quality control'
                }
            }
            stage('Upload code to centralized repo') {
                steps {
                    echo 'upload'
                }
            }
        }
    }
}