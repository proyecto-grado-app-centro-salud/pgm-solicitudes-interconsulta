package com.example.microservicio_solicitudes_interconsulta.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservicio_solicitudes_interconsulta.models.dtos.SolicitudInterconsultaDto;
import com.example.microservicio_solicitudes_interconsulta.services.ContainerMetadataService;
import com.example.microservicio_solicitudes_interconsulta.services.SolicitudesInterconsultaService;

@RestController
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
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Page<SolicitudInterconsultaDto>> obtenerTodasSolicitudes(@RequestParam(required = false) String fechaInicio, @RequestParam(required = false) String fechaFin,@RequestParam(required = false) String ciPaciente,@RequestParam(required = false) String nombrePaciente,@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) String diagnosticoPresuntivo,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            Page<SolicitudInterconsultaDto> solicitudes = solicitudesInterconsultaService.obtenerTodasSolicitudes(fechaInicio,fechaFin,ciPaciente,nombrePaciente,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo,page,size);
            return new ResponseEntity<>(solicitudes, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudInterconsultaDto> obtenerSolicitudPorId(@PathVariable Integer id) {
        try {
            SolicitudInterconsultaDto solicitud = solicitudesInterconsultaService.obtenerSolicitudPorId(id);
            return new ResponseEntity<>(solicitud, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudInterconsultaDto> actualizarSolicitud(@PathVariable Integer id, @RequestBody SolicitudInterconsultaDto solicitudDto) {
        try {
            SolicitudInterconsultaDto solicitudActualizada = solicitudesInterconsultaService.actualizarSolicitud(id, solicitudDto);
            return new ResponseEntity<>(solicitudActualizada, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<Page<SolicitudInterconsultaDto>> obtenerSolicitudesInterconsultaDePaciente(@PathVariable int idPaciente,@RequestParam(required = false) String fechaInicio, @RequestParam(required = false) String fechaFin,@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) String diagnosticoPresuntivo,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            Page<SolicitudInterconsultaDto> papeletas = solicitudesInterconsultaService.obtenerTodasSolicitudesInterconsultaDePaciente(idPaciente,fechaInicio,fechaFin,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo,page,size);
            return new ResponseEntity<>(papeletas, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/info-container")
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio historias clinicas: " + containerMetadataService.retrieveContainerMetadataInfo();
    }
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> obtenerPDFSolicitudInterconsulta(SolicitudInterconsultaDto solicitudInterconsultaDto) {
        try {
            byte[] pdfBytes = solicitudesInterconsultaService.obtenerPDFSolicitudInterconsulta(solicitudInterconsultaDto);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=SolicitudInterconsulta.pdf");
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Length", "" + pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
      @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try{
            solicitudesInterconsultaService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(value = "/historia-clinica/{id}")
    public ResponseEntity<Void> deleteSolicitudesInterconsultasDeHistoriaClinica(@PathVariable int id) {
        try{
            solicitudesInterconsultaService.deleteSolicitudesInterconsultasDeHistoriaClinica(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}