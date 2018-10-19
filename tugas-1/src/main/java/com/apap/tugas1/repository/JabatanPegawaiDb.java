package com.apap.tugas1.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.JabatanPegawaiModel;

public interface JabatanPegawaiDb extends JpaRepository<JabatanPegawaiModel, BigInteger> {

}