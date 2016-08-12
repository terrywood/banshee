

import app.bean.XueReturnJson;
import app.bean.XueSellRebalancing;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class Main {
	public static void main(String[] args) throws ParseException, IOException {


		String cubeInfo = FileUtils.readFileToString(new File("D:\\Terry\\workspace\\banshee\\src\\test\\data.json"),"UTF-8");
		Main main = new Main();
		long json =0;
		long json2 =0;

		for(int i = 0;i<50;i++){
			json+= main.testObject(cubeInfo);
			//json2+= main.testGJSON(cubeInfo);
			System.out.println("------------------------");
		}
         System.out.println("--------------------summary-------------------");
		System.out.println(json);
		System.out.println(json2);
	}

	ObjectMapper objectMapper = new ObjectMapper();
	public long  testObject(String cubeInfo) throws IOException {
		long s = System.currentTimeMillis();
		XueReturnJson xueReturnJson = objectMapper.readValue(cubeInfo, XueReturnJson.class);
		XueSellRebalancing xueSellRebalancing = xueReturnJson.getSellRebalancing();
		System.out.println(xueSellRebalancing);
		long e = System.currentTimeMillis() -s;
		System.out.println("ues time:" + e);
		return  e;
	}
	public long  testGJSON(String cubeInfo) throws IOException {
		long s = System.currentTimeMillis();
		Gson gson = new Gson();
		XueReturnJson xueReturnJson = gson.fromJson(cubeInfo, XueReturnJson.class);
		XueSellRebalancing xueSellRebalancing = xueReturnJson.getSellRebalancing();
/*		Map xueReturnJson = gson.fromJson(cubeInfo, Map.class);
		Map xueSellRebalancing = (Map)xueReturnJson.get("sell_rebalancing");*/
		System.out.println(xueReturnJson);
		long e = System.currentTimeMillis() -s;
		System.out.println("ues time:" + e);
		return  e;
	}


}
