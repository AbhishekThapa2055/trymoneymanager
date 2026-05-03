package uk.abhishek.moneymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.abhishek.moneymanager.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    List<CategoryEntity> findByProfileId(Long profileId);

    //we goonna use this function while updating there we need category id and profile id
    Optional<CategoryEntity> findByIdAndProfileId(Long id,Long profileId);

     List<CategoryEntity> findByTypeAndProfileId(String type ,Long profileId);

     Boolean existsByNameAndProfileId(String name,Long profileId);
}
