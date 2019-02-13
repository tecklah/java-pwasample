package com.PwaSample;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * <pre>
 * % java --version
 * % java 11.0.1 2018-10-16 LTS
 *
 * % javac WeatherYdnJava.java && java -ea WeatherYdnJava
 * </pre>
 *
 */
public class WeatherYdnJava {
	
    public static void main(String[] args) throws Exception {

        final String appId = "vCpY2T44";
        final String consumerKey = "dj0yJmk9TmRBQWlOWTFoVmFkJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTYy";
        final String consumerSecret = "8f4de73c5b5075317e91ecf93a37d3eeabc6dda8";
        final String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";

        long timestamp = new Date().getTime() / 1000;
        byte[] nonce = new byte[32];
        Random rand = new Random();
        rand.nextBytes(nonce);
        String oauthNonce = new String(nonce).replaceAll("\\W", "");

        List<String> parameters = new ArrayList<>();
        parameters.add("oauth_consumer_key=" + consumerKey);
        parameters.add("oauth_nonce=" + oauthNonce);
        parameters.add("oauth_signature_method=HMAC-SHA1");
        parameters.add("oauth_timestamp=" + timestamp);
        parameters.add("oauth_version=1.0");
        // Make sure value is encoded
        parameters.add("location=" + URLEncoder.encode("sunnyvale,ca", "UTF-8"));
        parameters.add("format=json");
        Collections.sort(parameters);

        StringBuffer parametersList = new StringBuffer();
        for (int i = 0; i < parameters.size(); i++) {
            parametersList.append(((i > 0) ? "&" : "") + parameters.get(i));
        }

        String signatureString = "GET&" +
            URLEncoder.encode(url, "UTF-8") + "&" +
            URLEncoder.encode(parametersList.toString(), "UTF-8");

        String signature = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec((consumerSecret + "&").getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
            Encoder encoder = Base64.getEncoder();
            signature = encoder.encodeToString(rawHMAC);
        } catch (Exception e) {
            System.err.println("Unable to append signature");
            System.exit(0);
        }

        String authorizationLine = "OAuth " +
            "oauth_consumer_key=\"" + consumerKey + "\", " +
            "oauth_nonce=\"" + oauthNonce + "\", " +
            "oauth_timestamp=\"" + timestamp + "\", " +
            "oauth_signature_method=\"HMAC-SHA1\", " +
            "oauth_signature=\"" + signature + "\", " +
            "oauth_version=\"1.0\"";

        HttpClient client = HttpClient.newHttpClient();
        String urltest = url + "?location=sunnyvale,ca&format=json";
        System.out.println(urltest);
        System.out.println(authorizationLine);
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url + "?location=sunnyvale,ca&format=json"))
            .header("Authorization", authorizationLine)
            .header("Yahoo-App-Id", appId)
            .header("Content-Type", "application/json")
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        System.out.println(response.body());
    }
}