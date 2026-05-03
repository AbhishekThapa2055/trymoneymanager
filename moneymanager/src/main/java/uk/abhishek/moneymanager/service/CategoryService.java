package uk.abhishek.moneymanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import uk.abhishek.moneymanager.dto.CategoryDTO;
import uk.abhishek.moneymanager.entity.CategoryEntity;
import uk.abhishek.moneymanager.entity.ProfileEntity;
import uk.abhishek.moneymanager.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ProfileService profileService;
    private  final CategoryRepository categoryRepository;

    public CategoryDTO saveCategory(CategoryDTO categoryDTO)
    {
        ProfileEntity profile = profileService.getCurrentProfile();
        if(categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), profile.getId()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT ,"Category with this name already exists");

        }
        CategoryEntity newCategory = toEntity(categoryDTO,profile);
        newCategory = categoryRepository.save(newCategory);
        return toDTO(newCategory);
    }
//get Categories for the current user
public List<CategoryDTO> getCategoriesForCurrentUser()
{
    ProfileEntity profile = profileService.getCurrentProfile();
    List<CategoryEntity> categories = categoryRepository.findByProfileId(profile.getId());
    return categories.stream().map(this::toDTO).toList();
}

public List<CategoryDTO> getCategoriesByTypeForCurrentUser(String type)
{
    ProfileEntity profile = profileService.getCurrentProfile();
    List<CategoryEntity> categories = categoryRepository.findByTypeAndProfileId(type,profile.getId());
    return categories.stream().map(this::toDTO).toList();
}

public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO)
{
    ProfileEntity profile = profileService.getCurrentProfile();
    CategoryEntity updateCategory = categoryRepository.findByIdAndProfileId(categoryId,profile.getId()).orElseThrow(()-> new RuntimeException("Category not found or not accessible !!!"));
    updateCategory.setName(categoryDTO.getName());
    updateCategory.setIcon(categoryDTO.getIcon());
    updateCategory.setType(categoryDTO.getType());
   CategoryEntity updatedCategory = categoryRepository.save(updateCategory);
   return toDTO(updatedCategory);
}













    //helper methods

    private CategoryEntity toEntity(CategoryDTO categoryDTO, ProfileEntity profile)
    {
        return CategoryEntity.builder()
                .name(categoryDTO.getName())
                .profile(profile)
                .type(categoryDTO.getType())
                .icon(categoryDTO.getIcon())
                .build();
    }
    private CategoryDTO toDTO(CategoryEntity entity)

    {
        return CategoryDTO.builder()
                .id(entity.getId())
                .profileId(entity.getProfile()!=null ? entity.getProfile().getId():null)
                .name(entity.getName())
                .icon(entity.getIcon())
                .type(entity.getType())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
