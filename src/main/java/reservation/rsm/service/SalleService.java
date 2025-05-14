package reservation.rsm.service;

import reservation.rsm.entity.Salle;
import reservation.rsm.repository.SalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalleService {

    private final SalleRepository salleRepository;

    public SalleService(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    public List<Salle> getAllSalles() {
        return salleRepository.findAll();
    }

    public Optional<Salle> getSalleById(Long id) {
        return salleRepository.findById(id);
    }

    public Salle createSalle(Salle salle) {
        return salleRepository.save(salle);
    }

    public Salle updateSalle(Long id, Salle salleDetails) throws Exception {
        Salle salle = salleRepository.findById(id)
                .orElseThrow(() -> new Exception("Salle non trouvée avec l'id : " + id));

        salle.setNom(salleDetails.getNom());
        salle.setCapacite(salleDetails.getCapacite());

        return salleRepository.save(salle);
    }

    public void deleteSalle(Long id) throws Exception {
        Salle salle = salleRepository.findById(id)
                .orElseThrow(() -> new Exception("Salle non trouvée avec l'id : " + id));
        salleRepository.delete(salle);
    }
}
