import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;

public class Main {
	ObjectMapper objectMapper = new ObjectMapper();
	String userAccount="qgllGMsw22qyyoGamwY9I4s0f3e7FEUnX23bol9EfW2t4pSncr1czGGl/cm/U3s8Ja1cNlVkS+WutaRvTgz9Tg==";
	public static void main(String[] args) throws Exception {
		Main main = new Main();
		String token = main.testLogin();
		main.testPosition(token);
	}
	public void  testPosition(String token) throws IOException {
		//.out.println("token["+token+"]");
		CloseableHttpClient httpclient = HttpClients.custom().setUserAgent("fbadp")
				.build();
		try {
			HttpUriRequest login = RequestBuilder.post()
					.setUri(new URI("https://bdjr.gjzq.com.cn/stocktrade/GetPosition?from=android&clientVersion=3.4.3&vv=3.4.3&deviceId=865372028417613&mac=02:00:00:00:00:00"
					+"&tradeToken="+token))
					.addParameter("cuid", "5BC6A7A5EAAD48ABD766E2D595EF4CCA|316714820273568")
					.addParameter("phone", "13660288080")
					.addParameter("accountType", "1")
					.addParameter("userAccount", userAccount)
					.addParameter("brokerId", "1")
					.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
					.build();
			CloseableHttpResponse response2 = httpclient.execute(login);
			HttpEntity entity = response2.getEntity();
			String result = IOUtils.toString(entity.getContent(), "UTF-8");
			System.out.println(result);
			EntityUtils.consume(entity);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String  testLogin() throws IOException {

		CloseableHttpClient httpclient = HttpClients.custom().setUserAgent("fbadp")
				.build();
		try {
			HttpUriRequest login = RequestBuilder.post()
					.setUri(new URI("https://bdjr.gjzq.com.cn/stocktrade/dologin?from=android&clientVersion=3.4.3&vv=3.4.3&deviceId=865372028417613&brokerId=1&accountType=1&mac=02:00:00:00:00:00"))
					.addParameter("cuid", "5BC6A7A5EAAD48ABD766E2D595EF4CCA|316714820273568")
					.addParameter("phone", "13660288080")
					.addParameter("userPassword", "LUEjKWipgFuFSCjWVQz/nCMZ/DFclk5x/uacLolYmvx4n9MoAVaBRG8qcJB0813hvmJJQyMkBlzQhQKs8OCOKQ==")
					.addParameter("isBind ", "0")
					.addParameter("userAccount", userAccount)
					//.setHeader("Referer", "https://jy.yongjinbao.com.cn/winner_gj/gjzq/")
					.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
					.build();
			CloseableHttpResponse response2 = httpclient.execute(login);
			HttpEntity entity = response2.getEntity();
			String result = IOUtils.toString(entity.getContent(), "UTF-8");
			System.out.println(result);

			// URLDecoder.decode(object.getData().getLoginToken(),"UTF-8").trim();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}




}
