//Ferdinand Tembo

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File ("A.java");
		Scanner scanner = new Scanner(file);
		
		String input = "";
		String text = "";
		while(scanner.hasNextLine()) {
			input = scanner.nextLine();
			text += input+ "\n";
		}
	
		Pattern pattern = Pattern.compile("\\s*([a-zA-Z]*)\\s+([a-zA-Z]*)\\s*(=(.*))?;");
		Matcher matcher = pattern.matcher(text);
		
		while(matcher.find()) {
			System.out.println("Type: "+matcher.group(1));
			System.out.println("Variable name: "+matcher.group(2));
			System.out.println("test: "+matcher.group(4));
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		}
		
	}

}
