package client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class ApacheHttpUserClientGet {

    public static void main(String... args) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://localhost:8080/jax-rs-restful/api/users/all");
        request.addHeader("accept", "application/json");

        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Http connection failed, code: " + response.getStatusLine().getStatusCode());
        }

        final BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        Gson gson = new Gson();
        final Type type = new TypeToken<List<User>>() {}.getType();
        List<User> users = gson.fromJson(reader, type);

        users.stream().forEach(u -> System.out.println(u));

        httpClient.getConnectionManager().shutdown();
    }

}
