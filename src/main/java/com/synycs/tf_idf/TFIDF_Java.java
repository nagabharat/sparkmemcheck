package com.synycs.tf_idf;

import java.util.Arrays;
import java.util.List;

/**
 * Created by synycs on 26/12/16.
 */
public class TFIDF_Java {
    public static double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result;
    }
    public static double idf(List<List<String>> docs, String term) {
        double n = 0;
        for (List<String> doc : docs) {
            boolean already=false;
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log((docs.size()+1) / (n+1));
    }
    public static long numTimes(List<List<String>> docs, String term){
        long n=0;
        for (List<String> doc : docs) {
            boolean already=false;
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return n;
    }
    public static double tfIdf(List<String> doc, List<List<String>> docs, String term) {
        return tf(doc, term) * idf(docs, term);
    }

    public static void main(String [] args){
        List<String> doc1 = Arrays.asList("Hi I heard about Spark".split(" "));
        List<String> doc2 = Arrays.asList("I wish i could use scala and i am good at java".split(" "));
        List<String> doc3 = Arrays.asList("i Logistic regression models are neat".split(" "));
        List<List<String>> documents = Arrays.asList(doc1, doc2, doc3);
        System.out.println(idf(documents,"hi"));

    }

}
