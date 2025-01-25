package com.example.microservicio_solicitudes_interconsulta.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.microservicio_solicitudes_interconsulta.models.HistoriaClinicaEntity;
import com.example.microservicio_solicitudes_interconsulta.models.SolicitudInterconsultaEntity;
import com.example.microservicio_solicitudes_interconsulta.models.UsuarioEntity;
import com.example.microservicio_solicitudes_interconsulta.models.dtos.SolicitudInterconsultaDto;
import com.example.microservicio_solicitudes_interconsulta.repositories.HistoriaClinicaRepositoryJPA;
import com.example.microservicio_solicitudes_interconsulta.repositories.SolicitudesInterconsultaRepositoryJPA;
import com.example.microservicio_solicitudes_interconsulta.repositories.UsuariosRepositoryJPA;
import com.example.microservicio_solicitudes_interconsulta.util.SolicitudesInterconsultaSpecification;

import jakarta.transaction.Transactional;

@Service
public class SolicitudesInterconsultaService {

    @Autowired
    private SolicitudesInterconsultaRepositoryJPA solicitudInterconsultaRepositoryJPA;

    @Autowired
    private HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;

    @Autowired
    private UsuariosRepositoryJPA usuariosRepositoryJPA;

    @Autowired
    PDFService pdfService;


    @Autowired
    private ConvertirTiposDatosService convertirTiposDatosService;
    public SolicitudInterconsultaDto registrarSolicitud(SolicitudInterconsultaDto solicitudDto) {
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(solicitudDto.getIdMedico())
            .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findByIdHistoriaClinicaAndDeletedAtIsNull(solicitudDto.getIdHistoriaClinica())
            .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));

        SolicitudInterconsultaEntity solicitudEntity = new SolicitudInterconsultaEntity();
        solicitudEntity.setHistoriaClinica(historiaClinicaEntity);
        solicitudEntity.setMedico(medicoEntity);
        solicitudEntity.setHospitalInterconsultado(solicitudDto.getHospitalInterconsultado());
        solicitudEntity.setUnidadInterconsultada(solicitudDto.getUnidadInterconsultada());
        solicitudEntity.setQueDeseaSaber(solicitudDto.getQueDeseaSaber());
        solicitudEntity.setSintomatologia(solicitudDto.getSintomatologia());
        solicitudEntity.setTratamiento(solicitudDto.getTratamiento());
        
        solicitudEntity = solicitudInterconsultaRepositoryJPA.save(solicitudEntity);
        return new SolicitudInterconsultaDto().convertirSolicitudInterconsultaEntityASolicitudInterconsultaDto(solicitudEntity);
    }

    public Page<SolicitudInterconsultaDto> obtenerTodasSolicitudes(String fechaInicio, String fechaFin, String ciPaciente, String nombrePaciente, String nombreMedico, String nombreEspecialidad, String diagnosticoPresuntivo, Integer page, Integer size) {
        Pageable pageable = Pageable.unpaged();
        if(page!=null && size!=null){
            pageable = PageRequest.of(page, size);
        }         
        Specification<SolicitudInterconsultaEntity> spec = Specification.where(SolicitudesInterconsultaSpecification.obtenerSolicitudesIPorParametros(convertirTiposDatosService.convertirStringADate(fechaInicio),convertirTiposDatosService.convertirStringADate(fechaFin),ciPaciente,nombrePaciente,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo));
        Page<SolicitudInterconsultaEntity> solicitudesEntitiesPage=solicitudInterconsultaRepositoryJPA.findAll(spec,pageable);

        return solicitudesEntitiesPage.map(SolicitudInterconsultaDto::convertirSolicitudInterconsultaEntityASolicitudInterconsultaDto);
    }

    public SolicitudInterconsultaDto obtenerSolicitudPorId(Integer id) {
        SolicitudInterconsultaEntity solicitudEntity = solicitudInterconsultaRepositoryJPA.findByIdSolicitudInterconsultaAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Solicitud de interconsulta no encontrada"));
        return new SolicitudInterconsultaDto().convertirSolicitudInterconsultaEntityASolicitudInterconsultaDto(solicitudEntity);
    }

    public SolicitudInterconsultaDto actualizarSolicitud(Integer id, SolicitudInterconsultaDto solicitudDto) {
        SolicitudInterconsultaEntity solicitudEntity = solicitudInterconsultaRepositoryJPA.findByIdSolicitudInterconsultaAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Solicitud de interconsulta no encontrada"));
        
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(solicitudDto.getIdMedico())
            .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findByIdHistoriaClinicaAndDeletedAtIsNull(solicitudDto.getIdHistoriaClinica())
            .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));

        solicitudEntity.setHistoriaClinica(historiaClinicaEntity);
        solicitudEntity.setMedico(medicoEntity);
        solicitudEntity.setHospitalInterconsultado(solicitudDto.getHospitalInterconsultado());
        solicitudEntity.setUnidadInterconsultada(solicitudDto.getUnidadInterconsultada());
        solicitudEntity.setQueDeseaSaber(solicitudDto.getQueDeseaSaber());
        solicitudEntity.setSintomatologia(solicitudDto.getSintomatologia());
        solicitudEntity.setTratamiento(solicitudDto.getTratamiento());
        
        solicitudEntity = solicitudInterconsultaRepositoryJPA.save(solicitudEntity);
        return new SolicitudInterconsultaDto().convertirSolicitudInterconsultaEntityASolicitudInterconsultaDto(solicitudEntity);
    }

    public Page<SolicitudInterconsultaDto> obtenerTodasSolicitudesInterconsultaDePaciente(int idPaciente, String fechaInicio, String fechaFin, String nombreMedico, String nombreEspecialidad, String diagnosticoPresuntivo, Integer page, Integer size) {
        Pageable pageable = Pageable.unpaged();
        if(page!=null && size!=null){
            pageable = PageRequest.of(page, size);
        }         
        Specification<SolicitudInterconsultaEntity> spec = Specification.where(SolicitudesInterconsultaSpecification.obtenerSolicitudesIDePacientePorParametros(idPaciente,convertirTiposDatosService.convertirStringADate(fechaInicio),convertirTiposDatosService.convertirStringADate(fechaFin),nombreMedico,nombreEspecialidad,diagnosticoPresuntivo));
        Page<SolicitudInterconsultaEntity> solicitudesEntitiesPage=solicitudInterconsultaRepositoryJPA.findAll(spec,pageable);

        return solicitudesEntitiesPage.map(SolicitudInterconsultaDto::convertirSolicitudInterconsultaEntityASolicitudInterconsultaDto);
    }

    public byte[] obtenerPDFSolicitudInterconsulta(SolicitudInterconsultaDto solicitudInterconsultaDto) {
        try {
            return pdfService.generarPdfReporteSolicitudInterconsulta(solicitudInterconsultaDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el PDF de la historia clinica.", e);
        }
    }

    public void delete(int id) {
        SolicitudInterconsultaEntity solicitudEntity = solicitudInterconsultaRepositoryJPA.findByIdSolicitudInterconsultaAndDeletedAtIsNull(id)
        .orElseThrow(() -> new RuntimeException("Solicitud de interconsulta no encontrada"));
        
        solicitudEntity.markAsDeleted();
        solicitudInterconsultaRepositoryJPA.save(solicitudEntity);
    }

    @Transactional
    public void deleteSolicitudesInterconsultasDeHistoriaClinica(int idHistoriaClinica) {
        solicitudInterconsultaRepositoryJPA.markAsDeletedAllSolicitudesInterconsultasFromHistoriaClinica(idHistoriaClinica,new Date());
    }
}
