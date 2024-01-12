package com.es.iesmz.transita3.Transita.controller;
import com.es.iesmz.transita3.Transita.service.OpenRouteService;
import com.google.maps.model.LatLng;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;
@RestController
@RequestMapping("/rutas")
public class OpenRouteController {

    @Autowired
    private OpenRouteService openRouteService;

    @Operation(summary = "Obtain wheelchair directions between start and end coordinates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of coordinates",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = LatLng.class)))
            )
    })
    @GetMapping("/start/{start}/end/{end}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<List<List<Double>>> getWheelchairDirections(@PathVariable String start, @PathVariable String end) {
        try {
            List<List<Double>> coordinates = openRouteService.getCoordinatesForDirections(start, end);
            return new ResponseEntity<>(coordinates, HttpStatus.OK);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
