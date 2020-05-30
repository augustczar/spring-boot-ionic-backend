package com.augustczar.cursomc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication  implements CommandLineRunner{
	
	public static void main(String[] args){
		SpringApplication.run(CursomcApplication.class, args);
	}
	//methodo nao utilizado no momento, deixado apena com possibilidade de ser utilizado no futuro
	@Override
	public void run(String... args) throws Exception {
		
	}

}
