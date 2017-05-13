package com.code4piter.blueskythinking.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "words")
@AllArgsConstructor
@NoArgsConstructor
public class Word {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "value", nullable = false)
    private double value;
}
