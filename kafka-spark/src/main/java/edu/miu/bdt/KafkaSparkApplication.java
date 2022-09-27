package edu.miu.bdt;

import edu.miu.bdt.service.KafkaSparkService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@RequiredArgsConstructor
@EnableElasticsearchRepositories(basePackages = {"edu.miu.bdt.repo"})
public class KafkaSparkApplication implements CommandLineRunner {

	private final KafkaSparkService kafkaSparkService;

	public static void main(String[] args){
		SpringApplication.run(KafkaSparkApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		kafkaSparkService.sync();
	}
}
