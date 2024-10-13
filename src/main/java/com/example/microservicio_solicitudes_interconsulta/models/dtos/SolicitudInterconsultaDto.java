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
    private Integer idHistoriaClinica;
    private Integer idMedico;
    private String hospitalInterconsultado;
    private String unidadInterconsultada;
    private String queDeseaSaber;
    private String sintomatologia;
    private String tratamiento;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public SolicitudInterconsultaDto convertirSolicitudInterconsultaEntityASolicitudInterconsultaDto(SolicitudInterconsultaEntity solicitudInterconsultaEntity) {
        SolicitudInterconsultaDto solicitudInterconsultaDto = new SolicitudInterconsultaDto();
        solicitudInterconsultaDto.id = solicitudInterconsultaEntity.getIdSolicitudInterconsulta();
        solicitudInterconsultaDto.idHistoriaClinica = solicitudInterconsultaEntity.getHistoriaClinica().getIdHistoriaClinica();
        solicitudInterconsultaDto.idMedico = solicitudInterconsultaEntity.getMedico().getIdUsuario();
        solicitudInterconsultaDto.hospitalInterconsultado = solicitudInterconsultaEntity.getHospitalInterconsultado();
        solicitudInterconsultaDto.unidadInterconsultada = solicitudInterconsultaEntity.getUnidadInterconsultada();
        solicitudInterconsultaDto.queDeseaSaber = solicitudInterconsultaEntity.getQueDeseaSaber();
        solicitudInterconsultaDto.sintomatologia = solicitudInterconsultaEntity.getSintomatologia();
        solicitudInterconsultaDto.tratamiento = solicitudInterconsultaEntity.getTratamiento();
        solicitudInterconsultaDto.createdAt = solicitudInterconsultaEntity.getCreatedAt();
        solicitudInterconsultaDto.updatedAt = solicitudInterconsultaEntity.getUpdatedAt();
        solicitudInterconsultaDto.deletedAt = solicitudInterconsultaEntity.getDeletedAt();
        return solicitudInterconsultaDto;
    }
}