package objects;

import java.util.List;

public class HiddenDetails {

    private String findFlightsX;
    private String findFlightsY;
    private String[] cgifields;
    private String reserveFlightsX;
    private String reserveFlightsY;
    private String JSFormSubmit;
    private String buyFlightsX;
    private String buyFlightsY;


    public HiddenDetails(){
        super();
    }


    public HiddenDetails(String findFlightsX, String findFlightsY, String[] cgifields,
                         String reserveFlightsX, String reserveFlightsY, String JSFormSubmit,
                         String buyFlightsX, String buyFlightsY) {

        this.findFlightsX = findFlightsX;
        this.findFlightsY = findFlightsY;
        this.cgifields = cgifields;
        this.reserveFlightsX = reserveFlightsX;
        this.reserveFlightsY = reserveFlightsY;
        this.JSFormSubmit = JSFormSubmit;
        this.buyFlightsX = buyFlightsX;
        this.buyFlightsY = buyFlightsY;
    }

    public String getFindFlightsX() {
        return findFlightsX;
    }

    public void setFindFlightsX(String findFlightsX) {
        this.findFlightsX = findFlightsX;
    }

    public String getFindFlightsY() {
        return findFlightsY;
    }

    public void setFindFlightsY(String findFlightsY) {
        this.findFlightsY = findFlightsY;
    }

    public String[] getCgifields() {
        return cgifields;
    }

    public void setCgifields(String[] cgifields) {
        this.cgifields = cgifields;
    }

    public String getReserveFlightsX() {
        return reserveFlightsX;
    }

    public void setReserveFlightsX(String reserveFlightsX) {
        this.reserveFlightsX = reserveFlightsX;
    }

    public String getReserveFlightsY() {
        return reserveFlightsY;
    }

    public void setReserveFlightsY(String reserveFlightsY) {
        this.reserveFlightsY = reserveFlightsY;
    }

    public String getJSFormSubmit() {
        return JSFormSubmit;
    }

    public void setJSFormSubmit(String JSFormSubmit) {
        this.JSFormSubmit = JSFormSubmit;
    }

    public String getBuyFlightsX() {
        return buyFlightsX;
    }

    public void setBuyFlightsX(String buyFlightsX) {
        this.buyFlightsX = buyFlightsX;
    }

    public String getBuyFlightsY() {
        return buyFlightsY;
    }

    public void setBuyFlightsY(String buyFlightsY) {
        this.buyFlightsY = buyFlightsY;
    }
}
