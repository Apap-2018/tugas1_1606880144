package com.apap.tugas1.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;

	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("jabatan", jabatan);
		return "add-jabatan";
	}

	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		String msg = "jabatan " + jabatan.getNama() + " berhasil ditambahkan";
		model.addAttribute("message", msg);
		return "result";
	}

	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String view(@RequestParam("idJabatan") BigInteger idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(idJabatan).get();
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}

	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	public String update(@RequestParam("idJabatan") BigInteger idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(idJabatan).get();
		model.addAttribute("jabatan", jabatan);
		return "update-jabatan";
	}

	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.updateJabatan(jabatan);

		String msg = "Jabatan " + jabatan.getNama() + " berhasil diubah";
		model.addAttribute("message", msg);
		return "result";
	}

	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	private String delete(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.deleteJabatanById(jabatan.getId());
		model.addAttribute("message", "hapus");
		return "result";
	}

	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	private String viewAll(Model model) {
		List<JabatanModel> jabatan = jabatanService.getJabatan();
		model.addAttribute("listJabatan", jabatan);
		return "view-allJabatan";
	}

}
