package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class JabatanController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	// Fitur Lima: Menambahkan Jabatan
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	public String tambahJabatan (Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		model.addAttribute("headerTitle", "Tambah Jabatan");
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	public String tambahJabatan (Model model, @ModelAttribute JabatanModel jabatan) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("headerTitle", "Tambah Jabatan Sukses");
		return "add-jabatan-sukses";
	}
	
	// Fitur Enam: Menampilkan data suatu jabatan
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	public String viewJabatan (@RequestParam(value = "idJabatan", required = true) Long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(idJabatan).get();
		int jumlahPegawai = jabatan.getPegawai().size();
		model.addAttribute("jumlahPegawai", jumlahPegawai);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("headerTitle", "Lihat Jabatan");
		return "view-jabatan";
	}
	
	// Fitur Tujuh: Mengubah data jabatan
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	public String ubahJabatan (@RequestParam(value = "idJabatan", required = true) Long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(idJabatan).get();
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("headerTitle", "Ubah Jabatan");
		return "ubah-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	public String ubahJabatan (Model model, @ModelAttribute JabatanModel jabatan) {
		jabatanService.updateJabatan(jabatan.getId(), jabatan);
		model.addAttribute("headerTitle", "Ubah Jabatan Sukses");
		return "ubah-jabatan-sukses";
	}
	
	// Fitur Delapan: Menghapus jabatan
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	public String hapusJabatan (Model model, @RequestParam(value = "idJabatan", required = true) Long idJabatan) {
		try {
			jabatanService.deleteById(idJabatan);
			model.addAttribute("headerTitle", "Hapus Jabatan Sukses");
			return "delete-jabatan-sukses";
		}
		catch (Exception ex) {
			return "delete-jabatan-gagal";
		}
	}
	
	// Fitur Sembilan: Menampilkan daftar jabatan
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	public String viewAllJabatan (Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("headerTitle", "Viewall Jabatan");
		return "view-all-jabatan";
	}
}
