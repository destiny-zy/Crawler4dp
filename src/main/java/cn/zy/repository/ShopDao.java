package cn.zy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.zy.entity.Shop;

public interface ShopDao extends PagingAndSortingRepository<Shop, Long> {
	@Query("select shopid from Shop")
	List<Integer> findAllShopid();

	Shop findByShopid(Integer data);
}
