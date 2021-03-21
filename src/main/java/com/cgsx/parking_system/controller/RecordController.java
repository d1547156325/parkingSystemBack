package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.service.RecordService;
import com.cgsx.parking_system.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/record")
@Slf4j
public class RecordController {

    @Autowired
    private RecordService recordService;


    @RequestMapping("/listRecord")
    public Result listRecord(
            @RequestParam(name = "keyword", defaultValue = "")String keyword,
            @RequestParam(name = "payment", defaultValue = "-1")Integer payment,
            @RequestParam(name = "page", defaultValue = "0")Integer page,
            @RequestParam(name = "limit", defaultValue = "10")Integer size){
        log.info("请求接口listRecord,参数:"+keyword+" "+payment+" "+page+" "+size);
        return new Result().success("查询成功", recordService.getRecode(keyword, payment, page, size));
    }
}
