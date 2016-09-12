import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

public class Main {
	public static void main(String[] args) throws ParseException, IOException {


		//String cubeInfo = FileUtils.readFileToString(new File("D:\\Terry\\workspace\\banshee\\src\\test\\data.json"),"UTF-8");
		Main main = new Main();
		main.testObject();

	/*	long json =0;
		long json2 =0;
		//System.setProperty("http.agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
		for(int i = 0;i<50;i++){
			json2+= main.testGJSON();
			json+= main.testObject();
			System.out.println("------------------------");
		}
         System.out.println("--------------------summary-------------------");
		System.out.println(json);
		System.out.println(json2);*/
	}

	ObjectMapper objectMapper = new ObjectMapper();
	public long  testObject() throws IOException {
		long s = System.currentTimeMillis();

		HttpURLConnection connection = null;
		try {
			// URL url = new URL("https://xueqiu.com/P/ZH914042");
			URL url = new URL("https://xueqiu.com/P/ZH902949"); // cheng lao shi
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
			InputStream in = connection.getInputStream();
			BufferedReader bd = new BufferedReader(new InputStreamReader(in));

			//StringBuilder builder = new StringBuilder();
			String text;
			String cubeInfo = null;
			while ((text = bd.readLine()) != null) {
				//System.out.println("text:" + text);
				//System.out.println("---------------------------------------------");
				if(text.startsWith("SNB.cubeInfo")){
					cubeInfo = text.substring(15);
					break;
				}

			}

			System.out.println(cubeInfo);
		}catch (Exception e){
			e.printStackTrace();
		}



		long e = System.currentTimeMillis() -s;
		System.out.println("ues Android:" + e);
		return  e;
	}
	public long  testGJSON() throws IOException {
		long s = System.currentTimeMillis();
		HttpURLConnection connection = null;
		try {
			// URL url = new URL("https://xueqiu.com/P/ZH914042");
			URL url = new URL("https://xueqiu.com/P/ZH902949"); // cheng lao shi
			connection = (HttpURLConnection) url.openConnection();

			InputStream in = connection.getInputStream();
			BufferedReader bd = new BufferedReader(new InputStreamReader(in));

			//StringBuilder builder = new StringBuilder();
			String text;
			String cubeInfo = null;
			while ((text = bd.readLine()) != null) {
				if (text.startsWith("SNB.cubeInfo")) {
					cubeInfo = text.substring(15);
					break;
				}
			}
			System.out.println(cubeInfo);
		}catch (Exception e){
			e.printStackTrace();
		}


		long e = System.currentTimeMillis() -s;
		System.out.println("ues Computer:" + e);
		return  e;
	}


}
