package com.cgsx.parking_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 车位db
 */
@Entity
@Table(name = "space")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Space implements Serializable {
    private static final long serialVersionUID = 8945557689155079769L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceId;

    // 车位编号
    private String spaceNum;

    // 车位状态 0:未占用  1:已占用  2:已下线
    private Integer spaceStatus;


//    @OneToMany
//    @JoinColumn(name = "space_id")
//    private List<Record> recordList = new ArrayList<>();

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "space_area_id")
    private SpaceArea spaceArea;

}
