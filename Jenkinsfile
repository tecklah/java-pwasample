pipeline {
    agent any
    tools {
		maven 'apache-maven-3.6.0' 
	}
    stages {
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