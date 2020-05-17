package dao.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ondrej Slimak on 18/04/2020.
 */
@Entity
@Table(name = "FoodChain_Animals")
public class AnimalInFoodChain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FoodChain_Animal_Id")
    private Long id;

    @ManyToOne
    @NotNull(message = "FoodChain cannot be null")
    @JoinColumn(name = "FoodChain_Id")
    private FoodChain foodChain;

    @ManyToOne
    @NotNull(message = "Animal cannot be null")
    @JoinColumn(name = "Animal_Id")
    private Animal animal;

    private int indexInFoodChain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FoodChain getFoodChain() {
        return foodChain;
    }

    public void setFoodChain(FoodChain foodChain) {
        this.foodChain = foodChain;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public int getIndexInFoodChain() {
        return indexInFoodChain;
    }

    public void setIndexInFoodChain(int indexInFoodChain) {
        this.indexInFoodChain = indexInFoodChain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalInFoodChain)) return false;
        AnimalInFoodChain that = (AnimalInFoodChain) o;
        return getIndexInFoodChain() == that.getIndexInFoodChain() &&
                getFoodChain().getId().equals(that.getFoodChain().getId()) &&
                getAnimal().equals(that.getAnimal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFoodChain().getId(), getAnimal(), getIndexInFoodChain());
    }
}

