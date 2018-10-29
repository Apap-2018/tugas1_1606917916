package com.apap.tugas1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.ProvinsiModel;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinsiDb extends JpaRepository<ProvinsiModel, Long> {

	List<ProvinsiModel> findByNama(String nama);

}
