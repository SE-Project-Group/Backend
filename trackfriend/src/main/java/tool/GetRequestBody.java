package tool;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class GetRequestBody {
	public static String getBody(HttpServletRequest request) throws IOException{
		BufferedReader br = request.getReader();
		String str, wholeStr = "";
		while((str = br.readLine()) != null){
			wholeStr += str;
		}
		return wholeStr;
	}
}
