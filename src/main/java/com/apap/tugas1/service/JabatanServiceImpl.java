package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDb jabatanDb;

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}

	@Override
	public List<JabatanModel> getAllJabatan() {
		return jabatanDb.findAll();
	}

	@Override
	public Optional<JabatanModel> getJabatanById(Long id) {
		return jabatanDb.findById(id);
	}

	@Override
	public void updateJabatan(Long jabatanId, JabatanModel jabatanBaru) {
		JabatanModel jabatanLama = jabatanDb.findById(jabatanId).get();
		jabatanLama.setNama(jabatanBaru.getNama());
		jabatanLama.setDeskripsi(jabatanBaru.getDeskripsi());
		jabatanLama.setGajiPokok(jabatanBaru.getGajiPokok());
		
	}

	@Override
	public void deleteById(Long jabatanId) {
		jabatanDb.deleteById(jabatanId);
	}
		
}
