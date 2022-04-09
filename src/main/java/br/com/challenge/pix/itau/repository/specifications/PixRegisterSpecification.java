package br.com.challenge.pix.itau.repository.specifications;

import br.com.challenge.pix.itau.dto.PixRegisterResponse;
import br.com.challenge.pix.itau.entity.PixRegister;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class PixRegisterSpecification implements Specification<PixRegister> {

    private String keyType;
    private Integer agencyNumber;
    private Integer accountNumber;
    private String userFirstName;
    private Date createdAt;
    private Date deletedAt;
    @Override
    public Predicate toPredicate(Root<PixRegister> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);

        List<Predicate> list = new ArrayList<>();

        if(keyType!=null){
            String keyTypeLike = String.join("%", keyType.split(" ")).toLowerCase();
            list.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("keyType")), "%"+keyTypeLike+"%"));
        }
        if(agencyNumber!=null){
            list.add(criteriaBuilder.equal(root.get("agencyNumber"), agencyNumber));
        }
        if(accountNumber!=null){
            list.add(criteriaBuilder.equal(root.get("accountNumber"), accountNumber));
        }
        if(userFirstName!=null){
            String userFirstNameLike = String.join("%", userFirstName.split(" ")).toLowerCase();
            list.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("userFirstName")), "%"+userFirstNameLike+"%"));
        }
        if(createdAt!=null){
            list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAt));
        }
        if(deletedAt!=null){
            list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("deletedAt"), deletedAt));
        }
        return criteriaBuilder.and(list.toArray(new Predicate[]{}));
    }
}
