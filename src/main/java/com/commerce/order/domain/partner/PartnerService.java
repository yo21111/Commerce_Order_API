package com.commerce.order.domain.partner;

public interface PartnerService {
    // Command CUD 관련, Criteria R 관련 --- Info 객체에 대한 리턴

    PartnerInfo registerPartner(PartnerCommand command);
    PartnerInfo getPartnerInfo(String partnerToken);
    PartnerInfo enablePartner(String partnerToken);
    PartnerInfo disablePartner(String partnerToken);
}
