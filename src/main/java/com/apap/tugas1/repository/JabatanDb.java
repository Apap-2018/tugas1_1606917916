package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.JabatanModel;
import org.springframework.stereotype.Repository;

@Repository
public interface JabatanDb extends JpaRepository<JabatanModel, Long> {
	
}
