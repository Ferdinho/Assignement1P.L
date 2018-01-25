// Ferdinand Tembo

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoursSchedule {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL("https://www.bellevuecollege.edu/classes");
		System.out.print("Enter quarter : ");
		Scanner sc = new Scanner(System.in);
		String quarter = sc.next();
		System.out.print("Enter Year : ");
		int year = sc.nextInt();
		System.out.print("Enter the initial of the program : ");
		String programLetter = sc.next();
		sc.nextLine();
		System.out.println();

		URL url1 = new URL("https://www.bellevuecollege.edu/classes/"+quarter+year+"?letter="+programLetter);
		BufferedReader in = new BufferedReader(new InputStreamReader(url1.openStream()));

		String input = "";
		String text = "";

		while ((input = in.readLine())!= null) {
			text += input + " \n";
		}

		Pattern pattern = Pattern.compile("<li class=\"subject-name\">\\s*<a href=\"/[a-z]*/[a-zA-Z]*\\d{4}/[A-Z]*\">(.*)</a>(.*&?)");
		Matcher matcher = pattern.matcher(text);
		System.out.println("Programs:");
		while(matcher.find()) {
			System.out.print(matcher.group(1));
			System.out.println(matcher.group(2));
		}
		System.out.println();
		System.out.print("Enter the program's name : ");
		String programName = sc.nextLine();
		System.out.print("Enter the course ID : ");
		String courseID = sc.nextLine();
		System.out.println();

		String text_display = programName+" Courses in "+quarter+" "+year;
		System.out.println(text_display);
		for( int i =0 ; i < text_display.length(); i ++) {
			System.out.print("=");
		}

		String ID = courseID.split(" ")[0];
		System.out.println();

		//https://www.bellevuecollege.edu/classes/Winter2018/INDES
		url1 = new URL("https://www.bellevuecollege.edu/classes/"+quarter+year+"/"+ID);
		in = new BufferedReader(new InputStreamReader(url1.openStream()));
		String pattern1="<span class=\"courseID\">(.*)<\\/span> <span class=\"courseTitle\">(.*)<\\/span>" ;
		String pattern2 = "Item number:.<.span>(.*)<.span>";
		String pattern3 = "SearchString=[a-zA-Z]*.[a-zA-Z]*.>(.*)<\\/a>";
		String pattern4 = "<abbr title=\"(Monday.*|Tuesday.*|Wednesday.*|Thursday.*|Friday.*|Daily)\">";
		String patternOnline = "<span class=\"days online\">(.*)<\\/span>";

		Pattern patternOne = Pattern.compile(pattern1);
		Pattern patternTwo = Pattern.compile(pattern2);
		Pattern patternThree = Pattern.compile(pattern3);
		Pattern patternFour = Pattern.compile(pattern4);
		Pattern patternFive = Pattern.compile(patternOnline);

		boolean matchFound=false;
		String code ="", title="", itemId="",instructor="";
		ArrayList<String> days= new ArrayList<String>() ;;
		while ((input = in.readLine())!= null) {
			text = input + " \n";
			Matcher matcher1 = patternOne.matcher(text);
			Matcher matcher2 = patternTwo.matcher(text);
			Matcher matcher3 = patternThree.matcher(text);
			Matcher matcher4 = patternFour.matcher(text);
			Matcher matcher5 = patternFive.matcher(text);

			if(matcher1.find()) {
				if(!itemId.equals("")) {
					if(!itemId.equals("")) {
						displayCourse(code,title,itemId,instructor,days);
						for( int i =0 ; i < text_display.length(); i ++) {
							System.out.print("=");
						}
						System.out.println();
						itemId="";
					}
				}
				if (matcher1.group(1).equalsIgnoreCase(courseID)) {
					matchFound=true;

					code = matcher1.group(1);
					title = matcher1.group(2);
				}
				else {
					code ="";
					title="";
					matchFound=false;
				}
			}
			if (matchFound) {
				if(matcher2.find()) {

					if(!itemId.equals("")) {
						displayCourse(code,title,itemId,instructor,days);
						for( int i =0 ; i < text_display.length(); i ++) {
							System.out.print("=");
						}
						System.out.println();
						itemId="";
						instructor="";
					}
					itemId= matcher2.group(1);
					days= new ArrayList<String>() ;

				}
				if(matcher3.find()) {

					instructor = matcher3.group(1);
				}
				if(matcher4.find()) {
					days.add(matcher4.group(1));
				}

				if(matcher5.find()) {
					days.add(matcher5.group(1));
				}
			}
		}

	}

	public static void displayCourse(String code, String title, String itemId, String instructor,List<String> days) {
		System.out.println("Code :"+code);
		System.out.println("Title :"+title);
		System.out.println("Item# :"+itemId);
		System.out.println("Instructor :"+instructor);
		for(String i : days) {
			System.out.println("days :"+i);
		}

	}

}
