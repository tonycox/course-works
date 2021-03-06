package org.lenteam.colmen.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;

/**
 * @author Anton_Solovev
 * @since 9/10/2016
 */
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value = "name")
public class StatisticPerson extends Person {

    private Long rank;

    public StatisticPerson(Integer id, Long rank) {
        super(id);
        this.rank = rank;
    }

    public Long getRank() {
        return rank;
    }

}
