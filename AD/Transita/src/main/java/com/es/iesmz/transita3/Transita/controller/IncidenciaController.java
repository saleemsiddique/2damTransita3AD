package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.Utils.Util;
import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.EstadoIncidencia;
import com.es.iesmz.transita3.Transita.domain.Incidencia;

import com.es.iesmz.transita3.Transita.domain.Punto;
import com.es.iesmz.transita3.Transita.exception.EstadoIncidenciaNotFoundException;
import com.es.iesmz.transita3.Transita.exception.IncidenciaNotFoundException;

import com.es.iesmz.transita3.Transita.service.IncidenciaService;

import com.es.iesmz.transita3.Transita.service.PuntoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import static com.es.iesmz.transita3.Transita.controller.Response.NOT_FOUND;
@RestController
public class IncidenciaController {
    @Autowired
    private IncidenciaService incidenciaService;

    @Autowired
    private PuntoService puntoService;

    @Operation(summary = "Obtiene el listado de incidencias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de incidencias",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
    })
    @GetMapping("/incidencias")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Set<Incidencia>> getIncidencia() {
        Set<Incidencia> incidencias = null;
        incidencias = incidenciaService.findAll();

        for (Incidencia incidencia : incidencias) {
            if (incidencia.getEstado() == EstadoIncidencia.ENVIADO) {
                incidencia.setFotos(decompressBase64String(incidencia.getFotos()));
            }
        }

        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de incidencias de un punto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de incidencias",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
    })
    @GetMapping("/incidencias/puntoid/{id}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Set<Incidencia>> getIncidenciaByPunto(@PathVariable long id) {
        Set<Incidencia> incidencias = null;
        incidencias = incidenciaService.findByPunto(id);
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getEstado() == EstadoIncidencia.ENVIADO) {
                incidencia.setFotos(decompressBase64String(incidencia.getFotos()));
            }
        }
        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una incidencia determinada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incidencia con ese ID",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
    })
    @GetMapping("/incidencia/id/{id}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Incidencia> getIncidenciaById(@PathVariable long id) {
        Incidencia incidencia = incidenciaService.findById(id)
                .orElseThrow(() -> new IncidenciaNotFoundException(id));
        return new ResponseEntity<>(incidencia, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene las incidencias de un cliente especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incidencias con ese Cliente_ID",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
    })
    @GetMapping("/incidencias/clienteid/{id}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Set<Incidencia>> findByIncidenciaByClienteId(@PathVariable long id) {
        Set<Incidencia> incidencias = incidenciaService.findByIncidenciaByClienteId(id);
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getEstado() == EstadoIncidencia.ENVIADO) {
                incidencia.setFotos(decompressBase64String(incidencia.getFotos()));
            }
        }
        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de incidencias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de incidencias",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
    })
    @GetMapping("/incidencia/estado/{estado}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Set<Incidencia>> findByEstado(@PathVariable int estado) {
        EstadoIncidencia estadoIncidencia = null;

        if (estado == 0) {
            estadoIncidencia = EstadoIncidencia.ENVIADO;
        } else if (estado == 1) {
            estadoIncidencia = EstadoIncidencia.ACEPTADO;
        } else if (estado == 2) {
            estadoIncidencia = EstadoIncidencia.ENPROCESO;
        } else if (estado == 3) {
            estadoIncidencia = EstadoIncidencia.FINALIZADO;
        } else {
            throw new EstadoIncidenciaNotFoundException("Estado de incidencia no existente");
        }

        Set<Incidencia> incidencias = incidenciaService.findByEstado(estadoIncidencia);
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getEstado() == EstadoIncidencia.ENVIADO) {
                incidencia.setFotos(decompressBase64String(incidencia.getFotos()));
            }
        }
        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de incidencias por duracion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de incidencias",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
    })
    @GetMapping("/incidencia/duracion/{duracion}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Set<Incidencia>> getIncideciaByDuracion(@PathVariable String duracion) {
        Set<Incidencia> incidencias = incidenciaService.findByDuracion(duracion);
        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de incidencias por paginas y filtrado por estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de incidencias",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
    })
    @GetMapping("/incidencia/filtrados")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Set<Incidencia>> getIncidenciaPagsPorEstado(
            @RequestParam(name = "estado", required = false) String estado,
            @RequestParam(name = "idInicial") int idInicial,
            @RequestParam(name = "idFinal") int idFinal
    ) {
        Set<Incidencia> incidencias;
        if(estado == null){
            incidencias = incidenciaService.findAllIncidenciasByPages(idInicial, idFinal);
        }else {
            incidencias = incidenciaService.findIncidenciaByPagesFiltro(estado, idInicial, idFinal);
        }
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getEstado() == EstadoIncidencia.ENVIADO) {
                incidencia.setFotos(decompressBase64String(incidencia.getFotos()));
            }
        }
        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene numero de incidencias con filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de puntos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))
            )})
    @GetMapping("/incidencia/count/filtros")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Integer> getIncidenciaCountConFiltros(
            @RequestParam(name = "estado", required = false) String estado) {
        int cantidadIncidencias;
        if(estado == null){
            cantidadIncidencias = Math.toIntExact(incidenciaService.count());
        }else {
            cantidadIncidencias = Math.toIntExact(incidenciaService.countIncidencia(estado));
        }
        return new ResponseEntity<>(cantidadIncidencias, HttpStatus.OK);
    }

    @Operation(summary = "Obtener una lista de todos las incidencias con filtros")
    @GetMapping("/incidencia/filtro")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Incidencia>> getClientesFiltrados(
            @RequestParam(name = "valor") String valor,
            @RequestParam(name = "filtro") String filtro) {
        Set<Incidencia> incidencias = incidenciaService.findAllByFiltro(filtro, valor);
        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo incidencia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el incidencia", content = @Content(schema = @Schema(implementation =
                    Incidencia.class)))
    })
    @PostMapping("/incidencia")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USUARIO') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Incidencia> addIncidencia(@RequestBody Incidencia incidencia) {
        PuntoController puntoController = new PuntoController();
        incidencia.setFotos(compressBase64String(incidencia.getFotos()));
        Incidencia nuevaIncidencia = incidenciaService.addIncidencia(incidencia);
        return new ResponseEntity<>(nuevaIncidencia, HttpStatus.OK);
    }


    @Operation(summary = "Modifica una incidencia en el catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica la incidencia", content = @Content(schema = @Schema(implementation =
                    Incidencia.class))),
            @ApiResponse(responseCode = "404", description = "la incidencia no existe", content = @Content(schema = @Schema(implementation =
                    Response.class)))
    })
    @PutMapping("/incidencia/modificar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USUARIO') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Incidencia> modifyIncidencia(@PathVariable long id, @RequestBody Incidencia incidencia) {
        Optional<Incidencia> optionalIncidencia = incidenciaService.findById(id);
        if(incidencia.getFotos() != null){
            if (incidencia.getEstado() == EstadoIncidencia.ENVIADO) {
                String base64Image = decompressBase64String(incidencia.getFotos());
                uploadToFTP(Util.FTPHost, Integer.parseInt(Util.FTPPort), Util.ftpUsername, Util.ftpPassword, Util.ftpRemoteDirectory, incidencia, base64Image);
                updatePunto(incidencia, "PUT");
            }
        } else{
            incidencia.setFotos(optionalIncidencia.get().getFotos());
        }

        Incidencia oldincidencia = incidenciaService.modifyIncidencia(id, incidencia);
        return new ResponseEntity<>(incidencia, HttpStatus.OK);
    }

    @Operation(summary = "Modifica el estado de una incidencia en el catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica el estado de la incidencia", content = @Content(schema = @Schema(implementation =
                    Incidencia.class))),
            @ApiResponse(responseCode = "404", description = "la incidencia no existe", content = @Content(schema = @Schema(implementation =
                    Response.class)))
    })
    @PutMapping("/incidencia/modificarEstado/{id}/{nuevoEstado}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Incidencia> modifyEstadoIncidencia(@PathVariable long id, @PathVariable String nuevoEstado) {
        Optional<Incidencia> optionalIncidencia = incidenciaService.findById(id);
        if (optionalIncidencia.isPresent()) {
            Incidencia incidencia = optionalIncidencia.get();
            EstadoIncidencia estadoIncidencia = mapEstado(nuevoEstado);

            if (incidencia.getEstado() == EstadoIncidencia.ENVIADO) {
                String base64Image = decompressBase64String(incidencia.getFotos());
                uploadToFTP("127.0.0.1", 21, "web", "web", "/img/puntos", incidencia, base64Image);
                updatePunto(incidencia, "PUT");
                incidencia.setFotos(null);
            }
            incidencia.setEstado(estadoIncidencia);
            // Guardar la incidencia modificada en la base de datos
            incidenciaService.modifyIncidencia(id, incidencia);

            return new ResponseEntity<>(incidencia, HttpStatus.OK);
        } else {
            throw new IncidenciaNotFoundException(id);
        }
    }

    private void updatePunto(Incidencia incidencia, String action){
        // Actualizar la propiedad fotos en la incidencia con la URL
        Set<Incidencia> incidenciasSet = getIncidenciaByPunto(incidencia.getPunto().getId()).getBody();
        List<Incidencia> incidencias = new ArrayList<>(incidenciasSet);
        switch (action) {
            case "PUT": {
                incidencias.add(incidencia);
                break;
            }
            case "DELETE": {
                incidencias.remove(getLastIncidencia(incidencias));
                break;
            }
        }

        if (incidencias.isEmpty()) {
            incidencia.getPunto().setFoto(null);
            incidencia.getPunto().setDescripcion(null);
        } else {
            // Utilizar la referencia a la última incidencia antes de la eliminación
            incidencia.getPunto().setFoto(getLastIncidencia(incidencias).getPunto().getId() + "_" + getLastIncidencia(incidencias).getPunto().getLatitud() + "_" + getLastIncidencia(incidencias).getPunto().getLongitud() + ".jpg");
            incidencia.getPunto().setDescripcion(getLastIncidencia(incidencias).getDescripcion());
        }

        puntoService.modifyPunto(incidencia.getPunto().getId(), incidencia.getPunto());
    }



    private Incidencia getLastIncidencia(List<Incidencia> incidencias){
        return incidencias.get(incidencias.size() - 1);
    }


    // Método para mapear el estado como cadena a un Enum de EstadoIncidencia
    private EstadoIncidencia mapEstado(String nuevoEstado) {
        return switch (nuevoEstado.toLowerCase()) {
            case "aceptado" -> EstadoIncidencia.ACEPTADO;
            case "enproceso" -> EstadoIncidencia.ENPROCESO;
            case "finalizado" -> EstadoIncidencia.FINALIZADO;
            default -> throw new EstadoIncidenciaNotFoundException("Estado de incidencia no existente");
        };
    }


    @Operation(summary = "Elimina una incidencia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina la incidencia", content = @Content(schema = @Schema(implementation =
                    Response.class))),
            @ApiResponse(responseCode = "404", description = "La incidencia no existe", content = @Content(schema = @Schema(implementation =
                    Response.class)))
    })
    @DeleteMapping("/incidencia/eliminar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USUARIO') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Incidencia> deleteIncidencia(@PathVariable long id) {
        Incidencia incidencia = incidenciaService.findById(id)
                .orElseThrow(() -> new IncidenciaNotFoundException(id));
        updatePunto(incidencia, "DELETE");
        incidenciaService.deleteIncidencia(id);
        return new ResponseEntity(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(IncidenciaNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response>
    handleException(IncidenciaNotFoundException pnfe) {
        Response response = Response.errorResonse(NOT_FOUND,
                pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public static String compressBase64String(String base64String) {
        try {//  w    w  w.    de  m   o  2   s.  c   o   m
            // Step 1: Decode the Base64 string to get the original binary data.
            byte[] decodedBytes = Base64.getDecoder().decode(base64String);

            // Step 2: Compress the binary data using gzip compression.
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(outputStream, new Deflater(Deflater.BEST_COMPRESSION));
            deflaterOutputStream.write(decodedBytes);
            deflaterOutputStream.finish();

            byte[] compressedBytes = outputStream.toByteArray();

            // Step 3: Encode the compressed binary data back to Base64 format.
            String compressedBase64String = Base64.getEncoder().encodeToString(compressedBytes);

            return compressedBase64String;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decompressBase64String(String compressedBase64String) {
        try {
            byte[] compressedBytes = Base64.getDecoder().decode(compressedBase64String);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedBytes);
            InflaterInputStream inflaterInputStream = new InflaterInputStream(inputStream, new Inflater());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inflaterInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inflaterInputStream.close();
            byte[] decompressedBytes = outputStream.toByteArray();

            return Base64.getEncoder().encodeToString(decompressedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void uploadToFTP(String server, int port, String username, String password, String remoteFilePath, Incidencia incidencia, String base64Image) {
        FTPClient ftpClient = new FTPClient();

        try {
            // Conectar al servidor FTP
            ftpClient.connect(server, port);
            ftpClient.login(username, password);

            // Configurar el tipo de archivo a transferir
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Cambiar al directorio remoto
            ftpClient.changeWorkingDirectory(remoteFilePath);

            // Nombre del archivo que se subirá al servidor FTP (ID de la incidencia + ".jpg")
            String remoteFileName = incidencia.getPunto().getId() + "_" + incidencia.getPunto().getLatitud() + "_" + incidencia.getPunto().getLongitud() + ".jpg";

            // Decodificar la cadena Base64 y crear un InputStream
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            InputStream imageInputStream = new ByteArrayInputStream(imageBytes);

            // Subir el archivo al servidor FTP
            ftpClient.storeFile(remoteFileName, imageInputStream);

            // Cerrar la conexión
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}