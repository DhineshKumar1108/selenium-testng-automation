package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	
	private static Properties properties;
	
	private static void loadProperties()
	{
		if(properties==null)
		{
			properties = new Properties();
			String path = "src/test/java/resources/config.properties";
			try(InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")){
				properties.load(input);
				
			}
			catch(IOException e){
				throw new RuntimeException("Could not load config properties at"+ path, e);
			}
		}
	}
	
	public static String get(String key) {
		loadProperties();
		String value = properties.getProperty(key);
		if(value==null)
		{
			throw new RuntimeException("Missing Key '" + value + "' in congig.properties");
		}
		return value.trim();
		
	}
	
	public static int getInt(String key)
	{
		return Integer.parseInt(get(key));
	}
	
	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(get(key));
	}

}
