/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.berlin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author apple
 */
public class RegexMatcher {

    public static Matcher match(String regex, String content) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(content);
    }
}
 