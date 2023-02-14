package com.example.demoProject.account.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "roles_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "roles_id_seq", sequenceName = "roles_seq", allocationSize = 1)
    private Long id;
    private String name;
}
