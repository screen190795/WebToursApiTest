package objects;

public class CreditCard {
    private String creditCard;
    private String expDate;

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public CreditCard(String creditCard, String expDate) {
        this.creditCard = creditCard;
        this.expDate = expDate;
    }
}
