package com.apap.tugas1.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the instansi database table.
 * 
 */
@Entity
@Table(name = "instansi")
public class InstansiModel implements Serializable {
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

	// bi-directional many-to-one association to Provinsi
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_provinsi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private ProvinsiModel provinsiModel;

	// bi-directional many-to-one association to Pegawai
	@OneToMany(mappedBy = "instansi", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<PegawaiModel> pegawais;

	public InstansiModel() {
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

	public String getNama() {
		return this.nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public ProvinsiModel getProvinsi() {
		return this.provinsiModel;
	}

	public void setProvinsi(ProvinsiModel provinsiModel) {
		this.provinsiModel = provinsiModel;
	}

	public List<PegawaiModel> getPegawais() {
		return this.pegawais;
	}

	public void setPegawais(List<PegawaiModel> pegawaiModels) {
		this.pegawais = pegawaiModels;
	}

	public PegawaiModel addPegawai(PegawaiModel pegawaiModel) {
		getPegawais().add(pegawaiModel);
		pegawaiModel.setInstansi(this);

		return pegawaiModel;
	}

	public PegawaiModel removePegawai(PegawaiModel pegawaiModel) {
		getPegawais().remove(pegawaiModel);
		pegawaiModel.setInstansi(null);

		return pegawaiModel;
	}

}