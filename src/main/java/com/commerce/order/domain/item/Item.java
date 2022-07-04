package com.commerce.order.domain.item;


import com.commerce.order.common.exception.InvalidParamException;
import com.commerce.order.common.util.TokenGenerator;
import com.commerce.order.domain.partner.AbstractEntity;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "items")
public class Item extends AbstractEntity {
    private static final String PREFIX_ITEM = "itm_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemToken;
    private Long partnerId;
    private String itemName;
    private Long itemPrice;

    // Item : ItemOptionGruop = 1 : N
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.PERSIST)
    private List<ItemOptionGroup> itemOptionGroups = Lists.newArrayList();

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        PREPARE("판매준비중"), ON_SALES("판매중"), END_OF_SALES("판매준비중중");

       private final String description;
    }

    @Builder
    public Item(Long partnerId, String itemName, Long itemPrice) {
        if (partnerId == null) throw new InvalidParamException();
        if (StringUtils.isEmpty(itemName)) throw new InvalidParamException();
        if (itemPrice == null) throw new InvalidParamException();

        this.itemToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_ITEM);
        this.partnerId = partnerId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.status = Status.PREPARE;
    }

    public void changePrepare() {
        this.status = Status.PREPARE;
    }

    public void changeOnSales() {
        this.status = Status.ON_SALES;
    }

    public void changeEndOfSales() {
        this.status = Status.END_OF_SALES;
    }
}
