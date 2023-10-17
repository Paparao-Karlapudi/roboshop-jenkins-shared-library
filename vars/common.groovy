def compile() {
    if (app_lang == "nodejs") {
        sh 'npm install'
        sh 'env'
    }

    if (app_lang == "maven") {
        sh 'mvn package'
    }
}

def unittests() {
    if (app_lang == "nodejs") {
        //developer is missing unit test case for our project. we proceed further
        // skipping it
        sh 'npm test || true'
    }

    if (app_lang == "maven") {
        sh 'mvn test'
    }

    if (app_lang == "python") {
        sh 'python3 -m unittest'
    }
}
def email(email_note) {
    mail bcc: '', body: "Job failed- ${JOB_BASE_NAME}\nJenkins URL - ${JOB_URL}", cc: '', from: 'paparao.prema75@gmail.com', replyTo: '', subject: "Jenkins job failed at - ${JOB_BASE_NAME}", to: 'paparao.prema75@gmail.com'
}

def artifactPush() {
    sh " echo ${TAG_NAME} > VERSION"
    if (app_lang == "nodejs") {
       sh "zip -r cart-${TAG_NAME}.zip node_modules server.js VERSION"
    }
    sh 'ls -l'
}