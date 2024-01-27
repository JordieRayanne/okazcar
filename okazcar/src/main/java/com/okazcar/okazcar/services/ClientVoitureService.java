package com.okazcar.okazcar.services;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okazcar.okazcar.models.ClientVoiture;

import com.okazcar.okazcar.repositories.ClientVoitureRepository;

@Service
public class ClientVoitureService {
    private final ClientVoitureRepository clientVoitureRepository;

    @Autowired
    public ClientVoitureService(ClientVoitureRepository clientVoitureRepository){
        this.clientVoitureRepository=clientVoitureRepository;
    }

    public List<ClientVoiture> getAllClientVoiture(){
        return clientVoitureRepository.findAll();
    }

    public ClientVoiture createClientVoiture(ClientVoiture clienntVoiture){
        clienntVoiture = clientVoitureRepository.save(clienntVoiture);
        return clienntVoiture;
    }

    public ClientVoiture updateClientVoiture(int clientVoitureid) {
        return clientVoitureRepository.findById(clientVoitureid).map(clientVoiture -> {
            clientVoiture.setFavori(10);
            clientVoitureRepository.save(clientVoiture);
            return clientVoiture;
        }).orElseThrow(() -> new EntityNotFoundException("Client voiture not found with id: " + clientVoitureid));
    }


}
