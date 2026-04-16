package com.example.personajes.controller;


import com.example.personajes.model.Personaje;
import com.example.personajes.service.PersonajeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/personajes")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class PersonajeController {

    private final PersonajeService personajeService;

    public PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    @GetMapping
    public List<Personaje> listarPersonajes(@RequestParam(defaultValue = "nombre") String tipo_orden){
        return personajeService.obtenerTodosOrdenados(tipo_orden);
    }

    @GetMapping("/{id}")
    public Personaje obtenerPorId(@PathVariable Long id){
        return personajeService.encontrarPorId(id);
    }

    @PostMapping("/crear")
    public Personaje crearPersonaje(@RequestBody Personaje personaje){
        personaje.setFechaCreacion(LocalDateTime.now());
        return personajeService.guardar(personaje);
    }

    @PutMapping("/editar/{id}")
    public Personaje editarPersonaje(@PathVariable Long id, @RequestBody Personaje nuevos_datos){
        return personajeService.editar(id, nuevos_datos);
    }

    @DeleteMapping("/{id}")
    public void borrarPersonaje(@PathVariable Long id){
        personajeService.borrar(id);
    }


}
