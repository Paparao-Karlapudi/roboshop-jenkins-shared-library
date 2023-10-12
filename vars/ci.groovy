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
    } catch(Exception e) {
        common.email("Failed")
    }
}