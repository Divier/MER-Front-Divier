package co.com.claro.mgl.utils;

/**
 * Enumerador con las constantes de men&uacte; existentes en el aplicativo.
 *
 * @author wgavidia
 * @version 2017/09/20
 */
public enum MenuEnum {
    //MENU CUENTA MATRIX
    ID_MENU_CM_TB_BASICAS("menuCmTbBasicas", "rolMenuTbBasicas"),
    ID_MENU_CM_CRUD_TB_BASICAS("menuCmCrudTbBasicas", "rolMenuCrudTbBasicas"),
    ID_MENU_CM_MTO_TB_BASICAS("menuCmMtoTbBasicas", "rolMenuMtoTbTablabBasicas"),
    ID_MENU_CM_TB_COMPETENCIAS("menuCmTbCompetencias", "rolMenuTbCompetencias"),
    ID_MENU_CM_ESTADO_INTERNO_GA("menuCmEstadoInternoGa", "rolMenuEstadoInternoGa"),
    ID_MENU_CM_RELACION_CM_GA("menuCmRelacionCmGa", "rolMenuRelacionCmGa"),
    ID_MENU_CM_COMPANIA_ADMIN("menuCmCompaniaAdmin", "rolMenuCompaniaAdmin"),
    ID_MENU_CM_COMPANIA_CONSTRUCTORA("menuCmCompaniaConstructora", "rolMenuCompaniaConstructora"),
    ID_MENU_CM_COMPANIA_ASCENSORES("menuCmCompaniaAscensores", "rolMenuCompaniaAscensonres"),
    ID_MENU_CM_GESTION_OT("menuCmGestionOt", "rolMenuGestionOt"),
    ID_MENU_CM_CREA_OT_VT("menuCmCreaOtVt", "rolMenuCreaOtVt"),
    ID_MENU_CM_ADMIN_BUSCA_CM("menuCmAdminBuscaCm", "rolMenuAdminBuscaCm"),
    ID_MENU_CM_CREA_SOL_CREACION_CM("menuCmCreaSolCreacionCm", "rolMenuCreaSolCreacionCm"),
    ID_MENU_CM_CREA_SOL_MODIFICACION_CM("menuCmCreaSolModificacionCm", "rolMenuCreaSolModificacionCm"),
    ID_MENU_CM_CREA_SOL_VT("menuCmCreaSolVt", "rolMenuCreaSolVt"),
    ID_MENU_CM_CREA_SOL_CREACION_HHPP_CM("menuCmCreaSolCreacionHhppCm", "rolMenuCreaSolCreacionHhppCm"),
    ID_MENU_CM_CREA_SOL_MODOFICACION_HHPP_CM("menuCmCreaSolModificacionHhppCm", "rolMenuCreaSolModificacionHhppCm"),
    ID_MENU_CM_GESTION_SOLICITUD("menuCmGestionSolicitud", "rolMenuGestionSolicitud"),
    ID_MENU_CM_VIABILIDAD_HHPP("menuCmViabilidadHhpp", "rolMenuViabilidadHhpp"),
    ID_MENU_CM_VIABILIDAD_ESTRATO("menuCmViabilidadEstrato", "rolMenuViabilidadEstrato"),
    ID_MENU_CM_REPORTES_SOLICITUD("menuCmReporteSolicitud", "rolMenuCmReporteSolicitud"),
    ID_MENU_CM_REPORTES_PENETRACION("menuCmReportePenetracion", "rolMenuCmReportePenetracion"),
    ID_DETALLE_CM_PESTANA_HHPP("detalleCmPtHhpp", "rolDetalleCmPtHhpp"),
    ID_DETALLE_CM_PESTANA_PENETRACION("detalleCmPtPenetracion", "rolDetalleCmPtPenetracion"),
    ID_DETALLE_CM_PESTANA_INVENTARIO("detalleCmPtInventario", "rolDetalleCmPtInventario"),
    ID_DETALLE_CM_PESTANA_INFOTECNICA("detalleCmPtInfoTecnica", "rolDetalleCmPtInfoTecnica"),
    ID_DETALLE_CM_PESTANA_PROYECTOS("detalleCmPtProyectos", "rolDetalleCmProyectos"),
    ID_DETALLE_CM_PESTANA_BITACORA("detalleCmPtBitacora", "rolDetalleCmPtBitacora"),
    ID_DETALLE_CM_PESTANA_CASOS("detalleCmPtCasos", "rolDetalleCmPtCasos"),
    ID_DETALLE_CM_PESTANA_SEGURIDAD("detalleCmPtSeguridad", "rolDetalleCmPtSeguridad"),
    ID_DETALLE_CM_PESTANA_COMPANIAS("detalleCmPtCompania", "rolDetalleCmPtCompania"),
    ID_DETALLE_CM_PESTANA_OT("detalleCmPtOt", "rolDetalleCmPtOt"),
    ID_DETALLE_CM_PESTANA_NOTAS("detalleCmPtNotas", "rolDetalleCmPtNotas"),
    ID_DETALLE_CM_PESTANA_HORARIO("detalleCmPtHorario", "rolDetalleCmPtHorario"),
    ID_DETALLE_CM_PESTANA_COMPETENCIAS("detalleCmPtCompetencias", "rolDetalleCmPtCompetencias"),
    ID_DETALLE_CM_PESTANA_DIRECCIONES("detalleCmPtDirecciones", "rolDetalleCmPtDirecciones"),
    ID_DETALLE_CM_PESTANA_BL("detalleCmPtBlackList", "rolDetalleCmPtBlackList"),
    ID_ESTADOS_SLA_TIPO_OT("menuEstadoSla", "rolMenuEstadoSlaTipoOt"),
    ID_ESTADOS_FLUJOS_OT("menuEstadoFlujos", "rolMenuEstadosxFlujoOt"),
    ID_ESTADOS_SLA_SOLICITUD("menuEstadoSlaSolicitud", "rolMenuSlaSolicitudOt"),
    ID_REGLA_FLUJO_OT("menuReglaFlujoOt", "rolMenuReglaFlujoOt"),
    ID_ESTADO_TECNOLOGIAS("menuEstadosxTecnologias", "rolMenuEstadoTecnologia"),
    ID_MENU_CM_NODO("menuCmNodo", "rolMenuCmNodo"),
    ID_MENU_CM_ACT_EST_COMERCIAL("menuCmActEstCom", "rolMenuCmActEstCom"),
    ID_MENU_CM_ESTADO_SOLICITD("menuCmEstadoSolicitudes", "rolMenuCmEstadoSolicitudes"),
    ID_MENU_ELIMINAR_CM("menuElimarCM", "rolMenuElimarCM"),
    ID_MENU_CM_GENERAR_OT_EJECUCION("menuCmGenerarOtEjecucion", "rolMenuCmGenerarOtEjecucion"),
    ID_MENU_CM_REGIONAL("menuCmRegional", "rolMenuCmRegional"),
    ID_MENU_CM_PLANTA("menuCmPlanta", "rolMenuCmPlanta"),
    ID_MENU_CM_COMUNIDAD("menuCmComunidad", "rolMenuCmComunidad"),
    ID_MENU_MTTO_TIPO_TABLA("menuMttoTipoTabla", "rolMenuMttoTipoTabla"),
    ID_MENU_MTTO_TABLAS("menuMttoTablas", "rolMenuMttoTablas"),
    ID_MENU_CM_MODIFICACIONES_MASIVA("menuCmModificacionMasiva","rolMenuCmModificacionMasiva"),
    ID_MENU_CM_MODIFICACIONES_CARGUE("menuCmCargueMasivo","rolMenuCmCargueMasivo"),
    ID_MENU_CM_GESTION_PARAMETROS("menuCmGestionParametros","rolMenuGestionParametros"),
    ID_MENU_CM_ARRENDATARIO("menuCmArrendatario", "rolMenuCmArrendatario"),
    ID_MENU_CM_SLA_EJECUCION("menuCmSlaEjecucion", "rolMenuCmSLAEjecucion"),
    ID_MENU_CM_HOMOLACION_RAZONES("menuCmHomologacionCodigos", "rolMenuCmHomologacionCodigos"),
    ID_MENU_CM_MARCACIONES_HHPP_VT("menuMarcacionHHPP","rolMenuMarcacionesHHPPVirtual"),
    ID_MENU_CARGUE_MASIVO_CCMM("menuCargueMasivo", "rolMenuCargueMasivo"),
    ID_MENU_ORDENES_TRABAJO_CCMM("menuOrdenesTrabajo", "rolMenuOrdenesTrabajo"),
    ID_MENU_ADMINISTRACION("menuAdministracion", "rolMenuAdministracion"),
    ID_MENU_MANTENIMIENTO_TABLAS("menuMantenimientoTablas", "rolMenuMantenimientoTablas"),
    ID_MENU_PARAMETRIZACION_PROVEEDORES("menuParametrizacionProveedores", "rolMenuParametrizacionProveedores"),
    ID_MENU_VIABILIDAD("menuViablilidad", "rolMenuViablilidad"),
    ID_MENU_MATRIZ_VIABILIDAD("menuMatrizViablilidad", "rolMenuMatrizViablilidad"),
    ID_MENU_ESTADOS_COMBINADOS("menuEstadosCombinados", "rolMenuEstadosCombinados"),
    ///MENU HHPP
    ID_MENU_HOME_PASSED("menuHomePassed", "rolMenuHomePassed"),
    ID_MENU_HHPP_ORDENES_TRABAJO("menuOrdenesTrabajoHHPP", "rolMenuOrdenesTrabajoHHPP"),
    //SOLICITAR
    ID_MENU_VISITA_TECNICA_SOL_CREAR_SOLICITUD("menuVtSolCrearSolicitud", "rolMenuVisitaTecnicaSolCrearSolicitud"),
    ID_MENU_VISITA_TECNICA_SOL_EDIFICIO_CONJUNTO("menuVtSolEdificioConjunto", "rolMenuVisitaTecnicaSolEdificioConjunto"),
    ID_MENU_VISITA_TECNICA_SOL_REPLANTEAMIENTO("menuVtSolReplanteamiento", "rolMenuVisitaTecnicaSolReplanteamiento"),
    ID_MENU_VISITA_TECNICA_SOL_EN_CASA("menuVtSolEnCasa", "rolMenuVisitaTecnicaSolEnCasa"),
    ID_MENU_VISITA_TECNICA_SOL_CREACION_CM("menuVtSolCreacionCuentaMatriz", "rolMenuVtSolCreacionCuentaMatriz"),
    ID_MENU_VISITA_TECNICA_SOL_CREACION_HHPP_UNI("menuVtSolCreacionHHPPUniDireccional", "rolMenuVtSolCreacionHHPPUnidireccional"),
    ID_MENU_VISITA_TECNICA_SOL_CREACION_HHPP_EN_CM("menuVtSolCreacionHHPPEnCuentaMatriz", "rolMenuVtSolCreacionHHPPEnCuentaMatriz"),
    ID_MENU_VISITA_TECNICA_SOL_CAMBIO_ESTRATO("menuVtSolCambioEstrato", "rolMenuVtSolCambioEstrato"),
    ID_MENU_VISITA_TECNICA_SOL_MOD_ELIM_CM("menuVtSolModificarEliminarCuentaMatriz", "rolMenuVtSolModificarEliminarCuentaMatriz"),
    ID_MENU_VISITA_TECNICA_SOL_MOD_HHPP("menuVtSolModificarHHPP", "rolMenuVtSolModificarHHPP"),
    ID_MENU_VISITA_TECNICA_SOL_VERIFICAR_CASA("menuVtSolVerificarCasa", "rolMenuVtSolVerificarCasa"),
    ID_MENU_VISITA_TECNICA_SOL_VIABILIDAD_INTERNET("menuVtSolViabilidadInternet", "rolMenuVtSolViabilidadInternet"),
    //GESTIONAR
    ID_MENU_VISITA_TECNICA_GESTION_SOLICITUD("menuVtGestionSolicitud", "rolMenuVtGestionSolicitud"),
    ID_MENU_VISITA_TECNICA_GESTION_CAMBIO_ESTRATO("menuVtGestionCambiosEstrato", "rolMenuVtGestionCambiosEstrato"),
    ID_MENU_VISITA_TECNICA_GESTION_EDIFICIO_CONJUNTO("menuVtGestionEdificioConjunto", "rolMenuVtGestionEdificioConjunto"),
    ID_MENU_VISITA_TECNICA_GESTION_REPLANTEAMIENTO("menuVtGestionReplanteamiento", "rolMenuVtGestionReplanteamiento"),
    ID_MENU_VISITA_TECNICA_GESTION_EN_CASA("menuVtGestionEnCasa", "rolMenuVtGestionEnCasa"),
    ID_MENU_VISITA_TECNICA_GESTION_CREACION_CM("menuVtGestionCreacionCuentaMatriz", "rolMenuVtGestionCreacionCuentaMatriz"),
    ID_MENU_VISITA_TECNICA_GESTION_CREACION_HHPP_UNI("menuVtGestionCreacionHHPPUni", "rolMenuVtGestionCreacionHHPPUni"),
    ID_MENU_VISITA_TECNICA_GESTION_CREACION_HHPP_EN_CM("menuVtGestionCreacionHHPPCuentaMatriz", "rolMenuVtGestionCreacionHHPPCuentaMatriz"),
    ID_MENU_VISITA_TECNICA_GESTION_MOD_ELIM_CM("menuVtGestionModificarEliminarCuentaMatriz", "rolMenuVtGestionModificarEliminarCuentaMatriz"),
    ID_MENU_VISITA_TECNICA_GESTION_MOD_HHPP("menuVtGestionModificarHHPP", "rolMenuVtGestionModificarHHPP"),
    ID_MENU_VISITA_TECNICA_GESTION_VERIFICACION_CASAS("menuVtGestionVerificacionCasas", "rolMenuVtGestionVerificacionCasas"),
    ID_MENU_VISITA_TECNICA_GESTION_VIABILIDAD_INTERNET("menuVtGestionViabilidadInternet", "rolMenuVtGestionViabilidadInternet"),
    ////
    ID_MENU_VISITA_TECNICA_VETO_NODOS("menuVtVetoNodos", "rolMenuVtVetoNodos"),
    ID_MENU_BUSQUEDA_HHPP("menuHHPPBusquedaHHPP","rolMenuHHPPBusquedaHHPP"),
    ID_MENU_VISITA_TECNICA_MODELO_HHPP("menuVtModeloHHPP", "rolMenuVtModeloHHPP"),
    ID_MENU_VISITA_TECNICA_ESTADO_SOLICITUD("menuVtEstadoSolicitud", "rolMenuVtEstadoSolicitud"),
    ID_MENU_VISITA_TECNICA_ADMINISTRADOR("menuVtAdministrador", "rolMenuVtAdministrador"),
    ID_MENU_VISITA_TECNICA_RESPORTE_SOLICITUDES("menuVtReporteSolicitudes", "rolMenuVtReporteSolicitudes"),
    ID_MENU_VISITA_TECNICA_CIERRE_MASIVO("menuVtCierreMasivo", "rolMenuVtCierreMasivo"),
    //ADMINISTRADOR DE DIRECCIONES
    ID_MENU_VISITA_TECNICA_ADMIN_NODO("menuVtAdministradorNodo", "rolMenuVtAdministradorNodo"),
    ID_MENU_VISITA_TECNICA_ADMIN_HHPP("menuVtAdministradorHHPP", "rolMenuVtAdministradorHHPP"),
    ID_MENU_VISITA_TECNICA_ADMIN_DIRECCIONES("menuVtAdministradorDirecciones", "rolMenuVtAdministradorDirecciones"),
    ID_MENU_ADMINISTRADOR_DIRECCIONES("menuAdministradorDirecciones", "rolMenuAdministradorDirecciones"),
    ID_MENU_VISITA_TECNICA_ADMIN_CIUDADES("menuVtAdministradorCiudades", "rolMenuVtAdministradorCiudades"),
    //OPERACIONES SOBRE HHPP
    ID_MENU_VISITA_TECNICA_OPERACIONES_CAMBIO_ESTRATO_MASIVO("menuVtOperacionesCambioEstratoMasivo", "rolMenuVtOperacionesCambioEstratoMasivo"),
    ID_MENU_VISITA_TECNICA_OPERACIONES_ELIMINAR_MASIVO("menuVtOperacionesEliminarMasivoHHPP", "rolMenuVtOperacionesEliminarMasivoHHPP"),
    //ORDENES DE TRABAJO
    ID_MENU_ORDENES_TRABAJO_HHPP("menuVtAdministradorOtHhpp", "rolMenuVtOrdenesTrabajoHhpp"),
    ID_MENU_VISITA_TECNICA_OPERACIONES_CREAR_REPORTE ("menuVtOperacionesCrearReporte","rolMenuVtOperacionesCrearReporte"),
    ID_MENU_VISITA_TECNICA_OPERACIONES_CARGUE_MASIVO ("menuVtOperacionesCargueMasivo","rolMenuVtOperacionesCargueMasivo"),
    ID_MENU_VISITA_TECNICA_OPERACIONES_INHABILITAR_MASIVO ("menuVtOperacionesInhabilitarMasivo","rolMenuVtOperacionesInhabilitarMasivo"),
    //FICHAS Y PREFICHAS
    ID_MENU_FICHAS("menuFichas", "rolMenuFichas "),
    ID_MENU_GENERAR_PREFICHA("menuVtGenerarPreficha","rolMenuVtGenerarPreficha"),
    ID_MENU_VALIDAR_PREFICHA("menuVtValidarPreficha","rolMenuVtValidarPreficha"),
    ID_MENU_CREAR_PREFICHA("menuVtCrearPreficha","rolMenuVtCrearPreficha"),
    ID_MENU_CARGUE_FICHAS("menuVtCargueFichas","rolMenuVtCargueFichas"),
    ID_MENU_GENERAR_FICHA("menuVtGenerarFicha","rolMenuVtGenerarFicha"),
    ID_MENU_VALIDAR_FICHAS("menuVtValidarFicha","rolMenuVtValidarFicha"),
    ID_MENU_ACTUALZIAR_FICHA("menuVtActualizarFicha","rolMenuVtActualizarFicha"),
    ID_MENU_LOG_ACTUALIZAR_NAP("menuVtLogActualizarNap","rolMenuVtLogActualizarNap"),
    ID_MENU_HHPP_GESTION_OT("menuHhppGestionOt", "rolMenuHhppGestionOt"),
    //MARCAR DIR FRAUDES
    ID_MENU_MARCAR_DIRECCION_FRAUDULENTA("menuFraudes", "rolMenuFraudes"),
    ID_MENU_MARCAR_DIRECCION_FRAUDULENTA_UNO_A_UNO("menuFraudesMarcaUnoUno", "rolMenuFraudesMarcaUnoUno"),
    ID_MENU_DESMARCAR_DIRECCION_FRAUDULENTA_UNO_A_UNO("menuFraudesDesmarcaUnoUno", "rolMenuFraudesDesmarcaUnoUno"),
    //DESCARGUE DE ARCHIVOS DF
    ID_MENU_DESCARGUE_MARCADO_DF("menuDescargueMar", "rolMenuDescargueMar"),
    ID_MENU_DESCARGUE_DESMARCADO_DF("menuDescargueDes", "rolMenuDescargueDes"),
    //CARGUE MARCA DIR FRAUDES
    ID_MENU_CARGUE_MARCADO_DF("menuCargueMar", "rolMenuCargueMar"),
    ID_MENU_CARGUE_DESMARCADO_DF("menuCargueDes", "rolMenuCargueDes"),
    //MENU REPORTES
    ID_MENU_REPORTE_OT_CM("menureportOtCm","rolOtCcmm"),
    ID_MENU_REPORTE_HISTORICO_OT_CM("menureportHistOtCM","rolHistOtCm"),
    ID_MENU_REPORTE_OT_DIR("menureportOtDir","rolOtDir"),
    ID_MENU_REPORTE_HISTORICO_OT_DIR("menureportHistOtDir","rolHistOtDir"),
    //MENU SUB TIPOS DE ORDENES OT CCMM VT
    ID_MENU_SUBTIPOS_VT_CM_TEC("menuSubtiposOtCmVt", "rolMenuSubtipoOtCmVt"),
    ID_MENU_MODELO_OVERVIEW("menuVtModeloOverview", "rolMenuVtModeloOverview"),
    ID_MENU_ADM_PERFIL_FACT("menuAdmPerfilFact", "rolMenuAdmPerfilFact"),
    ID_MENU_REPORTE_FACT("menuReporteFact", "rolMenuReporteFact"),
    //MENU NODOS A CUADRANTES
    ID_MENU_OP_NOD_CUA_GES_SOL("menuVtNodoCuadrante", "rolMenuVtNodoCuadrante");

    private final String descripcion;
    private final String validador;

    /**
     * Constructor del enumerador
     *
     * @param descripcion String con la descripci&oacute;n del men&uacute;
     * @param validador String con el nombre del booleano que valida la
     * visualizaci&oacue;n del men&uacute;
     */
    private MenuEnum(String descripcion, String validador) {
        this.descripcion = descripcion;
        this.validador = validador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getValidador() {
        return validador;
    }
}
