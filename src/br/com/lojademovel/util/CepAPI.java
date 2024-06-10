package br.com.lojademovel.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CepAPI {
    public static JSONObject buscarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            conn.disconnect();

            return new JSONObject(content.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
