def compile() {
    if (app_lang == "nodejs") {
        sh 'npm install'
    }

    if (app_lang == "maven") {
        sh 'mvn package'
    }
}

def unittests() {
    if (app_lang == "nodejs") {
        //developer is missing unit test case for our project. we proceed further
        // skipping it
        //sh 'npm test'
        sh 'eco test cases'
    }

    if (app_lang == "maven") {
        sh 'mvn test'
    }
}