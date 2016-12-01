/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.berlin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author apple
 */
public class HttpClient {
    
    public static String get(String link) throws IOException{
        HttpURLConnection conn= (HttpURLConnection)new URL(link).openConnection();
        BufferedReader reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer buffer= new StringBuffer();
        String line="";
        while((line=reader.readLine())!=null){
            buffer.append(line);
        }
        reader.close();
        return buffer.toString();
    }
    
}
