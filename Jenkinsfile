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
        stage('One') {
            steps {
                echo 'Hi'
            }
        }
        stage('Two') {
            steps {
                input('Proceed?')
            }
        }
       	stage('Three') {
       		when {
       			not {
       				branch "master"
       			}
       		}
            steps {
                echo 'Hello'
            }
        }
    }
}