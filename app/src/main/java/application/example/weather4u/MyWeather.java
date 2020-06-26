package application.example.weather4u;

public class MyWeather {

    String main, humidity;
    double temp, lat, lon, windSpeed;

    public MyWeather(String main, double temp, double lat, double lon, double windSpeed, String humidity) {
        this.main = main;
        this.humidity = humidity;
        this.temp = temp;
        this.lat = lat;
        this.lon = lon;
        this.windSpeed = windSpeed;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getHumidity() {
        return humidity;
    }

    public void sethumidity(String humidity) {
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
