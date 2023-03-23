//package com.example.luck_project.login;
//
//import com.example.luck_project.controller.LuckLoginController;
//import com.jayway.jsonpath.JsonPath;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//public class login {
//    @InjectMocks
//    LuckLoginController luckLoginController;
//
//    @Mock
//    test test;
//
//    @Test
//    @DisplayName("정상적으로 처리되면 성공한다.(helper)")
//    void cardLedgerListHelper() throws Exception {
//        // given(준비)
//        String jsonStr = "{\"totalSelect\":\"T\",\"adminId\":\"admin\"}";
//        given(Service.getList(any())).willReturn(new ResModel());
//
//        // when(실행)
//        String rtnStn = Helper.Helper(jsonStr);
//
//        // then(검증)
//        assertNotNull(rtnStn);
//        Assertions.assertEquals("0000", JsonPath.read(rtnStn, "$.resultCode"));
//    }
//
//
//    @Test
//    @DisplayName(" 정상 처리되면 성공한다.")
//    void getListService() throws Exception {
//        // given(준비)
//        dto dto = dto.builder()
//                .adminId("adminId")
//                .build();
//
//        List<dto> cardList = new ArrayList<>();
//        dto dto = dto.builder()
//                .no(1)
//                .build();
//        dto.add(dto);
//
//        given(dto.dto(any())).willReturn(dto);
//
//        // when(실행)
//        dto returnModel = dto.getList(dto);
//
//        // then(검증)
//        assertNotNull(returnModel);
//        assertEquals(1, returnModel.dto());
//        assertEquals("1234", returnModel.dto().get(0).getCardNumber());
//    }
//
//    @Test
//    @DisplayName(" 정상 처리되면 성공한다")
//    void createCardRegServiceCardImgCode() throws Exception {
//        // given(준비)
//        given(dto.dto(any())).willReturn(1);
//
//        dto dto = dto.builder()
//                .adminId("admin")
//                .build();
//        // when(실행)/then(검증)
//        assertDoesNotThrow(() -> dto.dto(dto));
//    }
//
//    @Test
//    @DisplayName("ApiSr 예외가 발생하면 성공한다.")
//    void getCardLedgerRegList_BAndImgCatgCodeNull() throws Exception {
//        // given(준비)
//        dto dto = dto.builder()
//                .adminId("test")
//                .build();
//
//        // when(실행)/then(검증)
//        try {
//            dto.dto(dto);
//        } catch (Exception e) {
//            assertNotNull(e);
//        }
//    }
//
//
//}
