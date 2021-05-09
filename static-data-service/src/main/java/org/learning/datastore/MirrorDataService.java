package org.learning.datastore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MirrorDataService {

	@GetMapping(value = "/getData/{id}")
	public int getData(@PathVariable("id") int id) throws InterruptedException {
		//introducing delay to mimic processing time
		Thread.sleep(100);
		return id;
	}
}
