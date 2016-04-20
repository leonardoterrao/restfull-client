package client;

import com.google.gson.Gson;
import model.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class APacheHttpUserClientPost {

    public static void main(String... args) throws Exception {
        Gson gson = new Gson();

        Random random = new Random();
        final long randomLong = random.nextInt(1000);
        final String userJson = gson.toJson(new User(randomLong, "Bodi" + randomLong));

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost("http://localhost:8080/jax-rs-restful/api/users/post");

        StringEntity input = new StringEntity(userJson);
        input.setContentType("application/json");
        postRequest.setEntity(input);

        HttpResponse response = httpClient.execute(postRequest);

        if (response.getStatusLine().getStatusCode() != 201) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }

        httpClient.getConnectionManager().shutdown();

    }

}
