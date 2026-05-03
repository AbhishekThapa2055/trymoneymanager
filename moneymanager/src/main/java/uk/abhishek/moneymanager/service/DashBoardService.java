package uk.abhishek.moneymanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.abhishek.moneymanager.dto.ExpenseDTO;
import uk.abhishek.moneymanager.dto.IncomeDTO;
import uk.abhishek.moneymanager.dto.RecentTransactionDTO;
import uk.abhishek.moneymanager.entity.ProfileEntity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Stream.concat;

@Service
@RequiredArgsConstructor

public class DashBoardService {
    private final ProfileService profileService;
    private  final IncomeService incomeService;
    private final ExpenseService expenseService;
    public Map<String,Object> getDashboardData() {
        ProfileEntity profile = profileService.getCurrentProfile();
        Map<String, Object> returnValue = new LinkedHashMap<>();
        List<IncomeDTO> latestIncomes = incomeService.getLatest5IncomeForCurrentUser();
        List<ExpenseDTO> latestExpenses = expenseService.getLatest5ExpensesForCurrentUser();
        List<RecentTransactionDTO> recentTransactions = concat(latestIncomes.stream().map(income ->
                RecentTransactionDTO.builder()
                        .id(income.getId())
                        .name(income.getName())
                        .profileId(profile.getId())
                        .icon(income.getIcon())
                        .amount(income.getAmount())
                        .date(income.getDate())
                        .createdAt(income.getCreatedAt())
                        .updatedAt(income.getUpdatedAt())
                        .type("income")
                        .build()

        ), latestExpenses.stream().map(expense ->
                RecentTransactionDTO.builder()
                        .id(expense.getId())
                        .profileId(profile.getId())
                        .icon(expense.getIcon())
                        .name(expense.getName())
                        .amount(expense.getAmount())
                        .date(expense.getDate())
                        .createdAt(expense.getCreatedAt())
                        .updatedAt(expense.getUpdatedAt())
                        .type("expense")
                        .build()
        )).sorted((a, b) ->

        //since we have concat the income and expense transactions we cannot see the latest transaction so this function helps to sort out based on the date if date
                //is same then based on the createdAt;
        {

            int cmp = b.getDate().compareTo(a.getDate());
            if (cmp == 0 && a.getCreatedAt() != null && b.getCreatedAt() != null) {
                return b.getCreatedAt().compareTo(a.getCreatedAt());
            }
            return cmp;

        }).collect(Collectors.toList());

returnValue.put("totalBalance",incomeService.getTotalIncomeForCurrentUser().subtract(expenseService.getTotalExpenseForCurrentUser()));
returnValue.put("totalIncome",incomeService.getTotalIncomeForCurrentUser());
returnValue.put("totalExpense",expenseService.getTotalExpenseForCurrentUser());
returnValue.put("recent5Expenses",latestExpenses);
returnValue.put("recent5Incomes",latestIncomes);
returnValue.put("recentTransactions",recentTransactions);
return  returnValue;
    }

}
