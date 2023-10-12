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
        sh 'npm test'
    }

    if (app_lang == "maven") {
        sh 'mvn test'
    }

    if (app_lang == "python") {
        sh 'python3 -m unittest'
    }
}
def email(email_note) {
    mail bcc: '', body: 'Testing email sending from jenkins', cc: '', from: 'paparao.prema75@gmail.com', replyTo: '', subject: 'Test From Jenkins', to: 'paparao.prema75@gmail.com'
}