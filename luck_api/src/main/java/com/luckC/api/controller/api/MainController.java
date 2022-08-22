//package com.luckC.api.controller.api;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/luck")
//@RequiredArgsConstructor
//public class MainController {
//
//    @Slf4j
//    @PostMapping("/main.do")
//    public ResponseEntity<Result<List<User>>> main(@Valid @RequestBody mainReq, HttpSession httpSession) {
//        List<MainRes> mainResList = mainService.main(mainReq, httpSession);
//
//        return ResponseEntity.ok().body(new Result<MainRes>(mainResList));
//    }
//
//}
