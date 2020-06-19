package base.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "banksells")
public class CurrencyDataSet implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO?
    private long id;

    @Column(name = "fromCurrency", nullable = false, length = 3)
    private String fromCurrency;

    @Column(name = "toCurrency", nullable = false, length = 3)
    private String toCurrency;

    @Column(name = "rate")
    private double rate;

    public CurrencyDataSet(){}

    public CurrencyDataSet(long id, String fromCurr, String toCurr, double rate){
        this.setId(id);
        this.setFromCurrency(fromCurr);
        this.setToCurrency(toCurr);
        this.setRate(rate);
    }

    public CurrencyDataSet(String fromCurr, String toCurr, double rate){
        this.setId(-1);
        this.setFromCurrency(fromCurr);
        this.setToCurrency(toCurr);
        this.setRate(rate);
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setFromCurrency(String fromCurr) {
        this.fromCurrency = fromCurr;
    }

    public void setToCurrency(String toCurr) {
        this.toCurrency = toCurr;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CurrencyDataSet{ " +
                "id=" + id +
                ", fromCurrency='" + fromCurrency +
                "', toCurrency='" + toCurrency +
                "', rate=" + rate + " }";
    }
}
