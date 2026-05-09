package generic;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;

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

    public static String getProperties(String env, String propertyKey) {
        String keyVal=null;
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/"+env+".properties");
            Properties prop = new Properties();
            prop.load(fis);
            keyVal = prop.getProperty(propertyKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyVal;
    }
}