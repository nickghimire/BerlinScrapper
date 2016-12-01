/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.berlin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Program {

    private static final String base_url = "http://berlin2016.cwieme-media.com/catalog/index.cfm?fuseaction=Catalog.showCompanyAzAll&language=en&Letter=&CountryID=&ExhibitionInfo=&Page=";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        try {
            String maindata = "";
            int count = 1;
            int checkparam = 0;
            FileWriter writer = new FileWriter("/users/apple/desktop/shanghaidata.csv");
            for (int i = 1; i <= 9 ; i++) {

                String main_url = base_url + i;
                Document docs = Jsoup.connect(main_url).timeout(0).get();
                Elements allelements = docs.select("div.col-md-12 div.row a");

                for (Element e : allelements) {

                    String innerlink = e.attr("href");
                    Document innerdoc = Jsoup.connect(innerlink).timeout(0).get();
                    //    System.out.println(innerdoc.toString());
                    Elements innerelements = innerdoc.select("div.row div.col-xs-12");
                    for (Element e1 : innerelements) {

                        StringBuilder builder = new StringBuilder();

                        String content = e1.html();
                        Scanner scanner = new Scanner(content);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            builder.append(line);
                        }

                        String regex = "<h2>(.*?)</h2>(.*?)<br>(.*?)<br>(.*?)<br>(.*?)Phone:(.*?)<br>(.*?)Stand:(.*?)<br>";

                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(builder.toString());
                        //  System.out.println(builder);
                        while (matcher.find()) {
                            String company = matcher.group(1).trim().replaceAll(",", "").replaceAll("<br>", " ");
                            System.out.println(company);

                            String address = matcher.group(2).trim().replaceAll(",", "");

                            String state = matcher.group(3).trim();
                            String dataState = state.toString();

                            //  maindata = company + "," + address + "," + state + "," + country + "," + phone; 
                            maindata = company + "," + address;

                            if (dataState.contains(" ")) {
                                String tokens[] = dataState.split(" ");
                                String PostCode = tokens[0];
                                String City = tokens[1];
                                maindata = maindata + "," + PostCode + "," + City;

                            } else {
                                maindata = maindata + "," + dataState;
                            }

                            String country = matcher.group(4).trim().replaceAll(",", "");
                            String phone = matcher.group(6).trim().replaceAll(",", "");

                            maindata = maindata + "," + country + "," + phone;

                            String remains = matcher.group(7);

                            String emailregex = "E-Mail(.*?)>(.*?)</a>";
                            Pattern pattern2 = Pattern.compile(emailregex);
                            Matcher matcher2 = pattern2.matcher(remains);
                            if (matcher2.find()) {
                                String email = matcher2.group(2).trim().replaceAll(",", "");
                                maindata = maindata + "," + email;
                            } else {
                                String empty = " ";
                                maindata = maindata + "," + empty;
                            }

                            String webregex = "Web address:(.*?)>(.*?)</a>";
                            Pattern pattern3 = Pattern.compile(webregex);
                            Matcher matcher3 = pattern3.matcher(remains);
                            if (matcher3.find()) {
                                String webadd = matcher3.group(2).replaceAll(",", "");

                                maindata = maindata + "," + webadd;
                            } else {
                                String empty = " ";
                                maindata = maindata + "," + empty;
                            }

                            //        
                        }

                    }
                    StringBuilder builder = new StringBuilder();
                    Scanner scanner = new Scanner(innerdoc.toString());
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        builder.append(line);
                    }

                    // System.out.println(builder.toString());
                    // System.out.println("==================================");
                    String regex = "<hr>(.*?)Company Profile(.*?)<div class=\"row\">(.*?)</div>";

                    Pattern patrn = Pattern.compile(regex);
                    Matcher match = patrn.matcher(builder.toString());

                    if (match.find()) {

                        String profile = match.group(3).trim();
                        String prf = Jsoup.parse(profile).text().replaceAll(",", "");

                        //  String prfl = "profile->" + prf;
                        maindata = maindata + "," + prf;
                        //  checkparam = checkparam + 1;

                    } else {
                        String empty = " ";
                        maindata = maindata + "," + empty;
                    }

                    String productregex = "<hr>(.*?)Products\\s*</div>(.*?)<div class=\"col-xs-9(.*?)>\\s*<h3>(.*?)</h3>(.*?)</div>";
                    Pattern patrn2 = Pattern.compile(productregex);
                    Matcher match2 = patrn2.matcher(builder.toString());
                    if (match2.find()) {
                        String product = match2.group(4).trim() + match2.group(5).trim();
                        String prod = Jsoup.parse(product).text().replaceAll(",", "");
                        //String prd = "product->" + prod;
                        maindata = maindata + "," + prod;
                        // checkparam = checkparam + 1;
                    } else {
                        String empty = " ";
                        maindata = maindata + "," + empty;
                    }

                    String indexregex = "<div class=\"row\">\\s*<div class=\"col-sm-12(.*?)\">\\s*Product index\\s*</div>\\s*</div>\\s*<div class=\"row\">(.*?)<hr>";

                    Pattern patrn3 = Pattern.compile(indexregex);
                    Matcher match3 = patrn3.matcher(builder.toString());
                    if (match3.find()) {
                        String index = match3.group(2).trim();
                        String prodindex = Jsoup.parse(index).text().replaceAll(",", "");

                        // String indx = "Product index->" + prodindex;
                        maindata = maindata + "," + prodindex;
                        // checkparam = checkparam + 1;

                    } else {
                        String empty = " ";
                        maindata = maindata + "," + empty;
                    }

                    String keywordregex = "<hr>(.*?)Keyword\\s*</div>\\s*</div>\\s*<div class=\"row\">(.*?)</div>";

                    Pattern patrn4 = Pattern.compile(keywordregex);
                    Matcher match4 = patrn4.matcher(builder.toString());
                    if (match4.find()) {
                        String keyword = match4.group(2).trim();
                        String key = Jsoup.parse(keyword).text();
                        // String key = "Keyword-> " + keywrd;

                        maindata = maindata + "," + key + "\n";
                        //checkparam = checkparam + 1;
                    } else {
                        String empty = " ";
                        maindata = maindata + "\n";
                    }

                    /* if (checkparam == 0) {
                       maindata = maindata + ",";
                    }
                     */
                    // System.out.println(maindata);
                    writer.write(maindata);
                    System.out.println(count + "-data written");
                    count++;
                   

                }
                 System.out.println(i);
            }
            writer.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
