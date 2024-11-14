How to run the service

#Using maven - From the project root directory
		Run command
		mvn spring-boot:run


#With Jar file

1. Build jar file using
 		mvn install

2. Run the jar file with command
 	java -jar taxservice-0.0.1-SNAPSHOT


#With Dockerfile

1. Build docker image using
 	docker build -t tax services .

2. Run docker container using
 	docker run -it -p 8080:8080 tax service


#With IDE
  Import as maven project
		Run from main class
