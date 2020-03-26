package dao.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kostka on 23/03/2020.
 */
@Entity
@Table(name = "Animal")
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Animals cannot be null")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "FoodChain", joinColumns = { @JoinColumn(name = "ID")}, inverseJoinColumns = {@JoinColumn(name="ID")})
    private Set<FoodChain> foodChain = new HashSet<FoodChain>();
}
