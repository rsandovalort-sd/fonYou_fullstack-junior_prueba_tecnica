package com.example.personajes.repository;

import com.example.personajes.PersonajesApplication;
import com.example.personajes.model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {
}
