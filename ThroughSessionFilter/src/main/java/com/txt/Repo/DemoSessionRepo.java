package com.txt.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.txt.entity.DemoSession;

@Repository
public interface DemoSessionRepo extends JpaRepository<DemoSession, Integer>{

	@Query("from DemoSession   where name=:name")
	List<DemoSession> getByName(@Param("name")String name);
	
	@Query("from DemoSession   where salary=:salary")
	List<DemoSession> getBySalary(@Param("salary")Double salary);
}
