package ru.volodin.vacation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.volodin.vacation.service.CalculateService;

import java.time.LocalDate;

@SuppressWarnings({"unused"})
@RestController
@RequestMapping("/vacation/calculate")
public class CalculateController {

    @Autowired
    private CalculateService calculateService;

    @GetMapping("/hard")
    public ResponseEntity<?> getVacationCompensationHard(@RequestParam float salary){
        try{
            return ResponseEntity.ok(calculateService.getVacationCompensationHard(salary));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/ez")
    public ResponseEntity<?> getVacationCompensationEazy(@RequestParam float salary,
                                                         @RequestParam int day){
        if(salary <= 0 || day <= 0){
            return ResponseEntity.badRequest().body("Неверно введены данные");
        }
        try {
            return ResponseEntity.ok(calculateService.getVacationCompensationEazy(salary, day));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
