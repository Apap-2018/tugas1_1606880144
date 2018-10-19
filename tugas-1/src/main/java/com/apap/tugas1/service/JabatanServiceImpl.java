package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDb jabatanDB;

	@Override
	public List<JabatanModel> getJabatan() {
		return jabatanDB.findAll();
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDB.save(jabatan);
	}

	@Override
	public Optional<JabatanModel> getJabatanById(BigInteger id) {
		return jabatanDB.findById(id);
	}

	@Override
	public void updateJabatan(JabatanModel jabatan) {
		JabatanModel updateJabatan = jabatanDB.findById(jabatan.getId()).get();
		updateJabatan.setNama(jabatan.getNama());
		updateJabatan.setDeskripsi(jabatan.getDeskripsi());
		updateJabatan.setGajiPokok(jabatan.getGajiPokok());
		jabatanDB.save(updateJabatan);
	}

	@Override
	public void deleteJabatanById(BigInteger id) {
		jabatanDB.deleteById(id);
	}

	@Override
	public List<JabatanModel> getAllJabatan() {
		return jabatanDB.findAll();
	}
}
