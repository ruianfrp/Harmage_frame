package com.harmonycloud.bean.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HWF
 * @date 2020-05-25
 *
 * 项目状态和里程碑状态联动枚举类
 * 单一属性映射其他属性
 * 方案1：静态缓存     方案2：反射
 * 在数据量不大的情况下，内部静态缓存资源消耗不大，和易懂的情况下选择方案1
 */
public enum ProjectMilestoneStatus {
    SERIOUS_DELAY("进行中", "延期", "严重延期"), NORMAL_COMPLETE("完成", "完成", "正常完成"), OVER_TIME_COMPLETE("完成", "完成", "超时完成"),
    SMOOTHLY("进行中", "正常", "顺利进行"), IN_DELAY("进行中", "正常", "延期中");

    private String projectStatus;
    private String projectSubState;
    private String milestoneState;

    private static final Map<String, ProjectMilestoneStatus> MAP = new HashMap<>();

    static {
        for (ProjectMilestoneStatus item : ProjectMilestoneStatus.values()) {
            MAP.put(item.milestoneState, item);
        }
    }

    ProjectMilestoneStatus(String projectStatus, String projectSubState, String milestoneState) {
        this.projectStatus = projectStatus;
        this.projectSubState = projectSubState;
        this.milestoneState = milestoneState;
    }

    /**
     * 根据里程碑状态获取对应的枚举信息
     * @param milestoneState
     * @return
     */
    public static ProjectMilestoneStatus getProjectMilestoneStatus(String milestoneState) {
        return MAP.get(milestoneState);
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public String getProjectStatus(String projectSubState) {
        return projectStatus;
    }

    public String getProjectSubState() {
        return projectSubState;
    }

    public String getMilestoneState() {
        return milestoneState;
    }

    public void setMilestoneState(String milestoneState) {
        this.milestoneState = milestoneState;
    }
}
