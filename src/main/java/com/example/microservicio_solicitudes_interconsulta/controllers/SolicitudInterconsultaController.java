package com.example.microservicio_solicitudes_interconsulta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservicio_solicitudes_interconsulta.models.dtos.SolicitudInterconsultaDto;
import com.example.microservicio_solicitudes_interconsulta.services.ContainerMetadataService;
import com.example.microservicio_solicitudes_interconsulta.services.SolicitudesInterconsultaService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/solicitudes-interconsulta")
public class SolicitudInterconsultaController {
    @Autowired
    private ContainerMetadataService containerMetadataService;
    @Autowired
    private SolicitudesInterconsultaService solicitudesInterconsultaService;

    @PostMapping
    public ResponseEntity<SolicitudInterconsultaDto> registrarSolicitud(@RequestBody SolicitudInterconsultaDto solicitudDto) {
        try {
            SolicitudInterconsultaDto nuevaSolicitud = solicitudesInterconsultaService.registrarSolicitud(solicitudDto);
            return new ResponseEntity<>(nuevaSolicitud, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<SolicitudInterconsultaDto>> obtenerTodasSolicitudes() {
        try {
            List<SolicitudInterconsultaDto> solicitudes = solicitudesInterconsultaService.obtenerTodasSolicitudes();
            return new ResponseEntity<>(solicitudes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudInterconsultaDto> obtenerSolicitudPorId(@PathVariable Integer id) {
        try {
            SolicitudInterconsultaDto solicitud = solicitudesInterconsultaService.obtenerSolicitudPorId(id);
            return new ResponseEntity<>(solicitud, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudInterconsultaDto> actualizarSolicitud(@PathVariable Integer id, @RequestBody SolicitudInterconsultaDto solicitudDto) {
        try {
            SolicitudInterconsultaDto solicitudActualizada = solicitudesInterconsultaService.actualizarSolicitud(id, solicitudDto);
            return new ResponseEntity<>(solicitudActualizada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<SolicitudInterconsultaDto>> obtenerSolicitudesInterconsultaDePaciente(@PathVariable int idPaciente) {
        try {
            List<SolicitudInterconsultaDto> papeletas = solicitudesInterconsultaService.obtenerTodasSolicitudesInterconsultaDePaciente(idPaciente);
            return new ResponseEntity<>(papeletas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/info-container")
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio historias clinicas: " + containerMetadataService.retrieveContainerMetadataInfo();
    }
}