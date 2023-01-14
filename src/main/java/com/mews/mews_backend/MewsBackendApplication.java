package com.mews.mews_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MewsBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(MewsBackendApplication.class, args);
		// 메모리 사용량 출력
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("HEAP Size(M) : "+ heapSize / (1024*1024) + " MB");
	}

}
