//package Start;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public abstract class DateGen {
	
	public static String getDateTime() {
		StringBuilder output = new StringBuilder();
		
		DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDateTime currentDate = LocalDateTime.now();
		
		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime currentTime = LocalDateTime.now();
		
		output.append("Date: " + date.format(currentDate));
		output.append("\nTime: " + time.format(currentTime));
		
		return output.toString();
	}
	

}
