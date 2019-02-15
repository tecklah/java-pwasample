pipeline {
    agent any
    tools { 
        maven 'Maven360' 
        jdk 'jdk8' 
    }
    stages {
    	stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "JAVA_HOME = ${JAVA_HOME}"
                ''' 
            }
        }
        stage('Approval') {
            steps {
                input('Proceed?')
            }
        }
 		stage ('SCM Checkout') {
            steps {
                git 'https://github.com/tecklah/java-pwasample.git'
            }
        }
		stage ('Build') {
            steps {
                sh 'mvn package' 
            }
        }
        stage ('Deploy') {
        	steps {
        		sh curl --upload-file target/*.war -u deployer:deployer "http://localhost:9080/manager/text/deploy?path=/PwaSample&update=true"
        	}
        }
    }
}