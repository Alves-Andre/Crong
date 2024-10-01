package com.example.crong.controllers;

import com.example.crong.models.OngModel;
import com.example.crong.repositories.OngRepository;
import com.example.crong.dto.OngDto;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class OngController {

    @Autowired
    OngRepository ongRepository;

    @PostMapping("/ong")
    public ResponseEntity<OngModel> saveOng (@RequestBody @Valid OngDto ongDto){
        var ong = new OngModel();
        BeanUtils.copyProperties(ongDto, ong);
        return ResponseEntity.status(HttpStatus.CREATED).body(ongRepository.save(ong));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getField() + ": " + ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Revise o Formulário.\n" + errorMessage);
    }


    @GetMapping("/ong")
    public ResponseEntity<List<OngModel>> getAllOng (){
        return ResponseEntity.status(HttpStatus.OK).body(ongRepository.findAll());
    }

    @GetMapping("/ong/{id}")
    public ResponseEntity<Object> getOneOng(@PathVariable(value= "id") UUID id){
        Optional<OngModel> ong = ongRepository.findById(id);
        if (ong.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ong não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(ong.get());
    }

    @PutMapping("/ong/{id}")
    public ResponseEntity<Object> updateOng(@PathVariable(value= "id") UUID id,
                                                      @RequestBody @Valid OngDto ongDto){
        Optional<OngModel> ong = ongRepository.findById(id);
        if (ong.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ong não encontrada");
        }
        var ongModel = ong.get();
        BeanUtils.copyProperties(ongDto, ongModel);
        return ResponseEntity.status(HttpStatus.OK).body(ongRepository.save(ongModel));
    }

    @DeleteMapping("/ong/{id}")
    public ResponseEntity<Object> deleteOng(@PathVariable(value = "id") UUID id){
        Optional<OngModel> ong = ongRepository.findById(id);
        if (ong.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ong não encontrada");
        }
        ongRepository.delete(ong.get());
        return ResponseEntity.status(HttpStatus.OK).body("Ong deletada com sucesso");
    }
}

