import java.net.http.*;
import java.net.URI;
import java.io.IOException;
import org.json.JSONObject;

class WeatherApp {
    private static final String API_KEY = "b4338d4b0f4d990116a348d4ee505c2f"; // Your API Key
    private static final String CITY = "Tamilnadu";
    public static void main(String[] args) {
        fetchWeatherData(CITY);
    }
    public static void fetchWeatherData(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            parseAndDisplayWeather(response.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error fetching weather data: " + e.getMessage());
        }
    }
    public static void parseAndDisplayWeather(String responseBody) {
        JSONObject json = new JSONObject(responseBody);
        String cityName = json.getString("name");
        JSONObject main = json.getJSONObject("main");
        double temperature = main.getDouble("temp");
        double feelsLike = main.getDouble("feels_like");
        int humidity = main.getInt("humidity");
        String weatherDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");

        System.out.println("   Weather Data for " + cityName);
        System.out.println("====================================");
        System.out.println("Temperature    : " + temperature + "°C");
        System.out.println("Feels Like     : " + feelsLike + "°C");
        System.out.println("Humidity       : " + humidity + "%");
        System.out.println("Condition      : " + weatherDescription);
        System.out.println("====================================");
    }
}

