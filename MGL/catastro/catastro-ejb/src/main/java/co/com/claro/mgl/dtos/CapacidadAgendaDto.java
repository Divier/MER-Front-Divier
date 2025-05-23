package co.com.claro.mgl.dtos;

import co.com.claro.app.dtos.AppCrearAgendaOtPropertyRequest;
import co.com.claro.mgw.agenda.capacidad.CapacityRestrictionElement;
import co.com.claro.ofscCore.findMatchingResources.Items;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase para transportar la informaci&oacute;n de consulta en agendamiento
 *
 * @author wgavidia
 * @version 24/11/2017
 */
public class CapacidadAgendaDto implements Serializable, Comparable<CapacidadAgendaDto> {

    private Date date;
    private String timeSlot;
    protected String workSkill;
    private int available;
    private int quota;
    private String bucket;
    private List<RestriccionAgendaDto> restricciones;
    private String resourceId;
    private String nombreTecnico;
    private String aliadoId;
    private String horaInicio;
    private List<Items> tecnicos;
    private String numeroOrdenInmediata;
    private List<AppCrearAgendaOtPropertyRequest> property;

    public CapacidadAgendaDto() {
    }

    public CapacidadAgendaDto(Date date, String timeSlot,
            String workSkill, int available, int quota,String bucket,
            List<CapacityRestrictionElement> restrictions) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.workSkill = workSkill;
        this.available = available;
        this.bucket = bucket;
        this.quota = quota;
        restricciones = new ArrayList<>();
        if (restrictions != null && !restrictions.isEmpty()) {
            for (CapacityRestrictionElement restriction : restrictions) {
                restricciones.add(new RestriccionAgendaDto(
                        restriction.getCode(),
                        restriction.getType(),
                        restriction.getDescription()));
            }
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getWorkSkill() {
        return workSkill;
    }

    public void setWorkSkill(String workSkill) {
        this.workSkill = workSkill;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public List<RestriccionAgendaDto> getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(List<RestriccionAgendaDto> restricciones) {
        this.restricciones = restricciones;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }

    public String getAliadoId() {
        return aliadoId;
    }

    public void setAliadoId(String aliadoId) {
        this.aliadoId = aliadoId;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public List<Items> getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(List<Items> tecnicos) {
        this.tecnicos = tecnicos;
    }

    public String getNumeroOrdenInmediata() {
        return numeroOrdenInmediata;
    }

    public void setNumeroOrdenInmediata(String numeroOrdenInmediata) {
        this.numeroOrdenInmediata = numeroOrdenInmediata;
    }
    
    public List<AppCrearAgendaOtPropertyRequest> getProperty() {
        return property;
    }

    public void setProperty(List<AppCrearAgendaOtPropertyRequest> property) {
        this.property = property;
    }
    
    @Override
    public int compareTo(CapacidadAgendaDto capacidadAgendaDto) {

        int timeSlotA = Integer.parseInt(capacidadAgendaDto.getTimeSlot().substring(0, 2));
        int timeSlotCom = Integer.parseInt(timeSlot.substring(0, 2));

        if (capacidadAgendaDto.getDate().before(date)) {
            return 1;
        }
        if (date.before(capacidadAgendaDto.getDate())) {
            return -1;
        } else if (timeSlotA < timeSlotCom) {
            return 1;
        } else {
            return -1;
        }
    }

}
