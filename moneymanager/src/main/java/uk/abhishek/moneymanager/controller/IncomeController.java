package uk.abhishek.moneymanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.abhishek.moneymanager.dto.ExpenseDTO;
import uk.abhishek.moneymanager.dto.IncomeDTO;
import uk.abhishek.moneymanager.service.IncomeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<IncomeDTO> addExpense(@RequestBody IncomeDTO dto)
    {
         IncomeDTO incomesaved =  incomeService.addIncome(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(incomesaved);
    }
    @GetMapping
    public ResponseEntity<List<IncomeDTO>> getExpense()
    {
        List<IncomeDTO> incomes = incomeService.getCurrentMonthIncomeForCurrentUser();
        return ResponseEntity.ok(incomes);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id)
    {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}
