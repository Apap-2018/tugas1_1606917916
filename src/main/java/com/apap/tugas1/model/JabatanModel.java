package com.apap.tugas1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * JabatanModel
 */
@Entity
@Table(name = "jabatan")
public class JabatanModel implements Serializable, Comparable<JabatanModel> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false, unique = true)
	private String nama;

	@NotNull
	@Size(max = 255)
	@Column(name = "deskripsi", nullable = false)
	private String deskripsi;
	
	@NotNull
	@Column(name = "gaji_pokok", nullable = false)
	private Double gajiPokok;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "jabatan_pegawai", joinColumns = { @JoinColumn(name = "id_jabatan") }, inverseJoinColumns = { @JoinColumn(name = "id_pegawai") })
	private List<PegawaiModel> pegawai = new ArrayList<PegawaiModel>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public Double getGajiPokok() {
		return gajiPokok;
	}

	public void setGajiPokok(Double gajiPokok) {
		this.gajiPokok = gajiPokok;
	}


	public List<PegawaiModel> getPegawai() {
		return pegawai;
	}

	public void setPegawai(List<PegawaiModel> pegawai) {
		this.pegawai = pegawai;
	}

	
	@Override
	public int compareTo(JabatanModel other) {
		// TODO Auto-generated method stub
		if (this.id < other.getId()) {
			return -1;
		} else if (this.id > other.getId()) {
			return 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof JabatanModel)) return false;
		
		JabatanModel jabatan = (JabatanModel) other;		
		if (this.id != jabatan.getId()) return false;
		return true;
	}
	
}
