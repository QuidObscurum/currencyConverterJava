package com.quid.currencyconverter.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "banksells")
public class CurrencyJPA implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fromCurrency", nullable = false, length = 3)
    private String fromCurrency;

    @Column(name = "toCurrency", nullable = false, length = 3)
    private String toCurrency;

    @Column(name = "rate", precision=19, scale=4)
    private BigDecimal rate;

    public CurrencyJPA(){}

    public CurrencyJPA(Long id, String fromCurr, String toCurr, BigDecimal rate){
        this.setId(id);
        this.setFromCurrency(fromCurr);
        this.setToCurrency(toCurr);
        this.setRate(rate);
    }

    public CurrencyJPA(String fromCurr, String toCurr, BigDecimal rate){
        this.setId(-1L);
        this.setFromCurrency(fromCurr);
        this.setToCurrency(toCurr);
        this.setRate(rate);
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setFromCurrency(String fromCurr) {
        this.fromCurrency = fromCurr;
    }

    public void setToCurrency(String toCurr) {
        this.toCurrency = toCurr;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CurrencyJPA{" +
                "id=" + id +
                ", fromCurrency='" + fromCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                ", rate=" + rate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyJPA)) return false;
        CurrencyJPA that = (CurrencyJPA) o;
        return getId().equals(that.getId()) &&
                getFromCurrency().equals(that.getFromCurrency()) &&
                getToCurrency().equals(that.getToCurrency()) &&
                getRate().equals(that.getRate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFromCurrency(), getToCurrency(), getRate());
    }
}
