package oomtest.service;

import java.util.HashMap;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OomService {
	Logger log = LogManager.getLogger(this.getClass());

	@Value("${byteLength}")
	Integer size;

	HashMap<String,Byte[]> map ;

	@PostConstruct
	public void init() {
		log.info("in few moments oom test will start with byte arrays of size: {}",size);
		this.map = new HashMap<>();
	}

	@Scheduled(initialDelay = 3000, fixedDelay=Long.MAX_VALUE)
	public void goOutOfMemory() {
		try {
			Long l=0L;
			for(;;) {
				Runtime runtime = Runtime.getRuntime();
				long memory = runtime.totalMemory() - runtime.freeMemory();
				log.info("Used memory (KB): " + memory/1000);
				this.map.put(UUID.randomUUID().toString(), generateByteArray(this.size));
				l+=1;
			}
		} catch (OutOfMemoryError e) {
			Runtime runtime = Runtime.getRuntime();
			long memory = runtime.totalMemory() - runtime.freeMemory();
			log.error("------------------>>>   OutOfMemory at (KB): " + memory/1000 , e);
		}
	}

	public Byte[] generateByteArray(int size) {
		Byte[] x = new Byte[size];
		for(int i=0;i<size;i+=1) {
			x[i]='A';
		}
		return x;
	}
}
