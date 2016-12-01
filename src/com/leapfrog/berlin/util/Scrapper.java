/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.berlin.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author apple
 */
public class Scrapper extends Thread {
    private String link,param,fileName; 

    public Scrapper() {
    }

    public Scrapper(String link, String param) {
        this.link = link;
        this.param = param;
     
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
    
    @Override
    public void run() {
        try{
            String content= HttpClient.get(link);
                System.out.println("Scrapping" + link);
               // System.out.println(content);
              /* FileWriter writer= new FileWriter(fileName);
               writer.write(content);
               writer.close();
                System.out.println("====================================================");
                */
              
              String regEx="<a class=\"bold\" href=\"(.*?)\">(.*?)</a>";
              
              Matcher matcher= RegexMatcher.match(regEx, content); 
            
              while(matcher.find()){
                 ContentScrapper sc= new ContentScrapper(matcher.group(1));
                 sc.run();
                 
              
              }
            
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
        
    }
    
}
