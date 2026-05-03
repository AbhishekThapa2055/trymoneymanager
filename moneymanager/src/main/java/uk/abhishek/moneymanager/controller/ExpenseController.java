package uk.abhishek.moneymanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.abhishek.moneymanager.dto.ExpenseDTO;
import uk.abhishek.moneymanager.service.ExpenseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {
    private  final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> addExpense(@RequestBody ExpenseDTO dto)
    {
       ExpenseDTO expensesaved =  expenseService.addExpense(dto);
       return ResponseEntity.status(HttpStatus.CREATED).body(expensesaved);
    }
    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpense()
    {
        List<ExpenseDTO> expenses = expenseService.getCurrentMonthExpensesForCurrentUser();
        return ResponseEntity.ok(expenses);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id)
    {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
