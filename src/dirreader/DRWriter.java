package dirreader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DRWriter {

	boolean existsFile(String out) throws IOException {
	  	File target = new File(out);
	  	boolean exists = target.exists(); 
	  	if(!exists) {
	  		target.createNewFile();
	  	}
	  	return exists;
	}
	
	boolean canWriteFile(String out) {
	  	File target = new File(out);
	  	return target.canWrite();
	}	
	
	void writeFile(String content, String out) throws IOException {
	  	File target = new File(out);
  		FileWriter fw = new FileWriter(target);
	  	fw.write(content);
	  	fw.close();
	}	
}
