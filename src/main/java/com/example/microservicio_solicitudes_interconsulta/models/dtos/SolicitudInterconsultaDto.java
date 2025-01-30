package com.example.microservicio_solicitudes_interconsulta.models.dtos;

import java.util.Date;

import com.example.microservicio_solicitudes_interconsulta.models.SolicitudInterconsultaEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudInterconsultaDto {
    private Integer id;
    private String hospitalInterconsultado;
    private String unidadInterconsultada;
    private String queDeseaSaber;
    private String sintomatologia;
    private String tratamiento;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Integer idHistoriaClinica;
    private String diagnosticoPresuntivo;
    private Integer idEspecialidad;
    private String nombreEspecialidad;
    private String idMedico;
    private String nombreMedico;
    private String idPaciente;
    private String pacientePropietario;
    private String ciPropietario;


    public static SolicitudInterconsultaDto convertirSolicitudInterconsultaEntityASolicitudInterconsultaDto(SolicitudInterconsultaEntity solicitudInterconsultaEntity) {
        SolicitudInterconsultaDto solicitudInterconsultaDto = new SolicitudInterconsultaDto();
        solicitudInterconsultaDto.setId(solicitudInterconsultaEntity.getIdSolicitudInterconsulta());
        solicitudInterconsultaDto.setHospitalInterconsultado(solicitudInterconsultaEntity.getHospitalInterconsultado());
        solicitudInterconsultaDto.setUnidadInterconsultada(solicitudInterconsultaEntity.getUnidadInterconsultada());
        solicitudInterconsultaDto.setQueDeseaSaber(solicitudInterconsultaEntity.getQueDeseaSaber());
        solicitudInterconsultaDto.setSintomatologia(solicitudInterconsultaEntity.getSintomatologia());
        solicitudInterconsultaDto.setTratamiento(solicitudInterconsultaEntity.getTratamiento());
        solicitudInterconsultaDto.setCreatedAt(solicitudInterconsultaEntity.getCreatedAt());
        solicitudInterconsultaDto.setUpdatedAt(solicitudInterconsultaEntity.getUpdatedAt());
        solicitudInterconsultaDto.setDeletedAt(solicitudInterconsultaEntity.getDeletedAt());
        solicitudInterconsultaDto.setIdHistoriaClinica(solicitudInterconsultaEntity.getHistoriaClinica().getIdHistoriaClinica());
        solicitudInterconsultaDto.setDiagnosticoPresuntivo(solicitudInterconsultaEntity.getHistoriaClinica().getDiagnosticoPresuntivo());
        solicitudInterconsultaDto.setIdEspecialidad(solicitudInterconsultaEntity.getHistoriaClinica().getEspecialidad().getIdEspecialidad());
        solicitudInterconsultaDto.setNombreEspecialidad(solicitudInterconsultaEntity.getHistoriaClinica().getEspecialidad().getNombre());
        solicitudInterconsultaDto.setIdMedico(solicitudInterconsultaEntity.getMedico().getIdUsuario());
        solicitudInterconsultaDto.setNombreMedico(solicitudInterconsultaEntity.getMedico().getNombres()+" "+solicitudInterconsultaEntity.getMedico().getApellidoPaterno()+" "+solicitudInterconsultaEntity.getMedico().getApellidoMaterno());
        solicitudInterconsultaDto.setIdPaciente(solicitudInterconsultaEntity.getHistoriaClinica().getPaciente().getIdUsuario());
        solicitudInterconsultaDto.setPacientePropietario(solicitudInterconsultaEntity.getHistoriaClinica().getPaciente().getNombres()+" "+solicitudInterconsultaEntity.getHistoriaClinica().getPaciente().getApellidoPaterno()+" "+solicitudInterconsultaEntity.getHistoriaClinica().getPaciente().getApellidoMaterno());
        solicitudInterconsultaDto.setCiPropietario(solicitudInterconsultaEntity.getHistoriaClinica().getPaciente().getCi());
        return solicitudInterconsultaDto;
    }
}