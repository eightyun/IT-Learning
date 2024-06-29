package com.tianji.remark.task;

import com.tianji.remark.service.ILikedRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LikedTimesCheckTask {

    // 业务类型
    private static final List<String> BIZ_TYPES = List.of("QA", "NOTE");
    // 任务每次取的biz数量 每次发送30个
    private static final int MAX_BIZ_SIZE = 30;

    private final ILikedRecordService recordService;

    /**
     * 每20秒检查一次点赞次数，
     * 将reids中业务类型下面某业务的点赞总数
     * 发送消息到mq
     * 每次发送30个
     */
    @Scheduled(fixedDelay = 20000)
    public void checkLikedTimes(){
        for (String bizType : BIZ_TYPES) {
            recordService.readLikedTimesAndSendMessage(bizType, MAX_BIZ_SIZE);
        }
    }
}
