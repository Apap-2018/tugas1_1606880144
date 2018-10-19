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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The persistent class for the provinsi database table.
 * 
 */
@Entity
@Table(name = "provinsi")
public class ProvinsiModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;

	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;

	@NotNull
	@Column(name = "presentase_tunjangan", nullable = false)
	private double presentaseTunjangan;

	// bi-directional many-to-one association to Instansi
	@OneToMany(mappedBy = "provinsiModel", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<InstansiModel> instansiModels;

	public ProvinsiModel() {
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getNama() {
		return this.nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public double getPresentaseTunjangan() {
		return this.presentaseTunjangan;
	}

	public void setPresentaseTunjangan(double presentaseTunjangan) {
		this.presentaseTunjangan = presentaseTunjangan;
	}

	public List<InstansiModel> getInstansis() {
		return this.instansiModels;
	}

	public void setInstansis(List<InstansiModel> instansiModels) {
		this.instansiModels = instansiModels;
	}

	public InstansiModel addInstansi(InstansiModel instansiModel) {
		getInstansis().add(instansiModel);
		instansiModel.setProvinsi(this);

		return instansiModel;
	}

	public InstansiModel removeInstansi(InstansiModel instansiModel) {
		getInstansis().remove(instansiModel);
		instansiModel.setProvinsi(null);

		return instansiModel;
	}

}