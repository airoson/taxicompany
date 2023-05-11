package com.javatemplates.taxicompany.repositories;

import com.javatemplates.taxicompany.models.carmodel.Car;
import com.javatemplates.taxicompany.models.carmodel.Engine;
import com.javatemplates.taxicompany.models.carmodel.Gearbox;
import com.javatemplates.taxicompany.models.carmodel.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface CarRepository extends CrudRepository<Car, Long>, PagingAndSortingRepository<Car, Long> {
    @Query(value = "SELECT * FROM Car c WHERE c.rates && ?1 AND c.engine = ANY ( ?2 ) AND c.gearbox = ANY ( ?3 )",
    nativeQuery = true)
    List<Car> findAllByRatesAndGearboxAndEngine(Rate[] rates, String[] engine, String[] gearboxes, Pageable pageable);

    @Query(value = "SELECT * FROM Car c WHERE  c.rates && ?1 AND c.engine = ANY ( ?2 ) AND c.gearbox = ANY ( ?3 ) AND c.name LIKE ?4",
            nativeQuery = true)
    List<Car> findAllByRatesAndGearboxAndEngineAndName(Rate[] rates, String[] engine, String[] gearboxes, String name, Pageable pageable);

    @Query(value = "SELECT count(*) FROM CAR c WHERE c.rates && ?1 AND c.engine = ANY ( ?2 ) AND c.gearbox = ANY ( ?3 )",
    nativeQuery = true)
    long countCarsByRatesAndGearboxAndEngine(Rate[] rates, String[] engine, String[] gearboxes);

    @Query(value = "SELECT count(*) FROM Car c WHERE c.rates && ?1 AND c.engine = ANY ( ?2 ) AND c.gearbox = ANY ( ?3 ) AND c.name LIKE ?4",
    nativeQuery = true)
    long countCarsByRatesAndGearboxAndEngineAndName(Rate[] rates, String[] engine, String[] gearboxes, String name);

    @Query(value="SELECT name FROM Car GROUP BY name",
    nativeQuery = true)
    List<String> findAllNames();

    @Query(value="WITH mytable AS (SELECT ROW_NUMBER() OVER(ORDER BY id) AS row_number, id FROM Car) SELECT row_number FROM mytable WHERE id = ?1",
    nativeQuery = true)
    long getRowNumberOfCarByID(Long id);

    @Query(value="SELECT * FROM Car c WHERE c.name LIKE ?1",
    nativeQuery = true)
    List<Car> findByName(String name, Pageable page);

    Page<Car> findById(Long id, Pageable page);
}
