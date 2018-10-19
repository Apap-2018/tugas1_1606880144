package com.apap.tugas1.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The persistent class for the jabatan database table.
 * 
 */
@Entity
@Table(name = "jabatan")
public class JabatanModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;

	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;

	@NotNull
	@Size(max = 255)
	@Column(name = "deskripsi", nullable = false)
	private String deskripsi;

	@NotNull
	@Column(name = "gaji_pokok", nullable = false)
	private double gajiPokok;

	// bi-directional many-to-many association to Pegawai
//	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//	@JoinTable(name = "jabatan_pegawai", joinColumns = {
//			@JoinColumn(name = "id_jabatan", referencedColumnName = "id") }, inverseJoinColumns = {
//					@JoinColumn(name = "id_pegawai", referencedColumnName = "id") })
//	@JsonIgnore
//	private List<PegawaiModel> pegawaiModels;

	public JabatanModel() {
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getDeskripsi() {
		return this.deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public double getGajiPokok() {
		return this.gajiPokok;
	}

	public void setGajiPokok(double gajiPokok) {
		this.gajiPokok = gajiPokok;
	}

	public String getNama() {
		return this.nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

//	public List<PegawaiModel> getPegawais() {
//		return this.pegawaiModels;
//	}
//
//	public void setPegawais(List<PegawaiModel> pegawaiModels) {
//		this.pegawaiModels = pegawaiModels;
//	}

}