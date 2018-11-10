package gl.linpeng.serverless.advisor.controller.request;

import javax.validation.constraints.NotNull;

/**
 * Operation log request
 *
 * @author lin.peng
 * @since 1.0
 **/
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
}
