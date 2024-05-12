package lib;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import db.WifiData;
import db.WifiDataService;

//import org.json.simple.parser.ParseException;
import java.text.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.sql.*;
//import java.util.*;


public class GetWifi {

    static final String KEY = "인증키 입력";

    public static int GetWifiSave(double inputLat, double inputLnt) throws org.json.simple.parser.ParseException {

        URL url = null;
        HttpURLConnection con= null;
        JSONObject result = null;
        StringBuilder sb = new StringBuilder();
        int start = 1;
        Integer end = 1000;
        String baseUrl = "http://openapi.seoul.go.kr:8088/" + KEY + "/" +
                "json/TbPublicWifiInfo/"+ start + "/"+end+"/";

        try {
            url = new URL(baseUrl);
            con = (HttpURLConnection) url.openConnection(); // 커넥션 오픈

            con.setRequestMethod("GET"); // 응답받을 데이터가 있는 경우 true
            con.setRequestProperty("Content-type", "application/json");
            con.setDoOutput(true); // 요청시 데이터를 보내야 하는 경우 true

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            while(br.ready()) {
                sb.append(br.readLine());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            if(con != null) con.disconnect();
        }

        result = (JSONObject) new JSONParser().parse(sb.toString());

        //StringBuilder out = new StringBuilder();

        JSONObject data = (JSONObject) result.get("TbPublicWifiInfo");
        
        // 여기서 저장     
        JSONArray array = (JSONArray) data.get("row");
        JSONObject tmp;
        WifiDataService wifiService = new WifiDataService();
        
        for(int i = 0; i<array.size(); i++) {
        	
        	WifiData wifiData = new WifiData();
      	
        	tmp = (JSONObject) array.get(i);
        	
			wifiData.setMgrNo(tmp.get("X_SWIFI_MGR_NO").toString());
			
			// 거리 구하기
			double yd = Math.pow((Double.parseDouble(tmp.get("LAT").toString()) - inputLat),2);
			double xd = Math.pow((Double.parseDouble((String) tmp.get("LNT")) - inputLnt),2);
			double d = Math.sqrt(yd+xd);
			wifiData.setDistance(d);
			
			wifiData.setWrdofc(tmp.get("X_SWIFI_WRDOFC").toString());
			wifiData.setMainNm(tmp.get("X_SWIFI_MAIN_NM").toString());
			wifiData.setAdres1(tmp.get("X_SWIFI_ADRES1").toString());
			wifiData.setAdres2(tmp.get("X_SWIFI_ADRES2").toString());
			wifiData.setInstlFloor(tmp.get("X_SWIFI_INSTL_FLOOR").toString());
			wifiData.setInstlTy(tmp.get("X_SWIFI_INSTL_TY").toString());
			wifiData.setInstlMby(tmp.get("X_SWIFI_INSTL_MBY").toString());
			wifiData.setSvcSe(tmp.get("X_SWIFI_SVC_SE").toString());
			wifiData.setCmcwr(tmp.get("X_SWIFI_CMCWR").toString());
			wifiData.setCnstcYear(Integer.parseInt((String) tmp.get("X_SWIFI_CNSTC_YEAR")));
			wifiData.setInoutDoor(tmp.get("X_SWIFI_INOUT_DOOR").toString());
			wifiData.setRemars3(tmp.get("X_SWIFI_REMARS3").toString());
			wifiData.setLat(Double.parseDouble((String) tmp.get("LAT")));
			wifiData.setLnt(Double.parseDouble((String) tmp.get("LNT")));
			wifiData.setWorkDttm(tmp.get("WORK_DTTM").toString());
			
        	
			wifiService.wifiInsert(wifiData);

        }
        
        int totalGet = Integer.parseInt( data.get("list_total_count").toString());

        return totalGet;
    }

}