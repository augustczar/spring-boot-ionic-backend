package com.augustczar.cursomc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.augustczar.cursomc.services.S3Service;

@SpringBootApplication
public class CursomcApplication  implements CommandLineRunner{
	
	@Autowired
	private S3Service s3Service;
	
	public static void main(String[] args){
		SpringApplication.run(CursomcApplication.class, args);
	}
	//methodo nao utilizado no momento, deixado apena com possibilidade de ser utilizado no futuro
	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("/home/augustczar/Pictures/imgloiras/loira.png");
	}

}
