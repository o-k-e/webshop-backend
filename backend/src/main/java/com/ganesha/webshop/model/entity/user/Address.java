//package com.ganesha.webshop.model.entity.user;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Data
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "address")
//public class Address {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(nullable = false, name = "street_name")
//    private String streetName;
//
//    @Column(nullable = false, name = "street_number")
//    private String streetNumber;
//
//    @Column(name = "floor")
//    private String floor;
//
//    @Column(name = "door_number")
//    private String doorNumber;
//
//    @Column(name = "city")
//    private String city;
//
//    @Column(name = "country")
//    private String country;
//
//    @Column(name = "postal_code")
//    private String postalCode;
//
//    @Column(name = "other")
//    private String other;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
//}
