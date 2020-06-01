package cz.muni.fi.pa165.dominatingspecies.dao.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Ondrej Slimak on 25/03/2020.
 */

@Entity()
public class FoodChain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FoodChain_Id")
    private Long id;

    @NotNull(message = "Animals cannot be null")
    @OneToMany(cascade = {CascadeType.ALL},
                mappedBy = "foodChain")
    @OrderBy("indexInFoodChain")
    private List<AnimalInFoodChain> animalsInFoodChain = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAnimals(List<Animal> animals) {
        if (animals == null)
            throw new IllegalArgumentException("Animals cannot be null.");

        this.animalsInFoodChain.clear();

        for (int i = 0; i < animals.size(); i++) {
            Animal animal = animals.get(i);
            AnimalInFoodChain tmp = new AnimalInFoodChain();
            tmp.setAnimal(animal);
            tmp.setFoodChain(this);
            tmp.setIndexInFoodChain(i);

            this.animalsInFoodChain.add(tmp);
        }
    }

    public List<Animal> getAnimals() {
        List<Animal> result = new ArrayList<>();
        for (AnimalInFoodChain animalInFoodChain : animalsInFoodChain) {
            result.add(animalInFoodChain.getAnimal());
        }
        return result;
    }

    public List<AnimalInFoodChain> getAnimalsInFoodChain() {
        return animalsInFoodChain;
    }

    public void setAnimalsInFoodChain(List<AnimalInFoodChain> animalsInFoodChain) {
        this.animalsInFoodChain = animalsInFoodChain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodChain)) return false;
        FoodChain that = (FoodChain) o;

        // Hibernate BUG in PersistentBag: https://hibernate.atlassian.net/browse/HHH-5409
        // necessary to compare manually
        List<AnimalInFoodChain> thisAnimals = this.getAnimalsInFoodChain();
        List<AnimalInFoodChain> thatAnimals = that.getAnimalsInFoodChain();
        if (thisAnimals == thatAnimals) return true;

        if (thisAnimals != null && thatAnimals != null) {
            if (thisAnimals.size() != thatAnimals.size()) return false;

            for (int i = 0; i < thisAnimals.size(); i++) {
                if (thisAnimals.get(i).getIndexInFoodChain() != (thatAnimals.get(i)).getIndexInFoodChain() ||
                        !thisAnimals.get(i).getAnimal().equals(thatAnimals.get(i).getAnimal()))
                    return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAnimalsInFoodChain());
    }
}

