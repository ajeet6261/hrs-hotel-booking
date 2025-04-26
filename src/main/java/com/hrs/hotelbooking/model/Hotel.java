package com.hrs.hotelbooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    private String code;
    private String name;
    private String city;
    private int status;
    private String contactno;
    private String category;
    private String vendorno;
    private String address;
    private String companyCode;
}
