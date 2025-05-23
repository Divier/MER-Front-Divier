package co.com.claro.mgl.mbeans.cm.componente;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.DayOfWeek;
import co.com.claro.mgl.jpa.entities.cm.TipoRestriccion;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * @author Admin
 */
@FacesComponent("co.com.claro.mgl.mbeans.cm.componente.ControlerHorariosCM")
public class ControlerHorariosCM extends UINamingContainer { 

    private static final ArrayList<Horas> lsHoras;
    private static final String HORARIO_RESTRICCION_KEY = "HORARIO_RESTRICCION_KEY";
    private CmtHorarioRestriccionMgl horarioRestriccionCmEdit;
    private ArrayList<RegistroHorario> arraylistregistroHorario = new ArrayList<RegistroHorario>();
    private FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
    
    
    static {
        lsHoras = new ArrayList<Horas>(47);
        for (int i = 1; i <= 24; i++) {
            lsHoras.add(new Horas(i, i + ":00", String.format("%04d", Integer.parseInt("0" + (i) + "00"))));
            if (i != 24) {
                lsHoras.add(new Horas(i, i + ":30", String.format("%04d", Integer.parseInt("0" + (i) + "30"))));
            }
        }
    }

    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    public void init() {
        if (getStateHelper().get("horarioRestriccion")==null) {
            this.setHorarioRes((ArrayList<CmtHorarioRestriccionMgl>) getAttributes().get("horarioRestriccion"));
        }
    }
    
    

    private void pintarDatos() {
        init();
        arraylistregistroHorario = new ArrayList<RegistroHorario>();
        RegistroHorario registroHorario;
        for (Horas h : lsHoras) {
            if(h.getHora().equalsIgnoreCase("24:00")){
                System.err.println("No se registra esta hora");  
            }else{
            registroHorario = new RegistroHorario();
            registroHorario.setHora(rangoHora(h.getHora()));
            registroHorario.setLunes(buscarRestriccion(DayOfWeek.LUNES, h.getHora())); 
            registroHorario.setMartes(buscarRestriccion(DayOfWeek.MARTES, h.getHora()));
            registroHorario.setMiercoles(buscarRestriccion(DayOfWeek.MIERCOLES, h.getHora()));
            registroHorario.setJueves(buscarRestriccion(DayOfWeek.JUEVES, h.getHora()));
            registroHorario.setViernes(buscarRestriccion(DayOfWeek.VIERNES, h.getHora()));
            registroHorario.setSabado(buscarRestriccion(DayOfWeek.SABADO, h.getHora()));
            registroHorario.setDomingo(buscarRestriccion(DayOfWeek.DOMINGO, h.getHora()));
            arraylistregistroHorario.add(registroHorario);   
            }
        }
    }

    private DiaHorario buscarRestriccion(DayOfWeek dia, String hora) {
        if (this.getHorarioRes() != null) {
            for (CmtHorarioRestriccionMgl hr : this.getHorarioRes()) {
                if (dia.ordinal() >= hr.getDiaInicio().ordinal() && dia.ordinal() <= hr.getDiaFin().ordinal()) {
                    if (Integer.parseInt(hora.replace(":", "")) >= Integer.parseInt(hr.getHoraInicio().replace(":", ""))
                            && Integer.parseInt(hora.replace(":", "")) < Integer.parseInt(hr.getHoraFin().replace(":", ""))) {
                        return new DiaHorario(hr, hr.getTipoRestriccion(), hr.getRazonRestriccion());
                    }
                }

            }
        }
        return new DiaHorario();
    }

    private String rangoHora(String hora) {
        Integer horas = Integer.parseInt(hora.substring(0, hora.indexOf(":")).replace(":", ""));
        Integer minutos = Integer.parseInt(hora.substring(hora.indexOf(":")).replace(":", ""));
        if (minutos == 0) {
            if (horas >= 10) {
                hora = horas.toString() + ":00" + " - " + horas.toString() + ":30";
            } else {
                hora = "0" + horas.toString() + ":00" + " - 0"+ horas.toString() + ":30";
            }
        } else {
            if (horas >= 10) {
                hora = hora + " - " + String.valueOf(horas + 1) + ":00";
            } else {
                hora = "0" + hora + " - " + ((horas.equals(new Integer("9")))?"":"0")+ String.valueOf(horas + 1) + ":00";
            }
        }
        return hora;
    }

    //---
    public void addNewHorarioRestriccion() {
        String strValidar = "";
        horarioRestriccionCmEdit = getHorarioRestriccionCmEdit();
        if (horarioRestriccionCmEdit != null && this.getHorarioRes() != null) {
            strValidar = getValidarInsertHorario(horarioRestriccionCmEdit);
        }

        if (strValidar.isEmpty()) {
            boolean validar = false;
            List<CmtHorarioRestriccionMgl> lista = (List<CmtHorarioRestriccionMgl>) this.getStateHelper().get("horarioRestriccion");
            if (lista != null && lista.isEmpty()) {
                validar = true;
            }
            if (validar) {
                List<CmtHorarioRestriccionMgl> listaHorarioAux = new ArrayList<CmtHorarioRestriccionMgl>();
                listaHorarioAux.add(horarioRestriccionCmEdit);
                this.getStateHelper().put("horarioRestriccion", listaHorarioAux);
            } else {
                this.getStateHelper().add("horarioRestriccion", horarioRestriccionCmEdit);
            }
            this.getAttributes().put("horarioRestriccion", this.getStateHelper().get("horarioRestriccion"));
             setHorarioRestriccionCmEdit(new CmtHorarioRestriccionMgl());
            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, strValidar, strValidar));
        }
        session.setAttribute("ComponenteHorario", (ArrayList<CmtHorarioRestriccionMgl>)this.getStateHelper().get("horarioRestriccion"));
    }

    public void eliminarHorario(CmtHorarioRestriccionMgl horarioRestriccionCm) {
        this.getStateHelper().remove("horarioRestriccion", horarioRestriccionCm);
        if (getStateHelper().get("horarioRestriccion") == null) {
            this.getAttributes().put("horarioRestriccion", new ArrayList<CmtHorarioRestriccionMgl>());
            setHorarioRes((ArrayList<CmtHorarioRestriccionMgl>)this.getAttributes().get("horarioRestriccion"));            
        } else {
            this.getAttributes().put("horarioRestriccion", this.getStateHelper().get("horarioRestriccion"));            
        }
        session.setAttribute("ComponenteHorario", (ArrayList<CmtHorarioRestriccionMgl>)this.getStateHelper().get("horarioRestriccion"));
    }

    public String cancelar() {
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="Validators">
    private String getValidarInsertHorario(CmtHorarioRestriccionMgl horario) {
        if (getInsertExistente(horario)) {
            return "No puede agregar una marca de horario de tipo " + horario.getTipoRestriccion() + " sobre un rango de horas y días ya marcado previamente";
        } else if (horario.getDiaInicio().ordinal() > horario.getDiaFin().ordinal()) {
            return "El Día Inicio debe ser menor o igual al Día Fin";
        } else if (horario.getTipoRestriccion() == null) {
            return "Debe seleccionar el tipo de restricción";
        } else if (Integer.parseInt(horario.getHoraInicio()) >= Integer.parseInt(horario.getHoraFin())) {
            return "La Hora Inicio no puede ser igual a la Hora Fin y la la Hora Inicio debe ser menor a la Hora Fin";
        } else if (horario.getTipoRestriccion().equals(TipoRestriccion.RESTRICCION) && horario.getRazonRestriccion().isEmpty()) {
            return "Debe diligenciar una Razón de Restricción";
        } else {
            return "";
        }
    }

    private boolean getInsertExistente(CmtHorarioRestriccionMgl horario) {
        for (CmtHorarioRestriccionMgl hr : this.getHorarioRes()) {
            if (horario.getDiaInicio().ordinal() >= hr.getDiaInicio().ordinal() && horario.getDiaFin().ordinal() <= hr.getDiaFin().ordinal()
                    || (horario.getDiaInicio().ordinal() >= hr.getDiaInicio().ordinal() && horario.getDiaInicio().ordinal() <= hr.getDiaFin().ordinal())
                    || (horario.getDiaFin().ordinal() >= hr.getDiaInicio().ordinal() && horario.getDiaFin().ordinal() <= hr.getDiaFin().ordinal())) {
                Integer horaInicio = Integer.parseInt(horario.getHoraInicio().replace(":", ""));
                Integer horaFin = Integer.parseInt(horario.getHoraFin().replace(":", ""));
                if (horaInicio >= Integer.parseInt(hr.getHoraInicio().replace(":", ""))
                        && horaFin <= Integer.parseInt(hr.getHoraFin().replace(":", ""))) {
                    return true;
                } else if (horaInicio > Integer.parseInt(hr.getHoraInicio().replace(":", ""))
                        && horaInicio < Integer.parseInt(hr.getHoraFin().replace(":", ""))) {
                    return true;
                } else if (horaFin > Integer.parseInt(hr.getHoraInicio().replace(":", ""))
                        && horaFin < Integer.parseInt(hr.getHoraFin().replace(":", ""))) {
                    return true;
                }
            }
        }
        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public ArrayList<RegistroHorario> getArraylistregistroHorario() {
        pintarDatos();
        return arraylistregistroHorario;
    }

    public void setArraylistregistroHorario(ArrayList<RegistroHorario> arraylistregistroHorario) {
        this.arraylistregistroHorario = arraylistregistroHorario;
    }

    public ArrayList<Horas> getLsHoras() {
        return lsHoras;
    }

    public DayOfWeek[] getDayOfWeekValues() {
        return DayOfWeek.values();
    }

    public CmtHorarioRestriccionMgl getHorarioRestriccionCmEdit() {
        horarioRestriccionCmEdit = (CmtHorarioRestriccionMgl) getStateHelper().get(HORARIO_RESTRICCION_KEY);
        if (horarioRestriccionCmEdit == null) {
            getStateHelper().put(HORARIO_RESTRICCION_KEY, new CmtHorarioRestriccionMgl());
        }
        return horarioRestriccionCmEdit;
    }

    public void setHorarioRestriccionCmEdit(CmtHorarioRestriccionMgl horarioRestriccionCm) {
        getStateHelper().put(HORARIO_RESTRICCION_KEY, horarioRestriccionCm);
    }

    public TipoRestriccion[] getTipoRestriccion() {
        return TipoRestriccion.values();
    }

    public ArrayList<CmtHorarioRestriccionMgl> getHorarioRes() {
        return (ArrayList<CmtHorarioRestriccionMgl>) getStateHelper().get("horarioRestriccion");

    }

    public void setHorarioRes(ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion) {
        getStateHelper().put("horarioRestriccion", horarioRestriccion);
        
    }
    //</editor-fold>
}
