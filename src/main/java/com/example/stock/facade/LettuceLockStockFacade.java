package com.example.stock.facade;

import com.example.stock.repository.RedisRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Service;

@Service
public class LettuceLockStockFacade {

    private final RedisRepository repository;
    private final StockService stockService;

    public LettuceLockStockFacade(RedisRepository repository, StockService stockService) {
        this.repository = repository;
        this.stockService = stockService;
    }

    public void decrease(Long key, Long quantity) throws InterruptedException {
        while (!repository.lock(key)) {
            Thread.sleep(100);
        }
        try {
            stockService.decrease(key, quantity);
        } finally {
            repository.unlock(key);
        }
    }
}
