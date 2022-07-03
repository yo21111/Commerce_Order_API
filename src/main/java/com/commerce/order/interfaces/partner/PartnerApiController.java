package com.commerce.order.interfaces.partner;

import com.commerce.order.application.partner.PartnerFacade;
import com.commerce.order.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/partners")
public class PartnerApiController {
    private final PartnerFacade partnerFacade;

    @PostMapping()
    public CommonResponse registerPartner(PartnerDto.RegisterRequest request) {
        //1. 외부에서 전달된 파라미터(DTO) -> Command, Criteria convert
        //2. Facade 호출 .. PartnerInfo
        //3. PartnerInfo -> CommonResponse convert AND return

        var command = request.toCommand();
        var partnerInfo = partnerFacade.registerPartner(command);
        var response = new PartnerDto.RegisterResponse(partnerInfo);
        return CommonResponse.success(response);
    }
}
