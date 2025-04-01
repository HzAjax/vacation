package ru.volodin.vacation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.volodin.vacation.entity.CalculateDTO;
import ru.volodin.vacation.service.CalculateService;

import java.util.UUID;

@SuppressWarnings({"unused"})
@RestController
@RequestMapping("/vocation/calculate")
public class CalculateController {

    @Autowired
    private CalculateService calculateService;

    @PostMapping
    public ResponseEntity<?> getVacationCompensationHard(@RequestBody CalculateDTO cacl){
        try{
            return ResponseEntity.ok(calculateService.getVacationCompensationHard(cacl));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getVacationCompensationEazy(@RequestParam float salary,
                                                         @RequestParam int day){
        try {
            return ResponseEntity.ok(calculateService.getVacationCompensationEazy(salary, day));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
