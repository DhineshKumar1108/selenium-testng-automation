pipeline {

    agent any

    tools {
        maven 'Maven3'
    }

    triggers {
        cron('H 0 * * *')
    }

    parameters {

        choice(
            name: 'SUITE_TYPE',
            choices: ['CI_Smoke', 'Full_Regression'],
            description: 'Select the TestNG suite to execute'
        )

        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'headless-chrome'],
            description: 'Select browser'
        )
    }

    stages {

        stage('Checkout Code') {
            steps {
                cleanWs()
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Smoke Tests') {

            when {
                expression {
                    params.SUITE_TYPE == 'CI_Smoke'
                }
            }

            steps {

                echo "Running CI Smoke Tests"

                script {

                    catchError(
                        buildResult: 'UNSTABLE',
                        stageResult: 'FAILURE'
                    ) {

                        sh """
                            mvn test \
                            -DsuiteXmlFile=src/test/resources/testng-smoke.xml \
                            -Dbrowser=${params.BROWSER}
                        """
                    }
                }
            }
        }

        stage('Run Regression Tests') {

            when {
                expression {
                    params.SUITE_TYPE == 'Full_Regression'
                }
            }

            steps {

                echo "Running Full Regression Tests"

                script {

                    catchError(
                        buildResult: 'UNSTABLE',
                        stageResult: 'FAILURE'
                    ) {

                        sh """
                            mvn test \
                            -DsuiteXmlFile=src/test/resources/testng-regression.xml \
                            -Dbrowser=${params.BROWSER}
                        """
                    }
                }
            }
        }

        stage('Publish Extent Report') {

            steps {

                publishHTML(target: [
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'test-output/ExtentReport',
                    reportFiles: 'index.html',
                    reportName: 'Extent Automation Report'
                ])
            }
        }
    }

    post {

        always {

            junit(
                allowEmptyResults: true,
                testResults: '**/target/surefire-reports/*.xml'
            )

            archiveArtifacts(
                artifacts: 'test-output/screenshots/**',
                allowEmptyArchive: true
            )

            archiveArtifacts(
                artifacts: 'test-output/ExtentReport/**',
                allowEmptyArchive: true
            )
        }

        success {

            echo "Automation execution completed successfully."
        }

        unstable {

            echo "Build completed with test failures - check the Extent Automation Report and archived screenshots."
        }

        failure {

            echo "Automation execution failed before tests could complete. Check the Jenkins console output."
        }
    }
}