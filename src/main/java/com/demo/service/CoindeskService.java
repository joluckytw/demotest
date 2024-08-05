package com.demo.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class CoindeskService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String COINDESK_API_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
    public String  getCoindeskData() {

        String response = restTemplate.getForObject(COINDESK_API_URL, String.class);
        JSONObject jsonObject = new JSONObject(new JSONTokener(response));

        String isoDateTimeStr = jsonObject.getJSONObject("time").getString("updatedISO");
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(isoDateTimeStr, isoFormatter);
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formattedDateTimeStr = zonedDateTime.format(targetFormatter);
        ZonedDateTime taipeiZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Taipei"));
        String taipeiformattedDateTimeStr = taipeiZonedDateTime.format(targetFormatter);

        JSONObject bpi = jsonObject.getJSONObject("bpi");
        JSONObject updatedBpi = new JSONObject();

        for (String key : bpi.keySet()) {
            JSONObject currency = bpi.getJSONObject(key);
            JSONObject updatedCurrency = new JSONObject();
            updatedCurrency.put("code", currency.getString("code"));
            updatedCurrency.put("name", getCurrencyCodeName(key)); // 添加 codename 字段
            updatedCurrency.put("rate", currency.getString("rate"));
            updatedBpi.put(key, updatedCurrency);
        }
  
     // 构造结果对象
        JSONObject result = new JSONObject();
        result.put("updatedTaipeiTime", taipeiformattedDateTimeStr);
        result.put("bpi", updatedBpi);

        // 返回格式化后的 JSON 字符串
        return result.toString(4); // 缩进 4 个空格
        
    }

    private String getCurrencyCodeName(String code) {
        // 根据币种代码返回对应的中文名称
        switch (code) {
            case "USD":
                return "美元";
            case "GBP":
                return "英鎊";
            case "EUR":
                return "歐元";
            default:
                return "未知幣別";
        }
    }
    
    public String getRawCoindeskJson() {
        return restTemplate.getForObject(COINDESK_API_URL, String.class);
    }

    public String getFormattedCoindeskJson() {
        String jsonResponse = getRawCoindeskJson();
        JSONObject jsonObject = new JSONObject(jsonResponse);
        return jsonObject.toString(4); // 缩进 4 个空格
    }
    
    
}

