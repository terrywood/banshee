import cn.skypark.code.MyCheckCodeTool;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

public class Main {
	ObjectMapper objectMapper = new ObjectMapper();
	public static void main(String[] args) throws ParseException, IOException {


		//String cubeInfo = FileUtils.readFileToString(new File("D:\\Terry\\workspace\\banshee\\src\\test\\data.json"),"UTF-8");
		Main main = new Main();
		//main.testGJSON();
		main.testyonjinbao();

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


	public void  testyonjinbao() throws IOException {
		CloseableHttpClient httpclient = HttpClients.custom()
				.build();
		try {
			HttpGet httpget3 = new HttpGet("https://jy.yongjinbao.com.cn/winner_gj/gjzq/user/extraCode.jsp");
			CloseableHttpResponse response3 = httpclient.execute(httpget3);
			HttpEntity entity3 = response3.getEntity();
			BufferedImage image = ImageIO.read(entity3.getContent());
			EntityUtils.consume(entity3);
			MyCheckCodeTool tool = new MyCheckCodeTool("guojin");
			String code = tool.getCheckCode_from_image(image);
			System.out.println(code);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public long  testObject() throws IOException {
		long s = System.currentTimeMillis();

		HttpURLConnection connection = null;
		try {
			// URL url = new URL("https://xueqiu.com/P/ZH914042");
			URL url = new URL("https://xueqiu.com/P/ZH902949"); // cheng lao shi
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-agent","Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
			InputStream in = connection.getInputStream();
			BufferedReader bd = new BufferedReader(new InputStreamReader(in));
			String text;
			String cubeInfo = null;


			while ((text = bd.readLine()) != null) {
/*				System.out.println("text:" + text);
				System.out.println("---------------------------------------------");*/

				if(text.indexOf("902851")!=-1){
					//cubeInfo = text.substring(15);
					System.out.println(text);

				}

			}

			//System.out.println(cubeInfo);
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
				System.out.println("text:" + text);
				System.out.println("---------------------------------------------");

			/*	if (text.startsWith("SNB.cubeInfo")) {
					cubeInfo = text.substring(15);
					break;
				}*/
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
