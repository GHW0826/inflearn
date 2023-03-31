package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PessimisticLockStockService {
    private StockRepository stockRepository;

    public PessimisticLockStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithPessimistickLock(id);
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
