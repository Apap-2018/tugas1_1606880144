package com.apap.tugas1.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;

	@Autowired
	private ProvinsiService provinsiService;

	@Autowired
	private InstansiService instansiService;

	@Autowired
	private JabatanService jabatanService;

	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getJabatan();
		model.addAttribute("listJabatan", listJabatan);
		List<InstansiModel> listInstansi = instansiService.getInstansi();
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}

	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String view(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNIP(nip);
		int gaji = (int) pegawai.getGaji();

		model.addAttribute("gaji", gaji);
		model.addAttribute("pegawai", pegawai);
		return "view-pegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		List<ProvinsiModel> listProv = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getJabatan();

		// default
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(listProv.get(0));

		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setJabatan(new ArrayList<JabatanModel>());
		pegawai.getJabatan().add(new JabatanModel());

		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		return "add-pegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", params = { "addRow" }, method = RequestMethod.POST)
	public String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {

		List<ProvinsiModel> listProv = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getJabatan();

		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);

		pegawai.getJabatan().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", params = { "deleteRow" }, method = RequestMethod.POST)
	public String deleteRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,
			Model model) {

		List<ProvinsiModel> listProv = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getJabatan();

		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);

		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		System.out.println(rowId);
		pegawai.getJabatan().remove(rowId.intValue());
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String kode = pegawai.getInstansi().getId().toString();

		SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
		String tanggalLahir = newFormat.format(pegawai.getTanggalLahir()).replaceAll("-", "");

		String tahunKerja = pegawai.getTahunMasuk();

		int urutan = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(),
				pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size() + 1;

		String strUrutan;
		if (urutan < 10)
			strUrutan = "0" + urutan;
		else
			strUrutan = "" + urutan;

		String nip = kode + tanggalLahir + tahunKerja + strUrutan;

		pegawai.setNip(nip);

		pegawaiService.addPegawai(pegawai);

		String msg = "Pegawai dengan NIP " + nip + " berhasil ditambahkan";
		model.addAttribute("message", msg);
		return "result";
	}

	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	public String update(@RequestParam("nip") String nip, Model model) {

		List<ProvinsiModel> listProv = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getJabatan();

		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);

		PegawaiModel pegawai = pegawaiService.getPegawaiByNIP(nip);

		model.addAttribute("pegawai", pegawai);
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String updateSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String oldNip = pegawai.getNip();
		PegawaiModel oldPegawai = pegawaiService.getPegawaiByNIP(oldNip);

		String newNip;
		if ((!oldPegawai.getTahunMasuk().equals(pegawai.getTahunMasuk()))
				|| (!oldPegawai.getTanggalLahir().equals(pegawai.getTanggalLahir()))
				|| (!oldPegawai.getInstansi().equals(pegawai.getInstansi()))) {

			String kode = pegawai.getInstansi().getId().toString();

			SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
			String tanggalLahir = newFormat.format(pegawai.getTanggalLahir()).replaceAll("-", "");

			String tahunKerja = pegawai.getTahunMasuk();

			int urutan = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(),
					pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size() + 1;

			String strUrutan;
			if (urutan < 10)
				strUrutan = "0" + urutan;
			else
				strUrutan = "" + urutan;

			newNip = kode + tanggalLahir + tahunKerja + strUrutan;
			pegawai.setNip(newNip);
			System.out.println(newNip);
		} else {
			newNip = oldNip;
			pegawai.setNip(oldNip);
		}

		pegawaiService.updatePegawai(oldNip, pegawai);

		String msg = "Pegawai dengan NIP " + newNip + " berhasil diubah";
		model.addAttribute("message", msg);
		return "result";
	}

	@RequestMapping(value = "/pegawai/ubah", params = { "addRow" }, method = RequestMethod.POST)
	public String addRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {

		List<ProvinsiModel> listProv = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getJabatan();

		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);

		pegawai.getJabatan().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		return "update-pegawai";
	}

	@RequestMapping(value = "/pegawai/ubah", params = { "deleteRow" }, method = RequestMethod.POST)
	public String deleteRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult,
			HttpServletRequest req, Model model) {

		List<ProvinsiModel> listProv = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getJabatan();

		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);

		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		pegawai.getJabatan().remove(rowId.intValue());
		model.addAttribute("pegawai", pegawai);
		return "update-pegawai";
	}

	

	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	private String findPegawai(@RequestParam(value = "idProvinsi", required = false) Optional<Integer> idProvinsi,
			@RequestParam(value = "idInstansi", required = false) Optional<BigInteger> idInstansi,
			@RequestParam(value = "idJabatan", required = false) Optional<BigInteger> idJabatan, Model model) {
		List<ProvinsiModel> listAllProv = provinsiService.getProvinsi();
		List<JabatanModel> listAllJabatan = jabatanService.getJabatan();
		List<InstansiModel> listAllInstansi = instansiService.getInstansi();

		model.addAttribute("listInstansi", listAllInstansi);
		model.addAttribute("listJabatan", listAllJabatan);
		model.addAttribute("listProvinsi", listAllProv);

		List<PegawaiModel> pegawai = new ArrayList<PegawaiModel>();
		if (idInstansi.isPresent()) {

			InstansiModel instansi = instansiService.getInstansiById(idInstansi.get()).get();
			if (idJabatan.isPresent()) {
				JabatanModel jabatan = jabatanService.getJabatanById(idJabatan.get()).get();

				pegawai = pegawaiService.getPegawaiByInstansiAndJabatan(instansi, jabatan);
			} else {
				pegawai = pegawaiService.getPegawaiByInstansi(instansi);
			}
		} else {
			List<PegawaiModel> pegawaiTemp = new ArrayList<PegawaiModel>();
			if (idProvinsi.isPresent()) {
				ProvinsiModel provinsi = provinsiService.getProvinsiById(idProvinsi.get());

				List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(provinsi);
				if (idJabatan.isPresent()) {
					JabatanModel jabatan = jabatanService.getJabatanById(idJabatan.get()).get();

					for (InstansiModel instansi : listInstansi) {
						pegawaiTemp = pegawaiService.getPegawaiByInstansiAndJabatan(instansi, jabatan);
					}
					pegawai = pegawaiTemp;

				} else {
					for (InstansiModel instansi : listInstansi) {
						pegawaiTemp = pegawaiService.getPegawaiByInstansi(instansi);
					}
					pegawai = pegawaiTemp;
				}
			} else if (idJabatan.isPresent()) {
				JabatanModel jabatan = jabatanService.getJabatanById(idJabatan.get()).get();

				pegawai = pegawaiService.getPegawaiByJabatan(jabatan);
			}
		}
		for (PegawaiModel opegawai : pegawai) {
			System.out.println(opegawai.getTempatLahir());
			System.out.println(opegawai.getJabatan());
		}
		model.addAttribute("listPegawai", pegawai);
		return "find-pegawai";
	}

	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewTuaMuda(@RequestParam("idInstansi") BigInteger idInstansi, Model model) {
		InstansiModel instansi = instansiService.getInstansiById(idInstansi).get();

		List<PegawaiModel> listPegawai = pegawaiService.getPegawaiByInstansi(instansi);
		System.out.println(listPegawai.size());
		if (listPegawai.isEmpty()) {
			model.addAttribute("message", "Instansi ini tidak memiliki pegawai");
			return "error";
		} else if (listPegawai.size() == 1) {
			model.addAttribute("message", "Instansi ini hanya memiliki 1 pegawai");
			return "error";
		} else {
			PegawaiModel pegawaiOldest = listPegawai.get(0);
			PegawaiModel pegawaiYoungest = listPegawai.get(1);

			for (PegawaiModel pegawai : listPegawai) {
				if (pegawai.getAge() > pegawaiOldest.getAge())
					pegawaiOldest = pegawai;
				else if (pegawai.getAge() < pegawaiYoungest.getAge())
					pegawaiYoungest = pegawai;
			}

			int gajiOld = (int) pegawaiOldest.getGaji();
			int gajiYoung = (int) pegawaiYoungest.getGaji();

			model.addAttribute("oldest", pegawaiOldest);
			System.out.println(pegawaiOldest.getNama());
			model.addAttribute("youngest", pegawaiYoungest);

			model.addAttribute("gajiOld", gajiOld);
			model.addAttribute("gajiYoung", gajiYoung);
			return "view-pegawaiTuaMuda";
		}
	}

}