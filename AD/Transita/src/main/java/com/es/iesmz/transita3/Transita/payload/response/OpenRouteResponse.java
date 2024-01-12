package com.es.iesmz.transita3.Transita.payload.response;

import com.google.maps.model.LatLng;
import java.util.List;

public class OpenRouteResponse {
    public String type;
    public Metadata metadata;
    public List<Feature> features;

    public static class Metadata {
        public String attribution;
        public String service;
        public long timestamp;
        public Query query;
        public Engine engine;
    }

    public static class Query {
        public List<List<Double>> coordinates;
        public String profile;
        public String format;
    }

    public static class Engine {
        public String version;
        public String build_date;
        public String graph_date;
    }

    public static class Feature {
        public List<Double> bbox;
        public String type;
        public Properties properties;
        public Geometry geometry;
    }

    public static class Properties {
        // Define properties if needed
    }

    public static class Geometry {
        public List<List<Double>> coordinates;
        public String type;
    }
}
