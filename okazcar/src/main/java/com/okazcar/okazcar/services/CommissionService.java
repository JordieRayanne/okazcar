package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.Commission;
import com.okazcar.okazcar.repositories.CommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommissionService {

    private final CommissionRepository commissionRepository;

    @Autowired
    public CommissionService(CommissionRepository commissionRepository) {
        this.commissionRepository = commissionRepository;
    }

    public Commission insert(Commission commission) {
        return commissionRepository.save(commission);
    }

    public List<Commission> findAll() {
        return commissionRepository.findAll();
    }
}
