package com.example.microservicio_solicitudes_interconsulta.services;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio_solicitudes_interconsulta.models.EspecialidadesEntity;
import com.example.microservicio_solicitudes_interconsulta.models.HistoriaClinicaEntity;
import com.example.microservicio_solicitudes_interconsulta.models.SolicitudInterconsultaEntity;
import com.example.microservicio_solicitudes_interconsulta.models.UsuarioEntity;
import com.example.microservicio_solicitudes_interconsulta.models.dtos.SolicitudInterconsultaDto;
import com.example.microservicio_solicitudes_interconsulta.repositories.EspecialidadesRepositoryJPA;
import com.example.microservicio_solicitudes_interconsulta.repositories.HistoriaClinicaRepositoryJPA;
import com.example.microservicio_solicitudes_interconsulta.repositories.SolicitudesInterconsultaRepositoryJPA;
import com.example.microservicio_solicitudes_interconsulta.repositories.UsuariosRepositoryJPA;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PDFService {
    @Autowired
    UsuariosRepositoryJPA usuariosRepositoryJPA;
    @Autowired
    HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;
    @Autowired
    EspecialidadesRepositoryJPA especialidadesRepositoryJPA;
    @Autowired
    private SolicitudesInterconsultaRepositoryJPA solicitudesInterconsultaRepositoryJPA;
    public byte[] generarPdfReporteSolicitudInterconsulta(SolicitudInterconsultaDto solicitudInterconsultaDto) throws JRException {
        Optional<SolicitudInterconsultaEntity> solicitudInterconsultaEntityOptional=(solicitudInterconsultaDto.getId()!=null)?solicitudesInterconsultaRepositoryJPA.findById(solicitudInterconsultaDto.getId()):Optional.empty();
        if(solicitudInterconsultaEntityOptional.isPresent()){
            solicitudInterconsultaDto=new SolicitudInterconsultaDto().convertirSolicitudInterconsultaEntityASolicitudInterconsultaDto(solicitudInterconsultaEntityOptional.get());
        }else{
            solicitudInterconsultaDto.setCreatedAt(new Date());
            solicitudInterconsultaDto.setUpdatedAt(new Date());
        }
        InputStream jrxmlInputStream = getClass().getClassLoader().getResourceAsStream("reports/solicitud_interconsulta.jrxml");
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findById(solicitudInterconsultaDto.getIdHistoriaClinica()).orElseThrow(() -> new RuntimeException("Historia clinica no encontrada"));
        UsuarioEntity pacienteEntity = usuariosRepositoryJPA.findById(historiaClinicaEntity.getPaciente().getIdUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findById(solicitudInterconsultaDto.getIdMedico()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        EspecialidadesEntity especialidadesEntity = especialidadesRepositoryJPA.findById(historiaClinicaEntity.getEspecialidad().getIdEspecialidad()).orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        if (jrxmlInputStream == null) {
            throw new JRException("No se pudo encontrar el archivo .jrxml en el classpath.");
        }
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hospitalInterconsultado",solicitudInterconsultaDto.getHospitalInterconsultado());
        parameters.put("unidadInterconsultada",solicitudInterconsultaDto.getUnidadInterconsultada());
        parameters.put("queDeseaSaber",solicitudInterconsultaDto.getQueDeseaSaber());
        parameters.put("sintomatologia",solicitudInterconsultaDto.getSintomatologia());
        parameters.put("tratamiento",solicitudInterconsultaDto.getTratamiento());

        parameters.put("apellidoPaterno", pacienteEntity.getApellidoPaterno());
        parameters.put("apellidoMaterno", pacienteEntity.getApellidoMaterno());
        parameters.put("nombres", pacienteEntity.getNombres());
        parameters.put("nhc", historiaClinicaEntity.getIdHistoriaClinica()+"");
        parameters.put("edad", pacienteEntity.getEdad()+"");
        parameters.put("sexo", pacienteEntity.getSexo());
        parameters.put("estadoCivil", pacienteEntity.getEstadoCivil());
        parameters.put("unidad", especialidadesEntity.getNombre());



        parameters.put("fecha", formato.format(solicitudInterconsultaDto.getUpdatedAt()));
        parameters.put("nombreCompletoPaciente", pacienteEntity.getNombres()+" "+pacienteEntity.getApellidoPaterno());
        parameters.put("nombreCompletoMedico", medicoEntity.getNombres()+" "+medicoEntity.getApellidoPaterno());
        parameters.put("firmaPaciente", "");
        parameters.put("firmaMedico", "");

        parameters.put("IMAGE_PATH", getClass().getClassLoader().getResource("images/logo.jpeg").getPath());
        List<SolicitudInterconsultaDto> list = new ArrayList<>();
        list.add(solicitudInterconsultaDto);

        JRBeanCollectionDataSource emptyDataSource = new JRBeanCollectionDataSource(list);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, emptyDataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

       return outputStream.toByteArray();    
    }
}
