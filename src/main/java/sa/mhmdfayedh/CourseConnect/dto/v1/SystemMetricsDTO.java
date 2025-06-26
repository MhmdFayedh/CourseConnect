package sa.mhmdfayedh.CourseConnect.dto.v1;

public class SystemMetricsDTO {
    private Double heapMemoryUsed;
    private Double cpuUsage;
    private Double activeDbConnections;
    private Double uptimeInSeconds;
    private Double requestCount;

    public SystemMetricsDTO(Double heapMemoryUsed,
                            Double cpuUsage,
                            Double activeDbConnections,
                            Double uptimeInSeconds,
                            Double requestCount) {
        this.heapMemoryUsed = heapMemoryUsed;
        this.cpuUsage = cpuUsage;
        this.activeDbConnections = activeDbConnections;
        this.uptimeInSeconds = uptimeInSeconds;
        this.requestCount = requestCount;
    }

    public SystemMetricsDTO() {

    }

    public Double getHeapMemoryUsed() {
        return heapMemoryUsed;
    }

    public void setHeapMemoryUsed(Double heapMemoryUsed) {
        this.heapMemoryUsed = heapMemoryUsed;
    }

    public Double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(Double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public Double getActiveDbConnections() {
        return activeDbConnections;
    }

    public void setActiveDbConnections(Double activeDbConnections) {
        this.activeDbConnections = activeDbConnections;
    }

    public Double getUptimeInSeconds() {
        return uptimeInSeconds;
    }

    public void setUptimeInSeconds(Double uptimeInSeconds) {
        this.uptimeInSeconds = uptimeInSeconds;
    }

    public Double getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Double requestCount) {
        this.requestCount = requestCount;
    }
}
