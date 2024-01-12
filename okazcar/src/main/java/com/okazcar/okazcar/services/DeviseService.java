package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.Devise;
import com.okazcar.okazcar.repositories.DeviseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviseService {
    final DeviseRepository deviseRepository;
    @Autowired
    public DeviseService (DeviseRepository deviseRepository) {
        this.deviseRepository = deviseRepository;
    }

    public List<Devise> findAll() {
        return deviseRepository.findAll();
    }

    public Devise findOne(int id) {
        return deviseRepository.findById(id).orElse(null);
    }

    public Devise insert(Devise type) {
        return deviseRepository.save(type);
    }

    public Devise update(Devise type) {
        return deviseRepository.save(type);
    }

    public int delete(int id) {
        deviseRepository.deleteById(id);
        return id;
    }
}
