package com.stockprice.stock_trading_client;

import com.demo.stocktrading.StockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockTradingClientApplication implements CommandLineRunner {

	@Autowired
	private StockClientService stockClientService;

	public static void main(String[] args) {
		SpringApplication.run(StockTradingClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		stockClientService.subscribeStockPrice("APPL");
////		System.out.println("GRPC client response " + stockClientService.getStockPrice("GOOGL"));
//		System.out.println(stockResponse.getStockSymbol());
//		System.out.println(stockResponse.getPrice());
//		System.out.println(stockResponse.getTimestamp());
//		stockClientService.placeBulkOrder();

		stockClientService.startLiveTrading();
	}
}
