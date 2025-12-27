package generic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public class ConfigFileReader {

	static String value = "";
	
	public static String getPropertyValue(String propertyFilePath, String propertyKey) {
		try {
			
				File deviceProperty = new File(propertyFilePath+"."+"properties");
				BufferedReader reader = new BufferedReader(new FileReader(deviceProperty));
				String line;
				Iterator<String> lines = reader.lines().iterator();
				while(lines.hasNext()){
					line=lines.next();
					if(line.contains(propertyKey)){
						value = line.split("=")[1];
					}
				}
			
		}catch (FileNotFoundException e) {
			 e.printStackTrace();
		}
		
		return value;
	}

}
