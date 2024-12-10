package com.example.microservicio_solicitudes_interconsulta.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.microservicio_solicitudes_interconsulta.models.SolicitudInterconsultaEntity;

public interface SolicitudesInterconsultaRepositoryJPA extends JpaRepository<SolicitudInterconsultaEntity, Integer> ,JpaSpecificationExecutor<SolicitudInterconsultaEntity>{
    @Query("SELECT si FROM SolicitudInterconsultaEntity si "
    + "JOIN si.historiaClinica hc "
    + "JOIN hc.paciente p "
    + "WHERE p.idUsuario = :idPaciente")
    List<SolicitudInterconsultaEntity> obtenerSolicitudesInterconsultaPaciente(@Param("idPaciente") int idPaciente);
    Optional<SolicitudInterconsultaEntity> findByIdSolicitudInterconsultaAndDeletedAtIsNull(int idSolicitudInterconsulta);
}