package com.example.crong.repositories;

import com.example.crong.models.OngModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OngRepository extends JpaRepository <OngModel, UUID>{
}
