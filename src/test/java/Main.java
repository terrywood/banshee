

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;

public class Main {
	public static void main(String[] args) throws ParseException {

		String str=  "0\u00000\u00000\u00000\u00001\u0000.\u0000H\u0000K";
		System.out.println(str);
		System.out.println(new String(str.getBytes()));

	}


}
