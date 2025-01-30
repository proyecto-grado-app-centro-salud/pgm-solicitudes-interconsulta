package com.example.microservicio_solicitudes_interconsulta.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.microservicio_solicitudes_interconsulta.models.SolicitudInterconsultaEntity;

public interface SolicitudesInterconsultaRepositoryJPA extends JpaRepository<SolicitudInterconsultaEntity, Integer> ,JpaSpecificationExecutor<SolicitudInterconsultaEntity>{
    @Query("SELECT si FROM SolicitudInterconsultaEntity si "
    + "JOIN si.historiaClinica hc "
    + "JOIN hc.paciente p "
    + "WHERE p.idUsuario = :idPaciente")
    List<SolicitudInterconsultaEntity> obtenerSolicitudesInterconsultaPaciente(@Param("idPaciente") String idPaciente);
    Optional<SolicitudInterconsultaEntity> findByIdSolicitudInterconsultaAndDeletedAtIsNull(int idSolicitudInterconsulta);

    @Modifying
    @Query(value = "UPDATE solicitudes_interconsulta SET deleted_at = ?2 WHERE id_historia_clinica = ?1 AND deleted_at IS NULL", nativeQuery = true)
    void markAsDeletedAllSolicitudesInterconsultasFromHistoriaClinica(int idHistoriaClinica, Date date);
}