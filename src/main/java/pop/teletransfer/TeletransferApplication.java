package pop.teletransfer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeletransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeletransferApplication.class, args);
	}
}
