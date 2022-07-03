package com.commerce.order.infrastructure.partner;

import com.commerce.order.common.exception.InvalidParamException;
import com.commerce.order.domain.partner.Partner;
import com.commerce.order.domain.partner.PartnerStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PartnerStoreImpl implements PartnerStore {
    private final PartnerRepository partnerRepository;

    @Override
    public Partner store(Partner initPartner) {
        if(StringUtils.isEmpty(initPartner.getPartnerToken())) throw new InvalidParamException("Partner.getPartnerToken()");
        if(StringUtils.isEmpty(initPartner.getPartnerName())) throw new InvalidParamException("Partner.getPartnerName()");
        if(StringUtils.isEmpty(initPartner.getBusinessNo())) throw new InvalidParamException("Partner.getBusinessNo()");
        if(StringUtils.isEmpty(initPartner.getEmail())) throw new InvalidParamException("Partner.getEmail()");
        if(initPartner.getStatus() == null) throw new InvalidParamException("Partner.getStatus()");

        return partnerRepository.save(initPartner);
    }
}
