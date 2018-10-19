package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	void addJabatan(JabatanModel jabatan);

	void updateJabatan(JabatanModel jabatan);

	void deleteJabatanById(BigInteger id);

	List<JabatanModel> getJabatan();

	Optional<JabatanModel> getJabatanById(BigInteger id);

	List<JabatanModel> getAllJabatan();
}
