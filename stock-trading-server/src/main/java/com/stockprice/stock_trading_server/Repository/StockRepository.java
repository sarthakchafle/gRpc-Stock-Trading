package com.stockprice.stock_trading_server.Repository;

import com.stockprice.stock_trading_server.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    Stock findByStockSymbol(String stockSymbol);
}
