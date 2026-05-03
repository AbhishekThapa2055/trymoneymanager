package uk.abhishek.moneymanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uk.abhishek.moneymanager.dto.ExpenseDTO;
import uk.abhishek.moneymanager.dto.IncomeDTO;
import uk.abhishek.moneymanager.entity.CategoryEntity;
import uk.abhishek.moneymanager.entity.ExpenseEntity;
import uk.abhishek.moneymanager.entity.IncomeEntity;
import uk.abhishek.moneymanager.entity.ProfileEntity;
import uk.abhishek.moneymanager.repository.CategoryRepository;
import uk.abhishek.moneymanager.repository.ExpenseRepository;
import uk.abhishek.moneymanager.repository.IncomeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final CategoryService categoryService;
    private  final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;
    public IncomeDTO addIncome(IncomeDTO dto)
    {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()-> new RuntimeException("Category not found"));
        IncomeEntity incomeEntity = toEntity(dto,category,profile);
        incomeEntity  = incomeRepository.save(incomeEntity);
        return toDTO(incomeEntity);

    }

    //Reterives all incoeme for current month/based on the start date and end date
    public List<IncomeDTO> getCurrentMonthIncomeForCurrentUser()
    {
        ProfileEntity profile =  profileService.getCurrentProfile();
        LocalDate now =  LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<IncomeEntity> list= incomeRepository.findByProfileIdAndDateBetween(profile.getId(), startDate,endDate);
        return list.stream().map(this::toDTO).toList();

    }

    //Delete expenses for current by id
    public void deleteIncome(Long expenseId)
    {
        ProfileEntity profile = profileService.getCurrentProfile();
        IncomeEntity entity = incomeRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));
        if(!entity.getProfile().getId().equals(profile.getId()))
        {
            throw  new RuntimeException("Unauthorized to delete this income");
        }
        incomeRepository.delete(entity);

    }
    //Get Latest 5 income for current user
    public List<IncomeDTO> getLatest5IncomeForCurrentUser()
    {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<IncomeEntity> list = incomeRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return list.stream().map(this::toDTO).toList();
    }

    //get the total income for current user
    public BigDecimal getTotalIncomeForCurrentUser()
    {
        ProfileEntity profile  = profileService.getCurrentProfile();
        BigDecimal total = incomeRepository.getTotalExpenseByProfileId(profile.getId());
        return total!=null ? total:BigDecimal.ZERO;
    }
    //filter incomes
    public List<IncomeDTO> filterIncomes(LocalDate startDate , LocalDate endDate , String keyword, Sort sort)
    {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<IncomeEntity> list = incomeRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(), startDate,endDate,keyword,sort);
        return list.stream().map(this::toDTO).toList();
    }





    //helper methods

    private IncomeEntity toEntity(IncomeDTO dto, CategoryEntity category, ProfileEntity profile)
    {
        return IncomeEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .date(dto.getDate())
                .amount(dto.getAmount())
                .category(category)
                .profile(profile)
                .build();
    }
    private IncomeDTO toDTO(IncomeEntity entity)
    {
        return IncomeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .categoryId(entity.getCategory()!=null?entity.getCategory().getId():null)
                .categoryName(entity.getCategory()!=null?entity.getCategory().getName():null)
                .amount(entity.getAmount())
                .date(entity.getDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

    }
}
