package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fpt.edu.vn.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
