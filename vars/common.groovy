def compile() {
    if (app_lang == "nodejs") {
        sh 'npn install'
    }

    if (app_lang == "maven") {
        sh 'mvn package'
    }
}