package com.example.luck_project.controller;

import com.example.luck_project.domain.TodayLuckEntity;
import com.example.luck_project.dto.MainReq;
import com.example.luck_project.dto.MainRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/luck")
@RequiredArgsConstructor
@Log4j2
public class LuckMainController {

    @Autowired
    private MainService mainService;

    @PostMapping("/main.do")
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<TodayLuckEntity> luckMain(@Validated @RequestBody MainReq mainReq){
        String userId = mainReq.getUserId();
        log.info("[{}] 메인 조회 컨트롤러", userId);
        TodayLuckEntity mainRes = mainService.main(userId);

        return new ResponseEntity<>(mainRes, HttpStatus.OK);
    }


//        public void addStoreEvent(@Valid @RequestBody StoreEvent storeEvent, HttpServletResponse response, HttpServletRequest request) {
//            ResponseUtility responseUtil = new ResponseUtility(request, response, storeEvent);
//
//            String storeCd = "";
//
//            try {
//                storeCd = storeEvent.getStoreCd();
//
//                // 서비스 호출
//                service.saveStore(storeEvent);
//
//                responseUtil.setHeader(ResultCode.SUCCESS); // 성공 처리 헤더 저장
//            } catch(ApiException apiEx) {
//                logger.info("[{}] {}", storeCd, apiEx.getErrMsg());
//
//                responseUtil.setHeaderAndStatus(apiEx.getResultCode(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            } catch (Exception ex) {
//                logger.error("[{}] {}", storeCd, ex);
//
//                responseUtil.setHeaderAndStatus(ResultCode.SERVER_ERROR, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            }
//        }
//    }


}
