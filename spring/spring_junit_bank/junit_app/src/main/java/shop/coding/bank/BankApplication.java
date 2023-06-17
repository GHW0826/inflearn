package shop.coding.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(BankApplication.class, args);
//		String[] iocNames = run.getBeanDefinitionNames();
//		for (String iocName : iocNames) {
//			System.out.println("iocName = " + iocName);
//		}
	}

}
