package com.stockprice.stock_trading_client;

import com.demo.stocktrading.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockClientService {
    @GrpcClient("stockService")
//    private com.demo.stocktrading.StockTradingServiceGrpc.StockTradingServiceBlockingStub stockTradingServiceBlockingStub;
    private StockTradingServiceGrpc.StockTradingServiceStub stockTradingServiceStub;

    //    public com.demo.stocktrading.StockResponse getStockPrice(String stockSymbol) {
//        com.demo.stocktrading.StockRequest request = com.demo.stocktrading.StockRequest
//                .newBuilder()
//                .setStockSymbol(stockSymbol)
//                .build();
//        return stockTradingServiceBlockingStub.getStockPrice(request);
    public void subscribeStockPrice(String symbol) {
        StockRequest stockRequest = StockRequest.newBuilder().setStockSymbol(symbol).build();
        stockTradingServiceStub.subscribeStockPrice(stockRequest, new StreamObserver<StockResponse>() {
            @Override
            public void onNext(StockResponse stockResponse) {
                System.out.println("Stock Price update: " + stockResponse.getStockSymbol() + " Price: " + stockResponse.getPrice() + " Time: " + stockResponse.getTimestamp());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("stock streaming completed");
            }
        });
    }

    public void placeBulkOrder() {
        StreamObserver<OrderSummary> responseObserver = new StreamObserver<OrderSummary>() {
            @Override
            public void onNext(OrderSummary orderSummary) {
                System.out.println("Order summary received from server");
                System.out.println("total order: " + orderSummary.getTotalOrders());
                System.out.println("Successful order: " + orderSummary.getSuccessCount());
                System.out.println("Total amount " + orderSummary.getTotalAmount());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Order summary received error from server" + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream completed. server is done sending the summary");
            }
        };
        StreamObserver<StockOrder> requestObserver = stockTradingServiceStub.bulkStockOrder(responseObserver);
        //send multiple stream stub order message/request

        try {
            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("1")
                    .setStockSymbol("APPL")
                    .setOrderType("BUY")
                    .setPrice(150.0)
                    .setQuantity(10)
                    .build());
            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("2")
                    .setStockSymbol("GOOGL")
                    .setOrderType("SELL")
                    .setPrice(2700.0)
                    .setQuantity(5)
                    .build());
            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("3")
                    .setStockSymbol("TSLA")
                    .setOrderType("BUY")
                    .setPrice(700.0)
                    .setQuantity(8)
                    .build());

            //done sending orders
            requestObserver.onCompleted();
        } catch (Exception e) {
            requestObserver.onError(e);
        }
    }
    public void startLiveTrading() throws InterruptedException {
        StreamObserver<StockOrder> requestObserver = stockTradingServiceStub.liveTrading(new StreamObserver<TradeStatus>() {
            @Override
            public void onNext(TradeStatus tradeStatus) {
                System.out.println("Server response "+ tradeStatus);

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error "+throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Streaming completed ");
            }
        });

        //seinding multiple requests

        for(int i=0;i<10;i++){
            StockOrder stockOrder = StockOrder.newBuilder()
                    .setOrderId("Order-"+i)
                    .setStockSymbol("TSLA")
                    .setOrderType("BUY")
                    .setPrice(700.0)
                    .setQuantity(8)
                    .build();
            requestObserver.onNext(stockOrder);
            Thread.sleep(500);

        }
        requestObserver.onCompleted();
    }
}
