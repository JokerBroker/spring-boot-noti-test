package com.karthik.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karthik.Entity.Stock;
@Repository
public interface StockRepository extends JpaRepository<Stock, String>{
	Stock findByStock(String stk);
}
