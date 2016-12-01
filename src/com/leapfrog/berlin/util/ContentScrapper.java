/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.berlin.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author apple
 */
public class ContentScrapper extends Thread {

    private String link;

    public ContentScrapper(String link) {
        this.link = link;
    }

    @Override
    public void run() {
        try {
            String content = HttpClient.get(link);
            
            //System.out.println(content);
            String regex = "<div class=\"col-xs-12 col-sm-8 col-md-9\">\\s+<h2>(.*?)</h2>(.*?)<br />(.*?)<br />(.*?)<br />";

            Matcher matcher = RegexMatcher.match(regex, content);
            
            while(matcher.find()) {

                System.out.println("Name: " + matcher.group(1).trim());
                System.out.println("Address : " + matcher.group(2).trim());
                String city = (matcher.group(3));
                String tokens[] = city.split(" ");
                System.out.println("Postcode:" + tokens[1]);
                System.out.println("City :" + tokens[2]);
                  //               System.out.println(city);


                System.out.println("Country :" + matcher.group(4).trim());

                System.out.println("=================================");
               
               
            }
         
        } catch (IOException ioe) {

        }
    }

}
