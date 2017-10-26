package com.pe.vietanhs0817.pecrudapp.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vietanhs0817 on 10/26/2017.
 */

public class UserModel {

    private static String BASE_URL = "http://dumbservice.azurewebsites.net/api/user/";

    private RestTemplate template = new RestTemplate();


    public Response createUser(User user){
        Response response = new Response();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("Email", user.getEmail());
        map.add("Password",user.getPassword());
        map.add("FullName",user.getFullName());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        Response res = template.postForEntity(BASE_URL, request , Response.class ).getBody();

        response.setStatus(res.isStatus());
        response.setMessage(res.getMessage());

        return response;
    }

    public Response updateUser(User user){
        Response response = new Response();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("Email", user.getEmail());
        map.add("Password",user.getPassword());
        map.add("FullName",user.getFullName());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        Response res =  template.exchange(BASE_URL, HttpMethod.PUT, request, Response.class,map).getBody();

        response.setStatus(res.isStatus());
        response.setMessage(res.getMessage());

        return response;
    }


    public Response deleteUser(String email){
        Response response = new Response();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        Response res =  template.exchange(BASE_URL+"?email="+email, HttpMethod.DELETE, request, Response.class,map).getBody();

        response.setStatus(res.isStatus());
        response.setMessage(res.getMessage());

        return response;
    }

    public User findUser(String email){
        User user = null;
        try {
            Response response = template.getForObject(BASE_URL+"getuser?email="+email, Response.class);
            if (response.isStatus()) {
                String json = parseString(response.getObject());
                ObjectMapper mapper = new ObjectMapper();
                user = mapper.readValue(json, new TypeReference<User>(){});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        try {
            Response response = template.getForObject(BASE_URL, Response.class);
            System.out.println(response.getMessage());
            if (response.isStatus()) {
                String json = "["+parseString(response.getObject())+"]";
                ObjectMapper mapper = new ObjectMapper();
                userList = mapper.readValue(json, new TypeReference<List<User>>(){});

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userList;
    }

    private String parseString(Object object){
        String result = null;
        if(object != null && !"null".equals(object.toString()) && !"[]".equals(object.toString())){
            String temp = object.toString().replaceAll("=",":");
            Matcher m = Pattern.compile("\\{(.*?)\\}").matcher(temp);
            StringBuilder builder = new StringBuilder();
            List<HashMap<String,String>> list = new ArrayList<>();
            while(m.find()){
                HashMap<String,String> map = new HashMap<>();
                String sub = m.group();
                sub = sub.substring(1,sub.length()-1);
                String[] strs = sub.split(", ");

                for(String str: strs){
                    String key = str.substring(0,str.indexOf(':'));
                    String value=str.substring(str.indexOf(':')+1);
                    map.put(key,value);
                }
                list.add(map);
            }
            for(HashMap<String,String> elements : list){
                StringBuilder sb = new StringBuilder("{");
                for(Map.Entry<String,String> entry: elements.entrySet()){
                    StringBuilder builderStr = new StringBuilder("\"");
                    builderStr.append(entry.getKey()).append("\"").append(":").append("\"").append(entry.getValue()).append("\",");
                    sb.append(builderStr);
                }
                String t = sb.substring(0,sb.length()-1);
                sb = new StringBuilder(t);
                sb.append("},");
                builder.append(sb);
            }

            builder.replace(builder.length()-1,builder.length()-1,"");
            result = builder.toString().substring(0,builder.length()-1);
        }
        return result;
    }
}
