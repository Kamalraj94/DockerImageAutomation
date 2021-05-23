package Test.Docker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.DockerClientBuilder;

/****
 * If the image has to be run in specific say Environment/Port/ , I have provided the entire configuration in README.md file *
 * IMPORTANT - Run this from Testng.xml suite *
 * This code is written for Automating the Docker image functionality using Docker Client API* 
 * It mainly covers Starting / Stopping the Docker image by container formula * 
 * I have used a Dummy image called hello world * 
 * Any kind of image can be used in this code for switching on/off * 
 * 
 * @author B.Kamal Raj
 *
 */

public class DockerTest {

	DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2375").build();
	List<Container> RunningContainer = dockerClient.listContainersCmd().exec();
	String image = "hello-world";
	String imageName = "tutum/hello-world";
	String localImagePathName = "hello.tar.gz";

	@BeforeTest
	public void checkContainer() throws FileNotFoundException, IOException {
		boolean b = false;
		RunningContainer = dockerClient.listContainersCmd().exec();
		for (int k = 0; k < RunningContainer.size(); k++) {
			if (RunningContainer.get(k).getImage().equals("hello-world")) {
				b = true;
				System.out.println("Hello-world docker image is already running");
				break;
			}
		}
		if (b == false) {
			StartContainer();
		}
	}

	public void StartContainer() throws FileNotFoundException, IOException {

		String imagePath = System.getProperty("user.dir") + "\\" + localImagePathName;
		try (InputStream is = new FileInputStream(imagePath)) {
			dockerClient.loadImageCmd(is).exec();
			CreateContainerCmd createCommand = dockerClient.createContainerCmd("docker_service")
					.withName(image);
			CreateContainerResponse response = (CreateContainerResponse) createCommand.withImage(imageName)
					.exec();
			dockerClient.startContainerCmd(response.getId()).exec();
			System.out.println("Container Id:" + response.getId());
			System.out.println("Hello-world docker image is running");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterTest
	public void containerStatus() throws IOException {
		RunningContainer = dockerClient.listContainersCmd().exec();
		for (int i = 0; i < RunningContainer.size(); i++) {
			if (RunningContainer.get(i).getImage().equals(imageName)) {
				dockerClient.stopContainerCmd(RunningContainer.get(i).getId()).exec();
				dockerClient.removeContainerCmd(RunningContainer.get(i).getId()).exec();
				System.out.println("Hello-world docker image is stopped running");
				break;
			}
		}
	}

}
