package com.harmonycloud.controller;


import com.harmonycloud.service.MilestoneBacklogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/milestoneBacklog")
public class MilestoneBacklogController {

    @Autowired
    MilestoneBacklogService milestoneBacklogService;
}
