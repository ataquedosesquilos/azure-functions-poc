package nga.hrx.utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
	
	private static String HOME_FOLDER = "D:\\home\\site\\wwwroot\\";
	
	public static String readFileIntoString(String filename) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filename)));
	}
	
	public static String getFilePath() {
		return HOME_FOLDER;
	}
	
}
