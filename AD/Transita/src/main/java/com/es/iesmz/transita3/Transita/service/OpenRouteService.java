package com.es.iesmz.transita3.Transita.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface OpenRouteService {
    List<List<Double>> getCoordinatesForDirections(String startPoint, String endPoint) throws IOException, InterruptedException, URISyntaxException;
}
