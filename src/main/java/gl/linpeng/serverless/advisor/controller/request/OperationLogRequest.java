package gl.linpeng.serverless.advisor.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;

/**
 * Operation log request
 *
 * @author lin.peng
 * @since 1.0
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationLogRequest {

    /**
     * 操作组
     */
    private int operationGroup;
    /**
     * 操作业务类型
     */
    @NotNull
    private int operationType;
    /**
     * 操作目标ID
     */
    @NotNull
    private Long operationTargetId;

    @NotNull
    private int operationTargetType;

    /**
     * 操作源id
     */
    private Long operationSourceId;
    /**
     * 操作源类型
     */
    private int operationSourceType;

    private String openId;

    public int getOperationTargetType() {
        return operationTargetType;
    }

    public void setOperationTargetType(int operationTargetType) {
        this.operationTargetType = operationTargetType;
    }

    public int getOperationGroup() {
        return operationGroup;
    }

    public void setOperationGroup(int operationGroup) {
        this.operationGroup = operationGroup;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public Long getOperationTargetId() {
        return operationTargetId;
    }

    public void setOperationTargetId(Long operationTargetId) {
        this.operationTargetId = operationTargetId;
    }

    public Long getOperationSourceId() {
        return operationSourceId;
    }

    public void setOperationSourceId(Long operationSourceId) {
        this.operationSourceId = operationSourceId;
    }

    public int getOperationSourceType() {
        return operationSourceType;
    }

    public void setOperationSourceType(int operationSourceType) {
        this.operationSourceType = operationSourceType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
