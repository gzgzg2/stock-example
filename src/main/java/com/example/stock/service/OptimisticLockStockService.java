package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptimisticLockStockService {

    private final StockRepository repository;

    public OptimisticLockStockService(StockRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void decrease(Long id, Long quantity){
        Stock stock = repository.findByWithOptimistic(id);

        stock.decrease(quantity);

        repository.saveAndFlush(stock);
    }
}
