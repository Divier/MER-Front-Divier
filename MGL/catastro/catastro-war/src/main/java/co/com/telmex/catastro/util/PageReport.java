package co.com.telmex.catastro.util;

/**
 * Clase PageReport
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class PageReport {

    private static String PAGE = "page";
    private static String QUANTITY = "quantity";
    private static String INTERVAL = "interval";
    private static String URL = "url";
    private static String INDEX = "index";
    private static String PREVIOUS = "Anterior";
    private static String NEXT = "Siguiente";

    /**
     * 
     */
    public PageReport() {
    }

    /**
     * 
     * @param page
     * @param quantity
     * @param interval
     * @param url
     * @param index
     * @param previous
     * @param next
     */
    public PageReport(String page, String quantity, String interval, String url, String index, String previous, String next) {
        PAGE = page;
        QUANTITY = quantity;
        INTERVAL = interval;
        URL = url;
        INDEX = index;
        PREVIOUS = previous;
        NEXT = next;
    }

    /**
     * 
     * @param prmPage : numero de pagina
     * @param prmQuantity : cantidad de registros
     * @param prmInterval : cantidad de registros por pagina
     * @param prmUrl : url del propio componente
     * @param union 
     * @return : retorna tag html de paginado
     * @throws NumberFormatException 
     */
    public String create(int prmPage, int prmQuantity, int prmInterval, String prmUrl, String union) throws NumberFormatException {
        String hyper = "", before = "", after = "", link = "", url = "";
        int index = 0, pg = 0, rst = 0, page = 0, quantity = 0, interval = 0;
        page = prmPage;
        quantity = prmQuantity;
        interval = prmInterval;
        url = prmUrl;



        if (quantity > interval) {
            pg = (int) (quantity / interval);
            rst = quantity % interval;
        } else {
            pg = 1;
        }

        if (rst > 0) {
            pg++;
        }

        if (pg == 1) {
            hyper = "1&nbsp;";
        } else {
            if (page == 1) {
                before = PREVIOUS + "&nbsp;";
            } else {
                index = ((page * interval) - (interval * 2)) + 1;
                before = "<a href=\"" + url + union + INDEX + "=" + index + "&" + PAGE + "=" + (page - 1) + "\">" + PREVIOUS + "</a>&nbsp;";
            }
            if (page == pg) {
                after = NEXT + "&nbsp;";
            } else {
                index = (page * interval) + 1;

                after = "<a href=\"" + url + union + INDEX + "=" + index + "&" + PAGE + "=" + (page + 1) + "\">" + NEXT + "</a>&nbsp;";
            }

            index = 1;
            for (int h = 1; h <= pg; h++) {
                if (h == page) {
                    link += h + "&nbsp;";
                } else {
                    link += "<a href=\"" + url + union + INDEX + "=" + index + "&" + PAGE + "=" + h + "\">" + h + "</a>&nbsp;";
                }
                index += interval;
            }
            hyper = before + link + after;
        }
        return hyper;
    }
}