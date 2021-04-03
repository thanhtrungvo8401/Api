package fusikun.com.api.service;

import fusikun.com.api.dao.CenterRepository;
import fusikun.com.api.model.study.Center;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class CenterService {
    @Autowired
    CenterRepository centerRepository;

    public Center save(Center entity) {
        if (entity.getId() == null) {
            entity.setCreatedDate(new Date());
        }
        if (entity.getIsActive() == null) {
            entity.setIsActive(true);
        }
        entity.setUpdatedDate(new Date());
        return centerRepository.save(entity);
    }

    public Center findByCenterName(String centerName) {
        Optional<Center> optCenter = centerRepository.findByCenterName(centerName);
        return optCenter.orElse(null);
    }

    public List<Center> findAll() {
        return centerRepository.findAll();
    }

    public Center findById(UUID id) {
        Optional<Center> optCenter = centerRepository.findById(id);
        return optCenter.orElse(null);
    }
    public void delete(Center center) {
        centerRepository.delete(center);
    }
}
