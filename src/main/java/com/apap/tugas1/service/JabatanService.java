package com.apap.tugas1.service;


import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanModel;


public interface JabatanService {
	void addJabatan(JabatanModel jabatan);
	List<JabatanModel> getAllJabatan();
	Optional<JabatanModel> getJabatanById(Long id);
	void updateJabatan(Long jabatanId, JabatanModel jabatanBaru);
	void deleteById(Long jabatanId);
}
