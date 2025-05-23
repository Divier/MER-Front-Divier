package co.com.claro.mer.utils.pagination;



import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import lombok.Getter;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utilería para proceso de paginación en tablas de JSF
 *
 * @param <E>
 * @author Gildardo Mora
 * @version 1.0, 2022/09/23
 */
@ToString
public class PaginatorUtil<E> {
    private static final Logger LOGGER = LogManager.getLogger(PaginatorUtil.class);
    @Getter
    private final PaginationDto<E> data = new PaginationDto<>();
    private static final int FIRST_PAGE = 1;

    /* ---------------------------------------- */
    /**
     * Realiza las operaciones para definir los datos requeridos
     * para el proceso de paginación.
     *
     * @param page             Número de página seleccionada
     * @param maxResultsByPage Número máximo de resultados a mostrar por página
     * @param list             Lista de elementos que se va a paginar
     */
    public void pagingProcess(int page, int maxResultsByPage, List<E> list) throws ApplicationException {
        if (Objects.isNull(list)) {
            String msgError = "El valor de la lista recibida es nulo, "
                    + "no se pudo realizar proceso de paginaciÃ³n "
                    + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.warn(msgError);
            throw new ApplicationException(msgError);
        }

        data.setCompleteList(list);
        data.setTotalPages(getTotalPagesEnabled(data.getCompleteList(), maxResultsByPage));
        int pageNumber = obtainNewPageNumber(page, data.getTotalPages());
        data.setPaginatedList(listInfoByPage(pageNumber, maxResultsByPage, list));
        data.setActualPageShow(pageNumber);
        data.setSelectedPage(pageNumber);
        data.setPageNumberSelector(assignPageNumbersToSelector(data.getTotalPages()));
        data.setDescriptionPages(data.getSelectedPage() + " de " + data.getTotalPages());
    }

    /**
     * Retorna el valor 1 asociado a la primera página de resultados
     *
     * @return Retorna el valor numérico 1;
     */
    public int goFirstPage() {
        return FIRST_PAGE;
    }

    /**
     * Retorna el valor numérico que corresponde a la página previa a la seleccionada
     *
     * @return Retorna el número de la pagina anterior.
     */
    public int goPreviousPage() {
        return obtainNewPageNumber((data.getActualPageShow() - 1), data.getTotalPages());
    }

    /**
     * Retorna el valor numúrico que corresponde a la página siguiente a la seleccionada
     *
     * @return Retorna el número de la página siguiente.
     */
    public int goNextPage() {
        return obtainNewPageNumber((data.getActualPageShow() + 1), data.getTotalPages());
    }

    /**
     * Retorna el valor numérico que corresponde a la Última página a la seleccionada.
     *
     * @return Retorna el número de la Última página.
     */
    public int goLastPage() {
        return data.getTotalPages();
    }

    /**
     * Obtiene el total de páginas que se generan a partir de lista y del número
     * máximo especificado de resultados por cada página.
     *
     * @param list       {@link List<T>}  Lista con la información que se requiere paginar
     * @param maxResults Número máximo de resultados a mostrar por cada pagina.
     * @return Número de páginas que se pueden mostrar en total.
     */
    private <T> int getTotalPagesEnabled(List<T> list, int maxResults) {
        if (Objects.isNull(list)) return 0;
        int sizeList = list.size();
        return (int) Math.ceil((double) sizeList / maxResults);
    }

    /**
     * Obtiene la información correspondiente al número de página seleccionado.
     *
     * @param numPage    Numero de página a consultar
     * @param maxResults Número máximo de resultados por página.
     * @param list       Lista de resultados a paginar.
     * @return Lista de resultados correspondientes a la página.
     */
    private <T> List<T> listInfoByPage(int numPage, int maxResults, List<T> list) {
        if (Objects.isNull(list)) return Collections.emptyList();
        if (maxResults < FIRST_PAGE) return list;
        int firstResult = numPage > FIRST_PAGE ? (maxResults * (--numPage)) : 0;
        return list.stream().skip(firstResult).limit(maxResults).collect(Collectors.toList());
    }

    /**
     * Obtiene la lista de números de página que se mostraran en el selector.
     *
     * @param totalPages Número de páginas disponibles para indicar en el selector.
     * @return {@link List<Integer>}  Lista con los números de páginas disponibles a seleccionar.
     */
    private List<Integer> assignPageNumbersToSelector(int totalPages) {
        return IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
    }

    /**
     * Verifica el número de página del cual se quieren mostrar los resultados.
     *
     * @param numPage    Número de página seleccionada
     * @param totalPages Número total de páginas disponibles.
     * @return Número de página seleccionada verificado.
     */
    private int obtainNewPageNumber(int numPage, int totalPages) {
        if (numPage <= 0) return 1;
        return Math.min(numPage, totalPages);
    }
}


