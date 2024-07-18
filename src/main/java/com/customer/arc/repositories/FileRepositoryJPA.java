package com.customer.arc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.arc.entities.File;

public interface FileRepositoryJPA extends JpaRepository<File, Long>{

}
