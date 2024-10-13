package com.example.microservicio_solicitudes_interconsulta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio_solicitudes_interconsulta.models.HistoriaClinicaEntity;
import com.example.microservicio_solicitudes_interconsulta.models.SolicitudInterconsultaEntity;
import com.example.microservicio_solicitudes_interconsulta.models.UsuarioEntity;
import com.example.microservicio_solicitudes_interconsulta.models.dtos.SolicitudInterconsultaDto;
import com.example.microservicio_solicitudes_interconsulta.repositories.HistoriaClinicaRepositoryJPA;
import com.example.microservicio_solicitudes_interconsulta.repositories.SolicitudesInterconsultaRepositoryJPA;
import com.example.microservicio_solicitudes_interconsulta.repositories.UsuariosRepositoryJPA;

@Service
public class SolicitudesInterconsultaService {

    @Autowired
    private SolicitudesInterconsultaRepositoryJPA solicitudInterconsultaRepositoryJPA;

    @Autowired
    private HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;

    @Autowired
    private UsuariosRepositoryJPA usuariosRepositoryJPA;

    public SolicitudInterconsultaDto registrarSolicitud(SolicitudInterconsultaDto solicitudDto) {
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findById(solicitudDto.getIdMedico())
            .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findById(solicitudDto.getIdHistoriaClinica())
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

    public List<SolicitudInterconsultaDto> obtenerTodasSolicitudes() {
        List<SolicitudInterconsultaEntity> solicitudes = solicitudInterconsultaRepositoryJPA.findAll();
        return solicitudes.stream()
                         .map(solicitud -> new SolicitudInterconsultaDto().convertirSolicitudInterconsultaEntityASolicitudInterconsultaDto(solicitud))
                         .toList();
    }

    public SolicitudInterconsultaDto obtenerSolicitudPorId(Integer id) {
        SolicitudInterconsultaEntity solicitudEntity = solicitudInterconsultaRepositoryJPA.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud de interconsulta no encontrada"));
        return new SolicitudInterconsultaDto().convertirSolicitudInterconsultaEntityASolicitudInterconsultaDto(solicitudEntity);
    }

    public SolicitudInterconsultaDto actualizarSolicitud(Integer id, SolicitudInterconsultaDto solicitudDto) {
        SolicitudInterconsultaEntity solicitudEntity = solicitudInterconsultaRepositoryJPA.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud de interconsulta no encontrada"));
        
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findById(solicitudDto.getIdMedico())
            .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findById(solicitudDto.getIdHistoriaClinica())
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

    public List<SolicitudInterconsultaDto> obtenerTodasSolicitudesInterconsultaDePaciente(int idPaciente) {
        List<SolicitudInterconsultaEntity> solicitudes = solicitudInterconsultaRepositoryJPA.obtenerSolicitudesInterconsultaPaciente(idPaciente);
        return solicitudes.stream()
                        .map(solicitud -> new SolicitudInterconsultaDto().convertirSolicitudInterconsultaEntityASolicitudInterconsultaDto(solicitud))
                        .toList();
    }
}
