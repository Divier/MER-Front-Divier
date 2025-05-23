package co.com.claro.mer.utils.pagination;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO para apoyar el proceso de paginación de información
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/09/23
 */
@Getter
@Setter
@ToString
public class PaginationDto<T> {

    private int totalPages;
    private int actualPageShow;
    private int selectedPage;
    private String descriptionPages;
    private List<Integer> pageNumberSelector;
    private List<T> paginatedList;
    private List<T>  completeList;

    public PaginationDto() {
        this.totalPages = 0;
        this.actualPageShow = 0;
        this.selectedPage = 0;
        this.descriptionPages = "";
        this.pageNumberSelector = new ArrayList<>();
        this.paginatedList = new ArrayList<>();
        this.completeList = new ArrayList<>();
    }
}
