package com.stockprice.stock_trading_server.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "stock_symbol", unique = true, nullable = false)
    private String stockSymbol;
    private double price;

    public Stock() {
    }

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public Stock(long id, String stockSymbol, LocalDateTime lastUpdated,int price) {
        this.id = id;
        this.stockSymbol = stockSymbol;
        this.lastUpdated = lastUpdated;
        this.price=price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
