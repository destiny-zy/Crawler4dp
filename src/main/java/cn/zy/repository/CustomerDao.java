package cn.zy.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.zy.entity.Customer;

public interface CustomerDao extends PagingAndSortingRepository<Customer, Long> {

}
