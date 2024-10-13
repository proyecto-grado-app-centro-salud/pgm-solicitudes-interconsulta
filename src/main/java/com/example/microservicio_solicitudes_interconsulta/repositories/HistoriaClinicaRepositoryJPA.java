package com.example.microservicio_solicitudes_interconsulta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio_solicitudes_interconsulta.models.HistoriaClinicaEntity;
import com.example.microservicio_solicitudes_interconsulta.models.UsuarioEntity;


public interface HistoriaClinicaRepositoryJPA extends JpaRepository<HistoriaClinicaEntity, Integer> {

    List<HistoriaClinicaEntity> findByPaciente(UsuarioEntity paciente);
}
