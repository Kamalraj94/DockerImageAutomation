GUIDELINES 

- Runner file is located in the path src/test/java -> package Test.Docker -> class DockerTest.java
- Run the code from TestNg xml file 
- If you want to test your own image , save the image in user directory and rename the image , image name , path , image file name in class file 
- Image stored in the user dir should be in .tar/.tar.gz format 
- you can convert the container to .tar format using the command "docker export <ContainerID> -o .tar.gz" in command prompt . <ContainerID> you can get if u run the docker image manually and enter "docker ps" in command prompt so that containerID can be fetched


REQUIREMENTS 
- Java 12 , Maven , TestNg installed 
- Docker client maven dependency is used for automation
- Docker installation in your system is mandatory 
- After installing the docker , open the docker application -> settings -> General -> select "Expose daemon on tcp://localhost:2375 without TLS"


CODE FUNCTIONALITY 

CHECKCONTAINER():

This method checks whether the respective image which we are going to run is already running or not . If it is running code stops and print image is already runnning .If it is not running , execution will jump to startContainer() method . 

STARTCONTAINER():

This method will get the path of the image from project .
Load the image , create the container for the image . You yourself can name the image.
After creating the container , it will start the container , so that image starts running 

CONTAINERSTATUS():

This method is used to stop the container from running and cleans up permanently .
So that docker image is stopped from running successfully

CONFIGURATION - methods defination

Suppose , your docker image has to be run in the specific environment or ports mean you can run in the respective format also 
say your image only supports with the environements ,
Image_name = "Login"
Database_server = "local_db_url"
Databse_user_name = "username"
Databse_password = "password"
Local_server_name = "server_name"
Local_server_Port =  "1010:1010"
Exposed_Port = "9999"

Then your code in the line no 63 (DockerTest.Java) will be as follows 
CreateContainerCmd createCommand = dockerClient.createContainerCmd("docker_service")
	                              .withName(Login)
								  .withEnv(
								  "Database_server = "local_db_url"",
								  "Database_user_name = "username"",
						          "Databse_password = "password"",
								  "Local_server_name = "server_name"".
								   withPortBindings(PortBinding.parse(1010:1010)).
								   withExposedPorts(ExposedPort.tcp(9999));
								
//Thanks
 Kamal Raj								
								   


