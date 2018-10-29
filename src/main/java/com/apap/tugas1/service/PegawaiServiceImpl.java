package com.apap.tugas1.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawai.setNip(this.generateNIP(pegawai));
		pegawaiDb.save(pegawai);
	}
	
	@Override
	public String generateNIP(PegawaiModel pegawai){
		InstansiModel instansi = pegawai.getInstansi();
		Date tanggalLahir = pegawai.getTanggalLahir();
		String tahunMasuk = pegawai.getTahunMasuk();
		int urutanPegawai = 1;
		
		List<PegawaiModel> listSementaraNIP = this.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
		if (!listSementaraNIP.isEmpty()) {
			urutanPegawai = (int) (Long.parseLong(listSementaraNIP.get(listSementaraNIP.size()-1).getNip())%100) + 1;
		}
		
		String kodeInstansi = Long.toString(instansi.getId());
		
		String pattern = "dd-MM-yy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		String tanggalLahirString = simpleDateFormat.format(tanggalLahir).replaceAll("-", "");
		String urutanPegawaiString = urutanPegawai/10 == 0 ? ("0" + Integer.toString(urutanPegawai)) : (Integer.toString(urutanPegawai));
		String nip = kodeInstansi + tanggalLahirString + tahunMasuk + urutanPegawaiString;
		return nip;
	}

	@Override
	public PegawaiModel getPegawaiByNip(String nip) {
		return pegawaiDb.findByNip(nip).get(0);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk) {
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}

	@Override
	public List<PegawaiModel> getPegawaiByProvinsi(Long provinsiId) {
		List<PegawaiModel> pegawaiByProvinsi = new ArrayList<>();
		
		for(PegawaiModel pegawai: pegawaiDb.findAll()) {
			if (pegawai.getInstansi().getProvinsi().getId() == provinsiId) {
				pegawaiByProvinsi.add(pegawai);
			}
		}
		return pegawaiByProvinsi;
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		return pegawaiDb.findByInstansi(instansi);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan) {
		List<PegawaiModel> pegawaiByInstansiAndJabatan = new ArrayList<>();
		
		for(PegawaiModel pegawai: pegawaiDb.findByInstansi(instansi)) {
			if (pegawai.getJabatan().contains(jabatan)) {
				pegawaiByInstansiAndJabatan.add(pegawai);
			}
		}
		return pegawaiByInstansiAndJabatan;
	}

	@Override
	public void deleteByNip(String nip) {
		pegawaiDb.deleteByNip(nip);
	}

	@Override
	public void update(String nip, PegawaiModel pegawaiBaru) {
		PegawaiModel pegawaiLama = pegawaiDb.findByNip(nip).get(0);
		
		if(!pegawaiLama.getTanggalLahir().equals(pegawaiBaru.getTanggalLahir())||
			!pegawaiLama.getTahunMasuk().equals(pegawaiBaru.getTahunMasuk())||
			!pegawaiLama.getInstansi().equals(pegawaiBaru.getInstansi())) {
			pegawaiLama.setNip(this.generateNIP(pegawaiBaru));
			pegawaiLama.setNip(pegawaiLama.getNip());
		}
		pegawaiLama.setNama(pegawaiBaru.getNama());
		pegawaiLama.setTahunMasuk(pegawaiBaru.getTahunMasuk());
		pegawaiLama.setInstansi(pegawaiBaru.getInstansi());
		pegawaiLama.setTanggalLahir(pegawaiBaru.getTanggalLahir());
		pegawaiLama.setTempatLahir(pegawaiBaru.getTempatLahir());
		pegawaiLama.setJabatan(pegawaiBaru.getJabatan());
		
		pegawaiDb.save(pegawaiLama);		
	}

	@Override
	public List<PegawaiModel> getPegawaiByProvinsiAndJabatan(Long provinsiId, JabatanModel jabatan) {
		List<PegawaiModel> pegawaiByProvinsiAndJabatan = new ArrayList<>();
		
		for(PegawaiModel pegawai : this.getPegawaiByProvinsi(provinsiId)) {
			if (pegawai.getJabatan().contains(jabatan)) {
				pegawaiByProvinsiAndJabatan.add(pegawai);
			}
		}
		return pegawaiByProvinsiAndJabatan;
	}

	@Override
	public List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan) {
		return pegawaiDb.findByJabatan(jabatan);
	}

	@Override
	public List<PegawaiModel> getTuaMudaInstansi(InstansiModel instansi) {
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
	}
}
