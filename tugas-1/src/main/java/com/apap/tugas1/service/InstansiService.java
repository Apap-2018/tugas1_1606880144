package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface InstansiService {
	List<InstansiModel> getInstansi();

	List<InstansiModel> getInstansiByProvinsi(ProvinsiModel provinsi);

	Optional<InstansiModel> getInstansiById(BigInteger idInstansi);

}
