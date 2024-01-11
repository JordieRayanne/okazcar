package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.Type;
import com.okazcar.okazcar.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {

    final TypeRepository typeRepository;
    @Autowired
    public TypeService (TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    public Type findOne(int id) {
        return typeRepository.findById(id).orElse(null);
    }

    public Type insert(Type type) {
        return typeRepository.save(type);
    }

    public Type update(Type type) {
        return typeRepository.save(type);
    }

    public int delete(int id) {
        typeRepository.deleteById(id);
        return id;
    }
}
