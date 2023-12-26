package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.Utils.Util;
import com.es.iesmz.transita3.Transita.payload.response.OpenRouteResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.model.LatLng;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
@Service
public class OpenRouteServiceImpl implements OpenRouteService{

    @Override
    public List<List<Double>> getCoordinatesForDirections(String start, String end) throws IOException, InterruptedException, URISyntaxException {
        LatLng startPoint = coordenadasLatLng(start, end).get(0);
        LatLng endPoint = coordenadasLatLng(start, end).get(1);

        System.out.println(Util.ORSToken);
        System.out.println(startPoint);
        System.out.println(endPoint);
        String apiUrl = "https://api.openrouteservice.org/v2/directions/wheelchair?api_key=" + Util.ORSToken + "&start=" + startPoint + "&end=" + endPoint;
        //String apiUrl = "https://api.openrouteservice.org/v2/directions/wheelchair?api_key=5b3ce3597851110001cf624877b29e7b706641248e293b507b995592&start=8.681495,49.41461&end=8.687872,49.420318";

        URI uri = new URI(apiUrl);

        // Create an instance of HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create an HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();


        // Send the HTTP request and retrieve the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Process the response and convert it to a list of LatLng
        List<List<Double>> coordinates = processResponse(response.body());

        return coordinates;
    }

    // Method to process the response and convert it to a list of LatLng
    private List<List<Double>> processResponse(String jsonResponse) {
        Gson gson = new Gson();
        Type responseType = new TypeToken<OpenRouteResponse>(){}.getType();
        OpenRouteResponse routeResponse = gson.fromJson(jsonResponse, responseType);

        // Extract the coordinates from the geometry object
        List<List<Double>> coordinates = routeResponse.features.get(0).geometry.coordinates;

        return coordinates;
    }


    public List<LatLng> coordenadasLatLng(String start, String end) {
        // Separar las coordenadas de inicio y fin
        String[] startCoords = start.split(",");
        String[] endCoords = end.split(",");

        // Crear objetos LatLng con las coordenadas obtenidas
        LatLng startLatLng = new LatLng(Double.parseDouble(startCoords[0]), Double.parseDouble(startCoords[1]));
        LatLng endLatLng = new LatLng(Double.parseDouble(endCoords[0]), Double.parseDouble(endCoords[1]));

        // Agregar las coordenadas a la lista
        List<LatLng> coordenadas = new ArrayList<>();
        coordenadas.add(startLatLng);
        coordenadas.add(endLatLng);
        return coordenadas;
    }
}
