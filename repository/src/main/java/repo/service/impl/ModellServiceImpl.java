package repo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import repo.exception.ResourceNotFoundException;
import repo.model.Modell;
import repo.repository.ModellRepository;
import repo.service.ModellService;

@Service
public class ModellServiceImpl implements ModellService {

	private ModellRepository modellRepository;
	
	public ModellServiceImpl(ModellRepository modellRepository) {
		super();
		this.modellRepository = modellRepository;
	}

	@Override
	public Modell saveModell(Modell modell) {
		return modellRepository.save(modell);
	}

	@Override
	public List<Modell> getAllModells() {
		return modellRepository.findAll();
	}

	@Override
	public Modell getModellById(long id) {
//		Optional<Modell> modell = modellRepository.findById(id);
//		if(modell.isPresent()) {
//			return modell.get();
//		}else {
//			throw new ResourceNotFoundException("Modell", "Id", id);
//		}
		return modellRepository.findById(id).orElseThrow(() ->
						new ResourceNotFoundException("Modell", "Id", id));
		
	}

	@Override
	public Modell updateModell(Modell modell, long id) {

		// we need to check whether modell with given id is exist in DB or not
		//Modell existingModell = modellRepository.findById(id).orElseThrow(
		//		() -> new ResourceNotFoundException("Modell", "Id", id));

		//existingModell.setID(modell.getID());
		//existingModell.setName(modell.getName());
		// save existing modell to DB
		//modellRepository.save(existingModell);
		//return existingModell;
	return null;
	}

	@Override
	public void deleteModell(long id) {

		modellRepository.findById(id).orElseThrow(() ->
								new ResourceNotFoundException("Modell", "Id", id));
		modellRepository.deleteById(id);
	}




	
}
