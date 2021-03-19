package com.cgsx.parking_system.service.impl;

import com.cgsx.parking_system.entity.Car;
import com.cgsx.parking_system.entity.Member;
import org.apache.commons.lang.StringUtils;
import com.cgsx.parking_system.entity.Space;
import com.cgsx.parking_system.repository.SpaceRepository;
import com.cgsx.parking_system.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.*;

@Service
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

//    @Override
//    public Page<Space> findAllByPage(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "spaceId");
//        return spaceRepository.findAll(pageable);
//    }

    @Override
    public void updateSpace(Space space) {
        spaceRepository.save(space);
    }

    @Override
    public Page<Space> getSpace(String keyword,Integer spaceRemark, Integer spaceStatus, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "spaceId");
        return spaceRepository.findAll(this.getWhereClause(keyword, spaceRemark, spaceStatus), pageable);
    }

    public Specification<Space> getWhereClause(String keyword, Integer spaceRemark, Integer spaceStatus){
        return new Specification<Space>() {
            @Override
            public Predicate toPredicate(Root<Space> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                /**
                 * 多表查询
                 */
                Join<Space, Car> sroot = root.join("space", JoinType.LEFT);
                if(StringUtils.isNotBlank(keyword)){
                    predicates.add(
                            criteriaBuilder.and(
                                    criteriaBuilder.or(
                                            criteriaBuilder.like(root.get("spaceArea"),"%" + keyword + "%"),
                                            criteriaBuilder.like(root.get("spaceNum"),"%" + keyword + "%"),
                                            criteriaBuilder.like(sroot.get("carNum"), "%" + keyword + "%"),
                                            criteriaBuilder.like(sroot.get("carOwner"), "%" + keyword + "%")
                                    )
                            )
                    );
                }
                if(spaceRemark != -1)
                    predicates.add(criteriaBuilder.equal(root.get("spaceRemark"), spaceRemark));
                if(spaceStatus != -1)
                    predicates.add(criteriaBuilder.equal(root.get("spaceStatus"), spaceStatus));
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
    }
}
