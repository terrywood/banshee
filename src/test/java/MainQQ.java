import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;

public class MainQQ {
	ObjectMapper objectMapper = new ObjectMapper();
	BasicCookieStore cookieStore;
	String userAccount="qgllGMsw22qyyoGamwY9I4s0f3e7FEUnX23bol9EfW2t4pSncr1czGGl/cm/U3s8Ja1cNlVkS+WutaRvTgz9Tg==";
	public static void main(String[] args) throws Exception {

		MainQQ main = new MainQQ();
		BoundResponse boundResponse = main.getBound();

		System.out.println(boundResponse);

		main.getAssetInfo(boundResponse);
		main.getHolding(boundResponse);
		main.getBulletin();
		main.login();
	}

/*	public String  getPubkey() throws IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setUserAgent("Dalvik/2.1.0 (Linux; U; Android 6.0.1; MI 4LTE MIUI/V8.0.2.0.MXDCNDG)")
				.build();
		try {
			HttpUriRequest login = RequestBuilder.post()
					.setUri(new URI("https://deal.finance.qq.com/trade/trade/getPubkey?check=2&_appName=android&_dev=MI+4LTE&_devId=d0a294bc591aa1a84cdba05defaccff4862cc131&_mid=d0a294bc591aa1a84cdba05defaccff4862cc131&_md5mid=0D6F2E073535CC97EC17C0ACB1511AD8&_omgid=5183a88ffcf4ad4280e81564d1cb22a98492001021091d&_omgbizid=246fc921b3b58b44134a5eb9ca24a2c17d18014021091d&_appver=5.1.1&_ifChId=119&_screenW=1080&_screenH=1920&_osVer=6.0.1&_uin=3532619&_wxuin=20000&_net=WIFI&__random_suffix=4468"))
					.addParameter("trdtoken", "rg7GkX1MjJ1S/H7sl+ThKb9k/Xg/xqhbtgpQdEpTRKzmDnRjIXUwU7rZzNiANVG14AgPeI93XVhX+BMkY1D6IRZmwhv72kCYnJwSpxQRZZhW77kecgPXx7+hLkCqV3fZU5xeFJ8RRaBTPZjmWTS4faNb/Fevh4RYUaAO+AVsDQM2u3uI8U99QwTeO09dzhYs")
					.addParameter("uin", "3532619")
					.addParameter("device_id", "0D6F2E073535CC97EC17C0ACB1511AD8")
					.setHeader("Referer", "http://zixuanguapp.finance.qq.com")
					.setHeader("Content-Type","application/x-www-form-urlencoded")
					.setHeader("cookie", "lskey=00030000e90ba5315813a8543c39030aa97f24fe1eeb2435eed5572417b0f83cc3d46b1335eaef573c22e0fc;%20luin=o03532619;")
					.build();
			CloseableHttpResponse response2 = httpclient.execute(login);
			HttpEntity entity = response2.getEntity();
			String result = IOUtils.toString(entity.getContent(), "UTF-8");
			System.out.println(result);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public String bind() throws IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setUserAgent("Dalvik/2.1.0 (Linux; U; Android 6.0.1; MI 4LTE MIUI/V8.0.2.0.MXDCNDG)")
				.build();
		try {
			HttpUriRequest login = RequestBuilder.post()
					.setUri(new URI("https://deal.finance.qq.com/trade/Trade/bind?check=2&_appName=android&_dev=MI+4LTE&_devId=d0a294bc591aa1a84cdba05defaccff4862cc131&_mid=d0a294bc591aa1a84cdba05defaccff4862cc131&_md5mid=0D6F2E073535CC97EC17C0ACB1511AD8&_omgid=5183a88ffcf4ad4280e81564d1cb22a98492001021091d&_omgbizid=246fc921b3b58b44134a5eb9ca24a2c17d18014021091d&_appver=5.1.1&_ifChId=119&_screenW=1080&_screenH=1920&_osVer=6.0.1&_uin=3532619&_wxuin=20000&_net=WIFI&__random_suffix=54764"))
					.addParameter("account", "40128457")
					.addParameter("uin", "3532619")
					.addParameter("login_type", "2")
					.addParameter("qs_id", "15900")
					.addParameter("trdtoken", "rg7GkX1MjJ1S/H7sl+ThKb9k/Xg/xqhbtgpQdEpTRKzmDnRjIXUwU7rZzNiANVG14AgPeI93XVhX+BMkY1D6IRZmwhv72kCYnJwSpxQRZZhW77kecgPXx7+hLkCqV3fZU5xeFJ8RRaBTPZjmWTS4faNb/Fevh4RYUaAO+AVsDQM2u3uI8U99QwTeO09dzhYs")
					.addParameter("device_id", "0D6F2E073535CC97EC17C0ACB1511AD8")
					.addParameter("passwd", "E5u0Po4+Ar9MZTfSwygYJ9T5qcVrKUkHb8/CYVyAQZdByLuYo9bXENvLkGOsU2jecRI1BUeaZtOugLUm6tc3+/39z684By6VuVww+evMPtFrwak5/x+CZRbZ9jcYPLjMOIdnZ9V2hP9bKtN2S4ZUJnXVDs/cmA32+hlhwwdHQgQ=")
					.setHeader("Referer", "http://zixuanguapp.finance.qq.com")
					.setHeader("Content-Type","application/x-www-form-urlencoded")
					.setHeader("cookie", "lskey=00030000e90ba5315813a8543c39030aa97f24fe1eeb2435eed5572417b0f83cc3d46b1335eaef573c22e0fc;%20luin=o03532619;")
					.build();
			CloseableHttpResponse response2 = httpclient.execute(login);
			HttpEntity entity = response2.getEntity();
			String result = IOUtils.toString(entity.getContent(), "UTF-8");
			System.out.println(result);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}*/

	public BoundResponse  getBound() throws IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setUserAgent("Dalvik/2.1.0 (Linux; U; Android 6.0.1; MI 4LTE MIUI/V8.0.2.0.MXDCNDG)")
				.build();
		try {
			HttpUriRequest login =this.getHttpUriRequest("getBound",
					new BasicNameValuePair("currency","0"),
					new BasicNameValuePair("device_id","0D6F2E073535CC97EC17C0ACB1511AD8")
			);
			CloseableHttpResponse response2 = httpclient.execute(login);
			HttpEntity entity = response2.getEntity();
			String result = IOUtils.toString(entity.getContent(), "UTF-8");
			Gson gson = new Gson();
			Result<BoundResponse> response = gson.fromJson(result, new TypeToken<Result<BoundResponse>>() {}.getType());
			return (response.getData());
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public void  getAssetInfo(BoundResponse boundResponse) throws IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setUserAgent("Dalvik/2.1.0 (Linux; U; Android 6.0.1; MI 4LTE MIUI/V8.0.2.0.MXDCNDG)")
				.build();
		try {
			HttpUriRequest login =this.getHttpUriRequest("getAssetInfo",
					new BasicNameValuePair("currency","0"),
					new BasicNameValuePair("trdtoken",boundResponse.getTrdtoken()),
					new BasicNameValuePair("device_id","0D6F2E073535CC97EC17C0ACB1511AD8")
			);
			CloseableHttpResponse response2 = httpclient.execute(login);
			HttpEntity entity = response2.getEntity();
			String result = IOUtils.toString(entity.getContent(), "UTF-8");
			System.out.println("------------------------------");
			System.out.println(result);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void  getHolding(BoundResponse boundResponse) throws IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setUserAgent("Dalvik/2.1.0 (Linux; U; Android 6.0.1; MI 4LTE MIUI/V8.0.2.0.MXDCNDG)")
				.build();
		try {
			HttpUriRequest login =this.getHttpUriRequest("getHolding",
					new BasicNameValuePair("currency","0"),
					new BasicNameValuePair("trdtoken",boundResponse.getTrdtoken()),
					new BasicNameValuePair("device_id","0D6F2E073535CC97EC17C0ACB1511AD8")
			);
			CloseableHttpResponse response2 = httpclient.execute(login);
			HttpEntity entity = response2.getEntity();
			String result = IOUtils.toString(entity.getContent(), "UTF-8");
			System.out.println("------------------------------");
			System.out.println(result);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void  login() throws IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setUserAgent("Dalvik/2.1.0 (Linux; U; Android 6.0.1; MI 4LTE MIUI/V8.0.2.0.MXDCNDG)")
				.build();
		try {
			HttpUriRequest login =this.getHttpUriRequest("login",
					new BasicNameValuePair("login_type","1"),
					new BasicNameValuePair("expire","3600"),
					new BasicNameValuePair("passwd","Mu0NfPRrCW5360G6NA9dhPhBvyF21afjeWWvCjaMDEXOpaEYY+qtS6x+WDgN5VciZvNZkUkS9LrXpNeQwcncrIXozxOQUEaTGoFdVofc6KpiPFnOGEr1j4mGGt7hhQ5+KZHRwffHlse02FoCSeU7mfk5zkgiLi9mk9hm393mMjQ="),
					new BasicNameValuePair("device_id","0D6F2E073535CC97EC17C0ACB1511AD8")
			);
			CloseableHttpResponse response2 = httpclient.execute(login);
			HttpEntity entity = response2.getEntity();
			String result = IOUtils.toString(entity.getContent(), "UTF-8");
			System.out.println("------------------------------");
			System.out.println(result);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void  trade(BoundResponse boundResponse) throws IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setUserAgent("Dalvik/2.1.0 (Linux; U; Android 6.0.1; MI 4LTE MIUI/V8.0.2.0.MXDCNDG)")
				.build();
		try {
			HttpUriRequest login =this.getHttpUriRequest("trade",
					new BasicNameValuePair("currency","currency"),
					new BasicNameValuePair("trdtoken",boundResponse.getTrdtoken()),
					new BasicNameValuePair("device_id","0D6F2E073535CC97EC17C0ACB1511AD8")
			);
			CloseableHttpResponse response2 = httpclient.execute(login);
			HttpEntity entity = response2.getEntity();
			String result = IOUtils.toString(entity.getContent(), "UTF-8");
			System.out.println("------------------------------");
			System.out.println(result);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void  getBulletin() throws IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setUserAgent("Dalvik/2.1.0 (Linux; U; Android 6.0.1; MI 4LTE MIUI/V8.0.2.0.MXDCNDG)")
				.build();
		try {
			HttpUriRequest login =this.getHttpUriRequest("getBulletin",new BasicNameValuePair("bulletin_id",""));
			CloseableHttpResponse response2 = httpclient.execute(login);
			HttpEntity entity = response2.getEntity();
			String result = IOUtils.toString(entity.getContent(), "UTF-8");
			System.out.println("------------------------------");
			System.out.println(result);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/***
	 *
	 * @param action  getBulletin| getHolding
	 * @param nvps
	 * @return
	 */
	public HttpUriRequest getHttpUriRequest(String action ,NameValuePair... nvps){
		String number = RandomStringUtils.randomNumeric(5);
		try {
			HttpUriRequest login = RequestBuilder.post()
					.setUri(new URI("https://deal.finance.qq.com/trade/Trade/"+action+"?check=2&_appName=android&_dev=MI+4LTE&_devId=d0a294bc591aa1a84cdba05defaccff4862cc131&_mid=d0a294bc591aa1a84cdba05defaccff4862cc131&_md5mid=0D6F2E073535CC97EC17C0ACB1511AD8&_omgid=5183a88ffcf4ad4280e81564d1cb22a98492001021091d&_omgbizid=246fc921b3b58b44134a5eb9ca24a2c17d18014021091d&_appver=5.1.1&_ifChId=119&_screenW=1080&_screenH=1920&_osVer=6.0.1&_uin=3532619&_wxuin=20000&_net=WIFI&__random_suffix="+number))
					.addParameter("uin", "3532619")
					.addParameter("qs_id", "15900")
					.addParameters(nvps)
					.setHeader("Referer", "http://zixuanguapp.finance.qq.com")
					.setHeader("Content-Type","application/x-www-form-urlencoded")
					.setHeader("cookie", "lskey=00030000e90ba5315813a8543c39030aa97f24fe1eeb2435eed5572417b0f83cc3d46b1335eaef573c22e0fc;%20luin=o03532619;")
					.build();
			return login;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}






}
