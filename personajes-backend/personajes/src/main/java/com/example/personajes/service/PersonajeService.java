package com.example.personajes.service;

import com.example.personajes.model.Personaje;
import com.example.personajes.repository.PersonajeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonajeService {

    private final PersonajeRepository personajeRepository;

    public PersonajeService(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Cacheable(value = "personajes", key= "#tipo_orden")
    public List<Personaje> obtenerTodosOrdenados(String tipo_orden){
        try {
            Sort sort = tipo_orden.equals("fecha_creacion") ? Sort.by(Sort.Direction.DESC, "fechaCreacion") : Sort.by(Sort.Direction.ASC, "nombre");
            return personajeRepository.findAll(sort);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("No fue posible obtener los personajes. Inténtelo más tarde");
        }
    }

    public Personaje encontrarPorId(Long id){
        return personajeRepository.findById(id).orElseThrow(()-> new RuntimeException("El personaje no ha sido creado aún"));
    }

    @CacheEvict(value = "personajes", allEntries = true)
    public Personaje guardar(Personaje personaje){
        try{
            if(personaje == null || personaje.getNombre().isEmpty()){
                throw new RuntimeException("El nombre del personaje es necesario");
            }
            return personajeRepository.save(personaje);
        }catch (Exception e){
            throw new RuntimeException("No fue posible guardar el personaje. Inténtelo más tarde");
        }

    }

    @CacheEvict(value = "personajes", allEntries = true)
    public Personaje editar(Long id, Personaje info_actualizada) {
        try {
            Personaje personajeExistente = encontrarPorId(id);

            personajeExistente.setNombre(info_actualizada.getNombre());
            personajeExistente.setIdentificacion(info_actualizada.getIdentificacion());
            personajeExistente.setImagen(info_actualizada.getImagen());
            personajeExistente.setRol(info_actualizada.getRol());
            personajeExistente.setDescripcion(info_actualizada.getDescripcion());

            return personajeRepository.save(personajeExistente);
        } catch (Exception e) {
            throw new RuntimeException("No fue posible editar el personaje. Inténtelo más tarde");
        }
    }

    @CacheEvict(value = "personajes", allEntries = true)
    public void borrar(Long id){
        try {
            personajeRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("No fue posible elminar el personaje. Inténtelo más tarde");
        }
    }

}
