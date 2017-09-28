package com.minsoura.findit;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by min on 2015-12-27.
 */
public class requestHandler {


    public String bringUserProfile(String requestURL, HashMap<String, String> postDataParams){
        URL url;

        StringBuilder jsonHtml = new StringBuilder();
        try{
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(50000);
            conn.setConnectTimeout(50000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            bufferedWriter.write(getPostDataString(postDataParams));

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            if(responseCode == HttpsURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String result;
                while((result = bufferedReader.readLine())!=null){
                    jsonHtml.append(result +"\n");
                }

            }else{


                return conn.getResponseMessage();
            }
        }catch (Exception e){

            e.printStackTrace();
        }
        return jsonHtml.toString();
    }

    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams){
        URL url;
        String response ="1";
        try{
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(50000);
            conn.setConnectTimeout(50000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            bufferedWriter.write(getPostDataString(postDataParams));


            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            int responseCode = conn.getResponseCode();



            if(responseCode == HttpsURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                response = bufferedReader.readLine();



            }else{

               response = conn.getResponseMessage();
                return response;
            }


        }catch(Exception e){


            e.printStackTrace();
        }

            return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));

            }


        return result.toString();

    }





}
