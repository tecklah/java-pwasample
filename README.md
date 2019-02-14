# java-pwasample

A sample project that follows Google Progressive Web Application (PWA) tutorial to create a web that display weather information retrieved from Yahoo API. It is important to note that the sample code in Google tutorial is not working as Yahoo API has changed to use OAuth for their authentication.

During my own exploration, I created a dynamic web project on Eclipse IDE and running the code on Tomcat 9 server. As the project is running on localhost, for testing purpose, it is necessary to startup Chrome with its web security disabled so that CORS will not be an issue.

alias ogc='open -a Google\ Chrome --args --disable-web-security'

Learning points :
1. There is a Chrome plugin that allows CORS to be disabled.
