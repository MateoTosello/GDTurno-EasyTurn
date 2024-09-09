package com.backend.easyturn.controllers;

import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    AdministratorService administratorService;

    @PostMapping(path="/post")
    @ResponseBody
    public int createAdministrator(@RequestBody Administrator administrator) {
        return this.administratorService.createAdministrator(administrator);
    }

    @GetMapping(path="/getAdministrators")
    @ResponseBody
    public List<Administrator> getAdministrators(){
        return this.administratorService.getAllAdministrator();
    }

    @DeleteMapping(path="/delete/{id}")
    public void deleteAdministrator(@PathVariable int id) {
        this.administratorService.deleteAdministrator(id);
    }

    @GetMapping(path = "/getAdministrator/{idAdmin}")
    @ResponseBody
    public Administrator getAdministrator(@PathVariable int idAdmin){
        return this.administratorService.getAdministrator(idAdmin);
    }

    @PutMapping("/update")
    @ResponseBody
    public Administrator updateAdministrator(@RequestBody Administrator administrator) {
        return this.administratorService.updateAdministrator(administrator);
    }
}
